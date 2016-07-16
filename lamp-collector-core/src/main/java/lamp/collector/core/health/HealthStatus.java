package lamp.collector.core.health;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HealthStatus {

    private HealthStatusCode code;
    private String message;

}
