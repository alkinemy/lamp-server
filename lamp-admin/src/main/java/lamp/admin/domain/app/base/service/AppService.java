package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AppEntityService appEntityService;

	public List<App> getApps(String path) {
		List<AppEntity> appEntities = appEntityService.getAppEntityListByPath(path);
		return smartAssembler.assemble(appEntities, App.class);
	}

	public App createApp(App app) {
		AppEntity entity = smartAssembler.assemble(app, AppEntity.class);
		AppEntity saved = appEntityService.addAppEntity(entity);
		return smartAssembler.assemble(saved, App.class);
	}

	public App getApp(String id) {
		AppEntity entity = appEntityService.getAppEntity(id);
		Exceptions.throwsException(entity == null, AdminErrorCode.APP_NOT_FOUND, id);
		return smartAssembler.assemble(entity, AppEntity.class, App.class);
	}

}
