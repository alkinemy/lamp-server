package lamp.admin.domain.base.model.assembler;


import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.base.model.Task;
import lamp.admin.domain.base.model.entity.TaskEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class TaskAssembler extends AbstractListAssembler <TaskEntity, Task> {

	@Override protected Task doAssemble(TaskEntity entity) {
		try {
			String dataType = entity.getDataType();
			Class<? extends Task> clazz = (Class<? extends Task>) Class.forName(dataType);
			Task job = JsonUtils.parse(entity.getData(), clazz);
			return job;
		} catch (Exception e) {
			throw new CannotAssembleException(e);
		}
	}

}
