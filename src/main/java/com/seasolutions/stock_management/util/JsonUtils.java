package com.seasolutions.stock_management.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();



    private JsonUtils() { }

    public static <E> E getObject(final String json, final E e) {
        try {
            return (E) MAPPER.readValue(json, e.getClass());
        } catch (final IOException ex) {
            logger.error("Error parsing json object.", ex);
        }
        return e;
    }

    public static <E> E getObject(final String json, final Class<E> type) {
        try {
            return (E) MAPPER.readValue(json, type);
        } catch (final IOException ex) {
            logger.error("Error parsing json object.", ex);
            return null;
        }
    }

    public static <E> List<E> getList(final String json, final TypeReference t) {
        try {
            return (List<E>) MAPPER.readValue(json, t);
        } catch (final IOException ex) {
            logger.error("Error parsing json object.", ex);
        }
        return new ArrayList<>();
    }

    public static <T, V> Map<T, V> getMap(final String json, final TypeReference t) {
        try {
            return (Map<T, V>) MAPPER.readValue(json, t);
        } catch (final IOException ex) {
            logger.error("Error parsing json object.", ex);
        }
        return new HashMap<>();
    }

    public static String toJSON(final Object o) {
        return toJSON(o, MAPPER, false);
    }

    public static String toJSON(final Object o, final ObjectMapper mapper, final boolean prettyPrint) {
        if (o == null) {
            return null;
        }
        try {
            if (prettyPrint) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } else {
                return mapper.writeValueAsString(o);
            }
        } catch (final IOException ex) {
            logger.error("Error writing json object.", ex);
        }
        return null;
    }

    public static String prettyJSON(final Object o) {
        return toJSON(o, MAPPER, true);
    }

    public static String prettyPrint(final String input) {
        try {
            final Object json = MAPPER.readValue(input, Object.class);
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (final Throwable e) {
            return input;
        }
    }
}
