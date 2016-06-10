package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.GroupCreateForm;
import lamp.admin.domain.app.base.model.form.SpringBootAppCreateForm;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AppEntityService appEntityService;
	@Autowired
	private SpringBootAppService springBootAppService;

	private App rootGroup = new App("", AppType.GROUP, "", "");

	public List<App> getAppsByPath(String path) {
		List<AppEntity> appEntities = appEntityService.getAppEntityListByPath(path);
		return smartAssembler.assemble(appEntities, App.class);
	}

	public App getApp(String id) {
		if (StringUtils.isBlank(id)) {
			return rootGroup;
		}
		AppEntity entity = appEntityService.getAppEntity(id);
		Exceptions.throwsException(entity == null, AdminErrorCode.APP_NOT_FOUND, id);
		return smartAssembler.assemble(entity, AppEntity.class, App.class);
	}


	public App createApp(App app) {
		AppEntity entity = smartAssembler.assemble(app, AppEntity.class);
		AppEntity saved = appEntityService.addAppEntity(entity);
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




}
