package org.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.InputStream;
import java.util.List;

public class JsonRepository <T> implements Repository<T>{
    protected List<T> data;

    protected JsonRepository(String filePath, Class<T> clazz) {this.data = loadFromJson(filePath, clazz);}

    @Override
    public List<T> findAll() {return data;}

    private List<T> loadFromJson(String filePath, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);

            return mapper.readValue(inputStream, collectionType);


        } catch (Exception e) {
            throw new RuntimeException("cannot load json file: " + filePath, e);
        }
    }

}
