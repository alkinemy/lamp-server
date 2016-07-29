package lamp.admin.domain.host.service;

import lamp.admin.LampAdminConstants;
import lamp.admin.config.web.ServerProperties;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.host.model.SshKey;
import lamp.admin.domain.host.model.entity.SshKeyEntity;
import lamp.admin.domain.host.repository.SshKeyEntityRepository;
import lamp.admin.domain.host.model.form.SshKeyForm;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.FileUtils;
import lamp.common.utils.IOUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class SshKeyService implements ResourceLoaderAware {

	@Autowired
	private ServerProperties serverProperties;

	@Autowired
	private SshKeyEntityRepository sshKeyEntityRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	private ResourceLoader resourceLoader;
	private SecretKeySpec secretKeySpec;
	private IvParameterSpec ivParameterSpec;

	@PostConstruct
	public void init() {
		// FIXME 제대로 구현 해라 : PBKDF2WithHmacSHA1

		String keyStorePassphrase = serverProperties.getKeyStorePassphrase();
		String keyStoreLocation = serverProperties.getKeyStoreLocation();
		Resource resource = resourceLoader.getResource(keyStoreLocation);
		try {
			if (!resource.isReadable()
				&& ResourceUtils.isFileURL(resource.getURL())
				&& StringUtils.isNotBlank(keyStorePassphrase)) {

					File file = resource.getFile();

					KeyGenerator keyGen = KeyGenerator.getInstance("AES");
					keyGen.init(128);
					SecretKey secretKey = keyGen.generateKey();

					FileUtils.writeByteArrayToFile(file, secretKey.getEncoded());
			}
		} catch (Exception e) {
			throw Exceptions.newException(AdminErrorCode.SECRET_KEY_GENERATION_FAILED, e);
		}

		try {
			byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());

			secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			ivParameterSpec = new IvParameterSpec(keyBytes);
		} catch (Exception e) {
			throw Exceptions.newException(AdminErrorCode.SECRET_KEY_LOAD_FAILED, e);
		}

	}

	public List<SshKey> getSshKeyList() {
		List<SshKeyEntity> sshKeyEntityList = sshKeyEntityRepository.findAll();
		return smartAssembler.assemble(sshKeyEntityList, SshKey.class);
	}

	public SshKey getSshKey(String id) {
		return getSshKey(id, false);
	}

	public SshKey getSshKey(String id, boolean decrypt) {
		SshKeyEntity sshKeyEntity = getSshKeyEntity(id);
		SshKey sshKey = smartAssembler.assemble(sshKeyEntity, SshKeyEntity.class, SshKey.class);
		if (decrypt) {
			sshKey.setPrivateKey(decrypt(sshKey.getPrivateKey()));
			sshKey.setPassphrase(decrypt(sshKey.getPassphrase()));
		}
		return sshKey;
	}

	public SshKeyEntity getSshKeyEntity(String id) {
		SshKeyEntity sshKeyEntity = sshKeyEntityRepository.findOne(id);
		Exceptions.throwsException(sshKeyEntity == null, AdminErrorCode.SSH_KEY_NOT_FOUND, id);
		return sshKeyEntity;
	}

	@Transactional
	public SshKey addSshKey(SshKeyForm editForm) {
		SshKey sshKey = createSshKey(editForm);
		return addSshKey(sshKey);
	}

	protected SshKey createSshKey(SshKeyForm editForm) {
		SshKey sshKey = new SshKey();
		sshKey.setId(UUID.randomUUID().toString());
		sshKey.setName(editForm.getName());
		sshKey.setDescription(editForm.getDescription());

		sshKey.setUsername(editForm.getUsername());
		try {
			sshKey.setPrivateKey(encrypt(new String(editForm.getPrivateKey().getBytes(), LampAdminConstants.DEFAULT_CHARSET)));
			sshKey.setPassphrase(encrypt(editForm.getPassphrase()));
		} catch (Exception e) {
			throw Exceptions.newException(AdminErrorCode.PRIVATE_KEY_UPLOAD_FAILED);
		}
		return sshKey;
	}

	@Transactional
	public SshKey addSshKey(SshKey sshKey) {
		SshKeyEntity entity = smartAssembler.assemble(sshKey, SshKeyEntity.class);
		SshKeyEntity saved = sshKeyEntityRepository.save(entity);
		return smartAssembler.assemble(saved, SshKey.class);
	}


	public SshKeyForm getSshKeyFormForUpdate(String id) {
		SshKey sshKey = getSshKey(id);
		SshKeyForm sshKeyForm = new SshKeyForm();
		sshKeyForm.setName(sshKey.getName());
		sshKeyForm.setDescription(sshKey.getDescription());
		sshKeyForm.setUsername(sshKey.getUsername());
		sshKeyForm.setPassphraseChange(false);
		return sshKeyForm;
	}

	@Transactional
	public void updateSshKey(String id, SshKeyForm editForm) {
		SshKeyEntity sshKeyEntity = getSshKeyEntity(id);
		sshKeyEntity.setName(editForm.getName());
		sshKeyEntity.setDescription(editForm.getDescription());
		sshKeyEntity.setUsername(editForm.getUsername());
		try {
			if (editForm.getPrivateKey() != null && !editForm.getPrivateKey().isEmpty()) {
				sshKeyEntity.setPrivateKey(encrypt(new String(editForm.getPrivateKey().getBytes(), LampAdminConstants.DEFAULT_CHARSET)));
			}
			if (editForm.isPassphraseChange()) {
				sshKeyEntity.setPassphrase(encrypt(editForm.getPassphrase()));
			}
		} catch (Exception e) {
			throw Exceptions.newException(AdminErrorCode.PRIVATE_KEY_UPLOAD_FAILED);
		}
	}

	@Transactional
	public void deleteSshKey(String id) {
		SshKeyEntity sshKeyEntity = getSshKeyEntity(id);
		sshKeyEntityRepository.delete(sshKeyEntity);
	}

	protected String encrypt(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		}

		if (secretKeySpec != null) {
			try {
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
				byte[] results = cipher.doFinal(text.getBytes(LampAdminConstants.DEFAULT_CHARSET));
				return Base64Utils.encodeToString(results);
			} catch (Exception e) {
				throw Exceptions.newException(AdminErrorCode.ENCRYPT_FAILED, e);
			}
		} else {
			return text;
		}
	}

	protected String decrypt(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		}

		if (secretKeySpec != null) {
			try {
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
				byte[] results = cipher.doFinal(Base64Utils.decodeFromString(text));
				return new String(results, LampAdminConstants.DEFAULT_CHARSET);
			} catch (Exception e) {
				throw Exceptions.newException(AdminErrorCode.DECRYPT_FAILED, e);
			}
		} else {
			return text;
		}
	}

	@Override public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


}
