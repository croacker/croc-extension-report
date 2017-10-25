package ru.croc.demo.service;

import ru.croc.demo.data.PersistEntity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author AGumenyuk
 * @since 25.10.2017 18:59
 */
public class DatasourceService {

    private static DatasourceService instance;

    public DatasourceService getInstance(){
        if(instance == null){
            instance = new DatasourceService();
        }
        return instance;
    }

    private Map<String, PersistEntity> data = new LinkedHashMap<>();

    public void add(PersistEntity entity){
        data.put(entity.getId(), entity);
    }

    public PersistEntity get(String id){
        return data.get(id);
    }

    public List<PersistEntity> all(PersistEntity entity){
        return data.values().stream().collect(Collectors.toList());
    }

    public void delete(PersistEntity entity){
        data.remove(entity.getId());
    }

}
