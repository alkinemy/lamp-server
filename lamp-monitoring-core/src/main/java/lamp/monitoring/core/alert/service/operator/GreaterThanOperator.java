package lamp.monitoring.core.alert.service.operator;

public class GreaterThanOperator implements RelationalOperator {

    @Override
    public boolean perform(Comparable... objects) {
        if (objects != null && objects.length == 2
                && objects[0] != null && objects[1] != null) {
            return objects[0].compareTo(objects[1]) > 0;
        }
        return false;
    }
    
}
