package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExerciseRepositorySynced {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public void save(Exercise ex){
        ex.setLastUpdated(System.currentTimeMillis());
        exerciseRepository.save(ex);
    }

    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }

    public List<Exercise> getAllExercisesUpdatedSince(Long timestamp){
        return exerciseRepository.getAllUpdatedExercisesSince(timestamp);
    }




}
