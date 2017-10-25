package ru.croc.demo.service;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Сервис трансляции json в объекты и обратно.
 *
 * @author agumenyuk
 * @since 01.07.2016 17:01
 */
public class MapperService {

    /**
     * Статический экземпляр, замена DI
     */
    private static MapperService instance;

    public static MapperService getInstance() {
        if (instance == null) {
            instance = new MapperService();
        }
        return instance;
    }

    /**
     * Подготовленный объект для трансляции объекта в json либо обратно.
     *
     * @return
     */
    public ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return mapper;
    }

    /**
     * Объект в json-строку.
     *
     * @param obj
     *            объект
     * @return
     */
    public String toString(final Object obj) {
        StringWriter stringWriter = new StringWriter();
        try {
            getMapper().writeValue(stringWriter, obj);
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return stringWriter.toString();
    }

    /**
     * json-строка в объект
     *
     * @param json
     *            json-строка
     * @param clazz
     *            класс
     * @return объект
     */
    public <T> T readValue(final String json, final Class<T> clazz) {
        T result = null;
        ObjectMapper mapper = MapperService.getInstance().getMapper();
        try {
            result = mapper.readValue(json, clazz);
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }

}
