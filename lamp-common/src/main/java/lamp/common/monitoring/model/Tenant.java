package lamp.common.monitoring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class Tenant implements Serializable {

    private String id;
    private String name;

    private List<TenantUser> users;

}
