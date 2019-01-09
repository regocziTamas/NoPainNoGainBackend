package com.codecool.nopainnogain.controller;

import com.codecool.nopainnogain.model.DeleteObject;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.repositories.DeleteObjectRepository;
import com.codecool.nopainnogain.repositories.DeletedObjectType;
import com.codecool.nopainnogain.repositories.WorkoutRepositorySynced;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
/*
    select deleteobje0_.id as id1_0_,
        deleteobje0_.delete_timestamp as delete_t2_0_,
        deleteobje0_.deleted_object_id as deleted_3_0_,
        deleteobje0_.deleted_object_type as deleted_4_0_
        from
        deleteobject deleteobje0_
        where deleteobje0_.delete_timestamp>? and deleteobje0_.deleted_object_type=?


*/