package com.codecool.nopainnogain.converter;

import com.codecool.nopainnogain.model.WorkoutBlock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutBlockConverter implements AttributeConverter<List<WorkoutBlock>, String> {

    private ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping();


    @Override
    public String convertToDatabaseColumn(List<WorkoutBlock> workoutBlocks) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writerFor(new TypeReference<List<WorkoutBlock>>() { }).writeValueAsString(workoutBlocks);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    public List<WorkoutBlock> convertToEntityAttribute(String s) {
        List<WorkoutBlock> result = new ArrayList<>();

        try {
            List<WorkoutBlock> blocks = objectMapper.readValue(s,new TypeReference<List<WorkoutBlock>>() { });
            result.addAll(blocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
