package com.codecool.nopainnogain.json;

import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkoutJsonConverter {

    public static String workoutToString(Workout workout){
        ObjectNode workoutNode = JsonNodeFactory.instance.objectNode();

        workoutNode.put("title", workout.getTitle());
        workoutNode.put("lastUpdated",String.valueOf(workout.getLastUpdated()));
        workoutNode.put("id",String.valueOf(workout.getId()));

        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

        for(WorkoutBlock block: workout.getBlocks()){
            arrayNode.add(WorkoutBlockJsonConverter.blockToJson(block));
        }

        workoutNode.put("blocks",arrayNode.toString());

        System.out.println(workoutNode.toString());

        return workoutNode.toString();
    }

    public static Workout jsonToWorkout(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;

        try {
            map = mapper.readValue(jsonString,new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        String title = (String) map.get("title");
        Long id = Long.valueOf((String) map.get("id"));
        Long lastUpdated = Long.valueOf((String) map.get("lastUpdated"));

        Workout resultWorkout = new Workout(title);
        resultWorkout.setLastUpdated(lastUpdated);
        resultWorkout.setId(id);

        List<String> blocksStringList = null;
        List<WorkoutBlock> blocks = new ArrayList<>();
        try {
            blocksStringList = mapper.readValue((String) map.get("blocks"),new TypeReference<List<String>>() {} );
            for(String block: blocksStringList){
                blocks.add(WorkoutBlockJsonConverter.jsonToBlock(block));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(WorkoutBlock block: blocks){
            resultWorkout.addBlock(block);
        }

        return resultWorkout;
    }



}
