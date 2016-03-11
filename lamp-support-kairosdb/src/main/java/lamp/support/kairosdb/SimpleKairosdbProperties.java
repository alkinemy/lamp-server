package lamp.support.kairosdb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleKairosdbProperties implements KairosdbProperties {

	private String url;

}
