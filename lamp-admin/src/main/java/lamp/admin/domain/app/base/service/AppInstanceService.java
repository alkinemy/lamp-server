package lamp.admin.domain.app.base.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppInstanceService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AppInstanceEntityService appInstanceEntityService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private AgentClient agentClient;

	public List<AppInstance> getAppInstances(String appId) {
		List<AppInstanceEntity> appInstanceEntityList = appInstanceEntityService.getListByAppId(appId);
		List<AppInstance> appInstances = smartAssembler.assemble(appInstanceEntityList, AppInstanceEntity.class, AppInstance.class);

		// TODO host null 처리
		return appInstances.stream().sorted((e1, e2) -> e1.getHost().getName().compareTo(e2.getHost().getName())).collect(Collectors.toList());
	}

	public List<AppInstance> getAppInstancesByAgent(Agent agent) {
		return agentClient.getAppInstances(agent);
	}

	public AppInstance getAppInstance(String id) {
		AppInstanceEntity entity = appInstanceEntityService.get(id);
		return smartAssembler.assemble(entity, AppInstanceEntity.class, AppInstance.class);
	}

	public Optional<AppInstance> getAppInstanceOptional(String id) {
		AppInstanceEntity entity = appInstanceEntityService.getOptional(id).orElse(null);
		AppInstance appInstance = smartAssembler.assemble(entity, AppInstanceEntity.class, AppInstance.class);
		return Optional.ofNullable(appInstance);
	}

	@Transactional
	public AppInstance create(AppInstance appInstance) {
		AppInstanceEntity appInstanceEntity = smartAssembler.assemble(appInstance, AppInstance.class, AppInstanceEntity.class);
		return smartAssembler.assemble(appInstanceEntityService.create(appInstanceEntity), AppInstanceEntity.class,
									   AppInstance.class);
	}

	@Transactional
	public AppInstance update(AppInstance appInstance) {
		AppInstanceEntity appInstanceEntity = appInstanceEntityService.get(appInstance.getId());
		smartAssembler.populate(appInstance, appInstanceEntity, AppInstance.class, AppInstanceEntity.class);
		return smartAssembler.assemble(appInstanceEntity, AppInstanceEntity.class, AppInstance.class);
	}

	@Transactional
	public AppInstance updateStatus(AppInstance appInstance) {
		Optional<AppInstanceEntity> appInstanceEntityOptional = appInstanceEntityService.getOptional(appInstance.getId());
		if (appInstanceEntityOptional.isPresent()) {
			AppInstanceEntity appInstanceEntity = appInstanceEntityOptional.get();
			appInstanceEntity.setStatus(appInstance.getStatus());
			appInstanceEntity.setStatusMessage(appInstance.getStatusMessage());
			log.info("AppInstanceEntity : {}", appInstanceEntity);
			return smartAssembler.assemble(appInstanceEntity, AppInstanceEntity.class, AppInstance.class);
		} else {
			// FIXME 수정 바람
			log.warn("AppInstance not exist : instanceId={}, hostId={}", appInstance.getId(), appInstance.getHostId());
			return appInstance;
		}
	}

	@Transactional
	public void delete(AppInstance appInstance) {
		appInstanceEntityService.delete(appInstance.getId());
	}

}

