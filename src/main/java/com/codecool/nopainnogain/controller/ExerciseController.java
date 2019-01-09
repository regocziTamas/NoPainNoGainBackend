package com.codecool.nopainnogain.controller;

import com.codecool.nopainnogain.model.DeleteObject;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.repositories.DeleteObjectRepository;
import com.codecool.nopainnogain.repositories.DeletedObjectType;
import com.codecool.nopainnogain.repositories.ExerciseRepositorySynced;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String getAllWorkoutDeletesSince(@PathVariable("timestamp") Long timestamp){
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

}
