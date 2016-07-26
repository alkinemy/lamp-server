package lamp.admin.domain.base.model.assembler;



import lamp.admin.domain.base.model.Task;
import lamp.admin.domain.base.model.entity.TaskEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class TaskEntityAssembler extends AbstractListAssembler<Task, TaskEntity> {

	@Override protected TaskEntity doAssemble(Task job) {
		TaskEntity entity = new TaskEntity();
		entity.setId(job.getId());
		entity.setDataType(job.getClass().getName());
		entity.setData(JsonUtils.stringify(job));
		return entity;
	}


}
