package lamp.admin.domain.app.base.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.app.base.*;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lamp.admin.core.host.Host;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.web.AdminErrorCode;
import lamp.collector.core.health.HealthStatus;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppInstanceService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AppInstanceEntityService appInstanceEntityService;
	@Autowired
	private AgentClient agentClient;

	public List<AppInstance> getAppInstances() {
		List<AppInstanceEntity> appInstanceEntityList = appInstanceEntityService.getList();
		return smartAssembler.assemble(appInstanceEntityList, AppInstanceEntity.class, AppInstance.class);
	}

	public List<AppInstance> getAppInstancesByAppId(String appId) {
		List<AppInstanceEntity> appInstanceEntityList = appInstanceEntityService.getListByAppId(appId);
		List<AppInstance> appInstances = smartAssembler.assemble(appInstanceEntityList, AppInstanceEntity.class, AppInstance.class);

		// TODO host null 처리
		return appInstances.stream().sorted((e1, e2) -> e1.getHostName().compareTo(e2.getHostName())).collect(Collectors.toList());
	}

	public List<AppInstance> getAppInstancesByHostId(String hostId) {
		List<AppInstanceEntity> appInstanceEntityList = appInstanceEntityService.getListByHostId(hostId);
		List<AppInstance> appInstances = smartAssembler.assemble(appInstanceEntityList, AppInstanceEntity.class, AppInstance.class);

		return appInstances.stream().sorted((e1, e2) -> e1.getHostName().compareTo(e2.getHostName())).collect(Collectors.toList());
	}

	public List<AppInstance> getAppInstancesByAgent(Agent agent) {
		return agentClient.getAppInstances(agent);
	}

	public AppInstance getAppInstance(String id) {
		AppInstanceEntity entity = appInstanceEntityService.getAppInstanceEntity(id);
		return smartAssembler.assemble(entity, AppInstanceEntity.class, AppInstance.class);
	}

	public Optional<AppInstance> getAppInstanceOptional(String id) {
		AppInstanceEntity entity = appInstanceEntityService.getOptionalAppInstanceEntity(id).orElse(null);
		AppInstance appInstance = smartAssembler.assemble(entity, AppInstanceEntity.class, AppInstance.class);
		return Optional.ofNullable(appInstance);
	}

	@Transactional
	public List<AppInstance> createAppInstances(List<? extends Host> hosts, App app) {
		List<AppInstance> appInstances = new ArrayList<>();
		for (Host host : hosts) {
			String instanceId = app.getName() + "-" + UUID.randomUUID().toString();
			AppInstance appInstance = createAppInstance(app, instanceId, host);
			appInstance.setStatus(AppInstanceStatus.PENDING);

			AppInstance addedAppInstance = addAppInstance(appInstance);

			appInstances.add(addedAppInstance);
		}
		return appInstances;
	}


	@Transactional
	public AppInstance addAppInstance(AppInstance appInstance) {
		AppInstanceEntity appInstanceEntity = smartAssembler.assemble(appInstance, AppInstance.class, AppInstanceEntity.class);
		return smartAssembler.assemble(appInstanceEntityService.addAppInstanceEntity(appInstanceEntity), AppInstanceEntity.class,
									   AppInstance.class);
	}

	@Transactional
	public AppInstance updateAppInstance(AppInstance appInstance) {
		AppInstanceEntity appInstanceEntity = appInstanceEntityService.getAppInstanceEntity(appInstance.getId());
		smartAssembler.populate(appInstance, appInstanceEntity, AppInstance.class, AppInstanceEntity.class);
		return smartAssembler.assemble(appInstanceEntity, AppInstanceEntity.class, AppInstance.class);
	}

	@Transactional
	public void updateAppInstanceStatus(AppInstance appInstance, AppInstanceStatus status) {
		updateAppInstanceStatus(appInstance, status, null);
	}

	@Transactional
	public AppInstance updateAppInstanceStatus(AppInstance appInstance, AppInstanceStatus status, String statusMessage) {
		AppInstanceEntity appInstanceEntity = appInstanceEntityService.getAppInstanceEntity(appInstance.getId());
		appInstanceEntity.setStatus(status);
		appInstanceEntity.setStatusMessage(statusMessage);
		return smartAssembler.assemble(appInstanceEntity, AppInstanceEntity.class, AppInstance.class);
	}

	@Transactional
	public void updateStatus(String id, AppInstanceStatusResult statusResult) {
		Optional<AppInstanceEntity> appInstanceEntityOptional = appInstanceEntityService.getOptionalAppInstanceEntity(id);
		if (appInstanceEntityOptional.isPresent()) {
			AppInstanceEntity appInstanceEntity = appInstanceEntityOptional.get();
			appInstanceEntity.setStatus(statusResult.getStatus());
			appInstanceEntity.setStatusMessage(statusResult.getStatusMessage());
			log.info("AppInstanceEntity : {}", appInstanceEntity);
		} else {
			// FIXME 수정 바람
			log.warn("AppInstance not exist : instanceId={}", id);
		}
	}

	@Transactional
	public void updateHealth(String id, HealthStatus healthStatus) {
		AppInstanceEntity appInstanceEntity = appInstanceEntityService.getAppInstanceEntity(id);
		appInstanceEntity.setHealth(healthStatus.getCode());
		appInstanceEntity.setHealthMessage(healthStatus.getMessage());
	}

	@Transactional
	public void delete(AppInstance appInstance) {
		appInstanceEntityService.deleteAppInstanceEntity(appInstance.getId());
	}



	protected AppInstance createAppInstance(App app, String instanceId, Host host) {
		AppInstance appInstance = new AppInstance();
		appInstance.setId(instanceId);
		appInstance.setName(app.getName());
		appInstance.setAppId(app.getId());
		appInstance.setAppName(app.getName());
		appInstance.setAppVersion(app.getVersion());
		appInstance.setClusterId(host.getClusterId());
		appInstance.setClusterName(host.getClusterName());
		appInstance.setHostId(host.getId());
		appInstance.setHostName(host.getName());
		appInstance.setHostAddress(host.getAddress());
		appInstance.setTags(app.getTags());

		AppContainer appContainer = createAppContainer(app, host);
		appInstance.setAppContainer(appContainer);
		populateAppContainer(appContainer, appInstance);

		return appInstance;
	}

	protected AppContainer createAppContainer(App app, Host host) {
		try {
			AppContainer appContainer = app.getContainer();
			AppContainer instanceAppContainer = appContainer.getClass().newInstance();
			BeanUtils.copyProperties(appContainer, instanceAppContainer);
			if (instanceAppContainer instanceof SimpleAppContainer) {
				HealthEndpoint healthEndpoint = ((SimpleAppContainer) instanceAppContainer).getHealthEndpoint();
				if (healthEndpoint != null) {
					healthEndpoint.setAddress(host.getAddress());
				}

				MetricsEndpoint metricsEndpoint = ((SimpleAppContainer) instanceAppContainer).getMetricsEndpoint();
				if (metricsEndpoint != null) {
					metricsEndpoint.setAddress(host.getAddress());
				}
			}
			return instanceAppContainer;
		} catch (Exception e) {
			throw Exceptions.newException(AdminErrorCode.APP_INSTANCE_APP_CONTAINER_CREATE_FAILED);
		}
	}

	protected void populateAppContainer(AppContainer appContainer, AppInstance appInstance) {
		if (appContainer instanceof SimpleAppContainer) {
			SimpleAppContainer simpleAppContainer = (SimpleAppContainer) appContainer;
			appInstance.setHealthEndpointEnabled(simpleAppContainer.isHealthEndpointEnabled());
			appInstance.setHealthEndpoint(simpleAppContainer.getHealthEndpoint());
			appInstance.setMetricsEndpointEnabled(simpleAppContainer.isMetricsEndpointEnabled());
			appInstance.setMetricsEndpoint(simpleAppContainer.getMetricsEndpoint());
		}
	}


}

