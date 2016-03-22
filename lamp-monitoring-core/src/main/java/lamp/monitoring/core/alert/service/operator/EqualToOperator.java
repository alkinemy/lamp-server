package lamp.monitoring.core.alert.service.operator;

import java.util.Objects;

public class EqualToOperator implements RelationalOperator {

    @Override
    public boolean perform(Comparable... objects) {
        if (objects != null && objects.length == 2) {
            return Objects.equals(objects[0], objects[1]);
        }
        return false;
    }

}
