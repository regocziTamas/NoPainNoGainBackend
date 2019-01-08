package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.model.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class WorkoutRepositorySynced{

    @Autowired
    WorkoutRepository workoutRepository;

    public void save(Workout workout){
        workout.setLastUpdated(System.currentTimeMillis());
        workoutRepository.save(workout);
    }

    @Transactional
    public Workout getById(Long id){
        return workoutRepository.getById(id);
    }

    @Transactional
    public List<Workout> getAllUpdatedWorkoutSince(Long timestamp){
        return workoutRepository.getAllUpdatedExercisesSince(timestamp);
    }

}
