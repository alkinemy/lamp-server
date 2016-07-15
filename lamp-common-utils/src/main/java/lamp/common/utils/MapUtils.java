package lamp.common.utils;

import java.util.Map;

public abstract class MapUtils { //NOSONAR

    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(final Map<?,?> map) {
        return !MapUtils.isEmpty(map);
    }

}
