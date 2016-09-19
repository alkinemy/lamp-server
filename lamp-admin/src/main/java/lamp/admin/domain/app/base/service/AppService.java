package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.app.base.model.entity.AppHistoryEntity;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.*;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.CollectionUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AppEntityService appEntityService;
	@Autowired
	private AppHistoryEntityService appHistoryEntityService;
	@Autowired
	private SpringBootAppService springBootAppService;
	@Autowired
	private DockerAppService dockerAppService;
	@Autowired
	private AppInstanceService appInstanceService;
	@Autowired
	private AppInstanceDeployService appInstanceDeployService;

	private App rootGroup = new App("", AppType.GROUP, "", "");

	public List<App> getAppsByParentPath(String parentPath) {
		List<AppEntity> appEntities = appEntityService.getListByParentPath(parentPath);
		return smartAssembler.assemble(appEntities, AppEntity.class, App.class);
	}

	public List<App> getAppsWithParentAll(App currentApp) {
		List<App> apps = new ArrayList<>();
		apps.add(currentApp);

		App app = currentApp;
		while (StringUtils.isNotBlank(app.getParentPath())) {
			app = getAppByPath(app.getParentPath());
			apps.add(0, app);
		}

		return apps;
	}

	public List<App> getApHistories(App app) {
		List<AppHistoryEntity> appHistoryEntityList =  appHistoryEntityService.getAppHistoryEntityList(app.getId());
		List<App> appHistories = new ArrayList<>();
		for (AppHistoryEntity appHistoryEntity : appHistoryEntityList) {
			AppEntity appEntity = new AppEntity();
			BeanUtils.copyProperties(appHistoryEntity, appEntity);
			appHistories.add(smartAssembler.assemble(appEntity, AppEntity.class, App.class));
		}
		return appHistories;
	}

	public App getApp(String path) {
		if (StringUtils.isBlank(path)) {
			return rootGroup;
		}
		Optional<App> appOptional = getAppOptional(path);
		Exceptions.throwsException(!appOptional.isPresent(), AdminErrorCode.APP_NOT_FOUND, path);
		return appOptional.get();
	}

	public Optional<App> getAppOptional(String id) {
		AppEntity appEntity = appEntityService.get(id);
		App app = smartAssembler.assemble(appEntity, AppEntity.class, App.class);
		return Optional.ofNullable(app);
	}

	public App getAppByPath(String path) {
		if (StringUtils.isBlank(path)) {
			return rootGroup;
		}
		Optional<App> appOptional = getAppByPathOptional(path);
		Exceptions.throwsException(!appOptional.isPresent(), AdminErrorCode.APP_NOT_FOUND, path);
		return appOptional.get();
	}

	public Optional<App> getAppByPathOptional(String path) {
		AppEntity appEntity = appEntityService.getByPath(path);
		App app = smartAssembler.assemble(appEntity, AppEntity.class, App.class);
		return Optional.ofNullable(app);
	}

	public App createGroup(String path, GroupCreateForm editForm) {
		App group = new App();
		group.setType(AppType.GROUP);
		group.setName(editForm.getName());
		group.setParentPath(path);
		group.setDescription(editForm.getDescription());
		return createApp(group);
	}

	@Transactional
	public App createApp(App app) {
		AppEntity entity = smartAssembler.assemble(app, App.class, AppEntity.class);
		AppEntity saved = appEntityService.createAppEntity(entity);
		return smartAssembler.assemble(saved, App.class);
	}

	@Transactional
	public App createApp(String parentPath, SpringBootAppCreateForm editForm) {
		return createApp(springBootAppService.newApp(null, parentPath, editForm));
	}

	@Transactional
	public App createApp(String parentPath, DockerAppCreateForm editForm) {
		return createApp(dockerAppService.newApp(null, parentPath, editForm));
	}

	public SpringBootAppUpdateForm getSpringBootAppUpdateForm(String path) {
		return springBootAppService.getSpringBootAppUpdateForm(getAppByPath(path));
	}

	public DockerAppUpdateForm getDockerAppUpdateForm(String path) {
		return dockerAppService.getDockerAppUpdateForm(getAppByPath(path));
	}

	@Transactional
	public App updateApp(App app) {
		AppEntity entity = appEntityService.getByPath(app.getPath());
		AppHistoryEntity appHistoryEntity = newAppHistoryEntity(entity);
		smartAssembler.populate(app, entity, App.class, AppEntity.class);

		appHistoryEntityService.create(appHistoryEntity);

		return smartAssembler.assemble(entity, App.class);
	}

	protected AppHistoryEntity newAppHistoryEntity(AppEntity appEntity) {
		AppHistoryEntity appHistoryEntity = new AppHistoryEntity();
		BeanUtils.copyProperties(appEntity, appHistoryEntity);
		return appHistoryEntity;
	}

	@Transactional
	public App updateApp(String path, SpringBootAppUpdateForm editForm) {
		App app = getAppByPath(path);
		return updateApp(springBootAppService.newApp(app, editForm));
	}

	@Transactional
	public App updateApp(String path, DockerAppUpdateForm editForm) {
		App app = getAppByPath(path);
		return updateApp(dockerAppService.newApp(app, editForm));
	}


	public void destroy(App app, boolean forceDestroy) {
		if (AppType.GROUP.equals(app.getType())) {
			List<AppEntity> children = appEntityService.getListByParentPath(app.getId());
			Exceptions.throwsException(CollectionUtils.isNotEmpty(children), LampErrorCode.APP_GROUP_NOT_EMPTY, app.getId());
			appEntityService.deleteAppEntity(app.getId());
		} else {
			List<AppInstance> appInstances = appInstanceService.getAppInstancesByAppId(app.getId());
			if (CollectionUtils.isNotEmpty(appInstances)) {
				for (AppInstance appInstance : appInstances) {
					appInstanceDeployService.undeploy(appInstance, forceDestroy);
				}
			}
			appEntityService.deleteAppEntity(app.getId());
		}
	}


}
