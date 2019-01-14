package com.codecool.nopainnogain.json;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.ExerciseTarget;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Map;

public class ExerciseJsonConverter {

    public static String exerciseToJson(Exercise ex){

        ObjectNode exercise = JsonNodeFactory.instance.objectNode();

        exercise.put("target",ex.getTarget().name());
        exercise.put("name",ex.getName());
        exercise.put("id",String.valueOf(ex.getId()));
        exercise.put("description", ex.getDescription());
        exercise.put("lastUpdated",String.valueOf(ex.getLastUpdated()));

        return exercise.toString();
    }

    public static Exercise jsonToExercise(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;

        try {
            map = mapper.readValue(jsonString,new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExerciseTarget target = ExerciseTarget.valueOf(String.valueOf(map.get("target")));
        String name = String.valueOf(map.get("name"));
        String description = String.valueOf(map.get("description"));
        Long id = Long.valueOf((String) map.get("id"));
        Long lastUpdated = Long.valueOf((String) map.get("lastUpdated"));

        Exercise result = new Exercise(name,description,target);
        result.setId(id);
        result.setLastUpdated(lastUpdated);


        return result;


    }

}
