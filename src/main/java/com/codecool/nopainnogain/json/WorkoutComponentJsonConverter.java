package com.codecool.nopainnogain.json;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Map;

public class WorkoutComponentJsonConverter {

    public static String componentToJson(WorkoutComponent comp){
        ObjectNode component = JsonNodeFactory.instance.objectNode();

        if(comp instanceof Rest){
            component.put("duration",String.valueOf(((Rest) comp).getDurationInMilis()));
            component.put("order", comp.getOrderId());
        }else if(comp instanceof WorkoutExercise){
            component.put("reps",((WorkoutExercise) comp).getReps());
            component.put("exercise",ExerciseJsonConverter.exerciseToJson(((WorkoutExercise) comp).getExercise()));
            component.put("order", comp.getOrderId());
        }
        return component.toString();
    }

    public static WorkoutComponent jsonToComponent(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        WorkoutComponent resultComp = null;

        try {
            map = mapper.readValue(jsonString,new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(map.containsKey("duration")){
            int order = (int) map.get("order");
            int duration = Integer.valueOf((String)map.get("duration"));

            resultComp = new Rest(duration);
            resultComp.setOrderId(order);
        }else{
            int order = (int) map.get("order");
            int reps = (int) map.get("reps");
            Exercise ex = ExerciseJsonConverter.jsonToExercise((String) map.get("exercise"));

            resultComp = new WorkoutExercise(reps,ex);
            resultComp.setOrderId(order);
        }

        return resultComp;


    }


}
