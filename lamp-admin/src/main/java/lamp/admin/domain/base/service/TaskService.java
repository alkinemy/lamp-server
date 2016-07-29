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
	public void addTask(Task task) {
		addTask(task, TaskStatus.READY);
	}

	@Transactional
	public void addTask(Task task, TaskStatus status) {
		TaskEntity taskEntity = smartAssembler.assemble(task, Task.class, TaskEntity.class);
		taskEntity.setStatus(TaskStatus.READY);
		TaskEntity saved =  jobEntityRepository.save(taskEntity);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateTaskStatus(String taskId, TaskStatus jobStatus) {
		TaskEntity taskEntity = jobEntityRepository.findOne(taskId);
		taskEntity.setStatus(jobStatus);
	}


}
