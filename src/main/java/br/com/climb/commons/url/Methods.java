package br.com.climb.commons.url;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class Methods {

    public static final Map<String, Method> GET = new ConcurrentHashMap<>();
    public static final Map<String, Method> POST = new ConcurrentHashMap<>();
    public static final Map<String, Method> PUT = new ConcurrentHashMap<>();
    public static final Map<String, Method> DELETE = new ConcurrentHashMap<>();

    public static final Map<String, Set<Long>> RESERVED_WORDS = new ConcurrentHashMap<>();

    private Methods() {}

    public synchronized static boolean isReservedWord(String key, Long position) {

        Set<Long> positions = RESERVED_WORDS.get(key);

        if (positions != null) {
            for (Long pos : positions) {
                if (pos.equals(position)) {
                    return true;
                }
            }
        }
        return false;
    }

}
