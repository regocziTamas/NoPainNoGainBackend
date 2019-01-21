package com.codecool.nopainnogain.controller;

import com.codecool.nopainnogain.model.DeleteObject;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.ExerciseTarget;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.repositories.DeleteObjectRepository;
import com.codecool.nopainnogain.repositories.DeletedObjectType;
import com.codecool.nopainnogain.repositories.ExerciseRepositorySynced;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class ExerciseController {

    @Autowired
    private ExerciseRepositorySynced exerciseRepository;

    @Autowired
    private DeleteObjectRepository deleteObjectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/exercise-updates/{timestamp}", method = RequestMethod.GET)
    public String getAllExercisesUpdatedSince(@PathVariable("timestamp") Long timestamp){
        String result = "";
        objectMapper.enableDefaultTyping();

        try {
            result = objectMapper.writerFor(new TypeReference<List<Exercise>>() { })
                    .writeValueAsString(exerciseRepository.getAllExercisesUpdatedSince(timestamp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;

    }

    @RequestMapping(value = "/exercise-deletes/{timestamp}", method = RequestMethod.GET)
    public String getAllExerciseDeletesSince(@PathVariable("timestamp") Long timestamp){
        List<Long> ids = new ArrayList<>();
        objectMapper.enableDefaultTyping();
        for(DeleteObject del: deleteObjectRepository.getDeletedObjectsSince(timestamp, DeletedObjectType.EXERCISE)){
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

    @RequestMapping(value = "/exercises/all")
    public String getAllExercises(){
        List<Exercise> exercises = exerciseRepository.getAllExercises();
        String result = "";
        try {
            result = objectMapper.writeValueAsString(exercises);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/targets/all")
    public String getAllTargets(){
        List<ExerciseTarget> targets = new ArrayList<>(Arrays.asList(ExerciseTarget.values()));
        String result = "";
        try {
            result = objectMapper.writeValueAsString(targets);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/exercises/add")
    public void addNewExercise(){
        Exercise newExercise = new Exercise("New Exercise", "Description", ExerciseTarget.Chest);
        exerciseRepository.save(newExercise);
    }

    @RequestMapping(value = "/exercises/save", method = RequestMethod.POST)
    public ResponseEntity<Object> saveExercise(@RequestBody String jsonString){
        Exercise updatedExercise = null;
        try {
            updatedExercise = objectMapper.readValue(jsonString, Exercise.class);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        exerciseRepository.save(updatedExercise);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/exercises/{id}", method = RequestMethod.DELETE)
    public void deleteExercise(@PathVariable("id") Long id){
        System.out.println("Delete: "+id);
        exerciseRepository.deleteById(id);
    }


}
