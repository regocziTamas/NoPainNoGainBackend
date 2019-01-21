package com.codecool.nopainnogain.controller;

import com.codecool.nopainnogain.model.DeleteObject;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.repositories.DeleteObjectRepository;
import com.codecool.nopainnogain.repositories.DeletedObjectType;
import com.codecool.nopainnogain.repositories.WorkoutRepositorySynced;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkoutController {

    @Autowired
    private WorkoutRepositorySynced workoutRepository;

    @Autowired
    private DeleteObjectRepository deleteObjectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/workout-updates/{timestamp}", method = RequestMethod.GET)
    public String getAllWorkoutUpdatedSince(@PathVariable("timestamp") Long timestamp){
        String result = "";
        objectMapper.enableDefaultTyping();

        try {
            result = objectMapper.writerFor(new TypeReference<List<Workout>>() { })
                    .writeValueAsString(workoutRepository.getAllUpdatedWorkoutSince(timestamp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/workout-deletes/{timestamp}", method = RequestMethod.GET)
    public String getAllWorkoutDeletesSince(@PathVariable("timestamp") Long timestamp){
        List<Long> ids = new ArrayList<>();
        objectMapper.enableDefaultTyping();
        for(DeleteObject del: deleteObjectRepository.getDeletedObjectsSince(timestamp, DeletedObjectType.WORKOUT)){
            ids.add(del.getDeletedObjectId());
        }

        String result = null;

        try {
            result = objectMapper.writerFor(new TypeReference<List<Long>>() {}).writeValueAsString(ids);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value="/workouts/{id}")
    public String getWorkoutById(@PathVariable("id") Long id){
        Workout workout = workoutRepository.getById(id);
        String s = null;
        try {
            s = objectMapper.writeValueAsString(workout);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping(value="/workouts/{id}", method = RequestMethod.POST)
    public void saveWorkoutUpdate(@RequestBody String json){
        System.out.println("workout to update\n" + json);
        Workout workout = null;
        try {
            workout = objectMapper.readValue(json,Workout.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(workout != null){
            workoutRepository.save(workout);
        }
    }

    @RequestMapping(value = "/workouts/names")
    public String getAllWorkoutNames(){
        List<Workout> all = workoutRepository.getAllWorkouts();
        ArrayNode arr =  JsonNodeFactory.instance.arrayNode();

        for(Workout w: all){
            ObjectNode obj = JsonNodeFactory.instance.objectNode();
            obj.put("name",w.getTitle());
            obj.put("id", w.getId());
            arr.add(obj);
        }
        return arr.toString();
    }

    @RequestMapping(value = "/workouts/{id}", method = RequestMethod.DELETE)
    public void deleteWorkout(@PathVariable("id") Long id){
        System.out.println("delete this: " + id);
        this.workoutRepository.deleteById(id);
    }

    @RequestMapping(value = "/workouts/add-new")
    public String addNewWorkout(){
        Workout newWorkout = new Workout("New Workout");
        this.workoutRepository.save(newWorkout);

        System.out.println(newWorkout.getId());

        return "{\"id\":\"" + newWorkout.getId() + "\"}";

    }

}
