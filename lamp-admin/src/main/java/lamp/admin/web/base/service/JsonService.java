package lamp.admin.web.base.service;

import lamp.admin.domain.support.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JsonService {

	public String stringify(Object object) {
		return JsonUtils.stringify(object);
	}

}
