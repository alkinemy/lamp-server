package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.GroupCreateForm;
import lamp.admin.domain.app.base.model.form.SpringBootAppCreateForm;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.CollectionUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
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
	private SpringBootAppService springBootAppService;
	@Autowired
	private AppInstanceService appInstanceService;
	@Autowired
	private AppInstanceDeployService appInstanceDeployService;

	private App rootGroup = new App("", AppType.GROUP, "", "");

	public List<App> getAppsByPath(String path) {
		List<AppEntity> appEntities = appEntityService.getAppEntityListByPath(path);
		return smartAssembler.assemble(appEntities, App.class);
	}

	public List<App> getAppsWithParentAll(App currentApp) {
		List<App> apps = new ArrayList<>();
		apps.add(currentApp);

		App app = currentApp;
		while (!rootGroup.getPath().equals(app.getPath())) {
			app = getApp(app.getPath());
			apps.add(0, app);
		}

		return apps;
	}

	public App getApp(String id) {
		if (StringUtils.isBlank(id)) {
			return rootGroup;
		}
		Optional<App> appOptional = getAppOptional(id);
		Exceptions.throwsException(!appOptional.isPresent(), AdminErrorCode.APP_NOT_FOUND, id);
		return appOptional.get();
	}

	public Optional<App> getAppOptional(String id) {
		AppEntity appEntity = appEntityService.getAppEntity(id);
		App app = smartAssembler.assemble(appEntity, AppEntity.class, App.class);
		return Optional.ofNullable(app);
	}

	public App createApp(App app) {
		AppEntity entity = smartAssembler.assemble(app, AppEntity.class);
		AppEntity saved = appEntityService.createAppEntity(entity);
		return smartAssembler.assemble(saved, App.class);
	}

	public App createGroup(String path, GroupCreateForm editForm) {
		App group = new App();
		group.setType(AppType.GROUP);
		group.setName(editForm.getName());
		group.setPath(path);
		group.setDescription(editForm.getDescription());
		return createApp(group);
	}

	@Transactional
	public App createApp(String path, SpringBootAppCreateForm editForm) {
		return createApp(springBootAppService.newApp(path, editForm));
	}


	public void destroy(App app, boolean forceDestroy) {
		List<AppInstance> appInstances = appInstanceService.getAppInstances(app.getId());
		if (CollectionUtils.isNotEmpty(appInstances)) {
			for (AppInstance appInstance : appInstances) {
				appInstanceDeployService.undeploy(appInstance, forceDestroy);
			}
		}
		appEntityService.deleteAppEntity(app.getId());
	}


}
