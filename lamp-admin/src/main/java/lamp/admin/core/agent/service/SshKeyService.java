package lamp.admin.core.agent.service;


import lamp.admin.core.agent.domain.SshKey;
import lamp.admin.core.agent.domain.SshKeyCreateForm;
import lamp.admin.core.agent.domain.SshKeyDto;
import lamp.admin.core.agent.domain.SshKeyUpdateForm;
import lamp.admin.core.agent.repository.SshKeyRepository;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SshKeyService {

	@Autowired
	private SshKeyRepository sshKeyRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public Page<SshKeyDto> getSshKeyDtoList(Pageable pageable) {
		return smartAssembler.assemble(pageable, getSshKeyList(pageable), SshKeyDto.class);
	}

	public List<SshKeyDto> getSshKeyDtoList() {
		return smartAssembler.assemble(getSshKeyList(), SshKeyDto.class);
	}

	public Page<SshKey> getSshKeyList(Pageable pageable) {
		return sshKeyRepository.findAll(pageable);
	}

	public List<SshKey> getSshKeyList() {
		return sshKeyRepository.findAll();
	}

	public SshKey getSshKey(Long id) {
		return getSshKeyOptional(id).orElseThrow(() -> Exceptions.newException(LampErrorCode.SSH_KEY_NOT_FOUND, 1));
	}

	public Optional<SshKey> getSshKeyOptional(Long id) {
		return Optional.ofNullable(sshKeyRepository.findOne(id));
	}

	@Transactional
	public SshKey insertSshKey(SshKeyCreateForm form) {
		SshKey sshKey = smartAssembler.assemble(form, SshKey.class);
		return sshKeyRepository.save(sshKey);
	}

	public SshKeyUpdateForm getSshKeyUpdateForm(Long id) {
		SshKey sshKey = getSshKey(id);
		SshKeyUpdateForm form = new SshKeyUpdateForm();
		BeanUtils.copyProperties(sshKey, form);
		return form;
	}

	@Transactional
	public SshKey updateSshKey(Long id, SshKeyUpdateForm form) {
		SshKey sshKey = getSshKey(id);
		smartAssembler.populate(form, sshKey);
		return sshKey;
	}

	@Transactional
	public void deleteSshKey(Long id) {
		sshKeyRepository.delete(id);
	}


}
