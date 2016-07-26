package lamp.admin.domain.base.service;

import lamp.admin.domain.base.model.Task;
import lamp.admin.domain.base.model.TaskStatus;
import lamp.admin.domain.base.model.entity.TaskEntity;
import lamp.admin.domain.base.repository.TaskEntityRepository;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

	@Autowired
	private TaskEntityRepository jobEntityRepository;
	@Autowired
	private SmartAssembler smartAssembler;

	public <T extends Task> List<T> getJobList(Class<T> jobClass, TaskStatus... status) {
		List<TaskEntity> jobEntities = jobEntityRepository.findAllByDataTypeAndStatusIn(jobClass.getName(), status);
		return smartAssembler.assemble(jobEntities, TaskEntity.class, (Class<T>) Task.class);
	}

	@Transactional
	public void addTask(Task job) {
		TaskEntity taskEntity = smartAssembler.assemble(job, Task.class, TaskEntity.class);
		taskEntity.setStatus(TaskStatus.READY);
		TaskEntity saved =  jobEntityRepository.save(taskEntity);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateJobStatus(String jobId, TaskStatus jobStatus) {
		TaskEntity taskEntity = jobEntityRepository.findOne(jobId);
		taskEntity.setStatus(jobStatus);
	}


}
