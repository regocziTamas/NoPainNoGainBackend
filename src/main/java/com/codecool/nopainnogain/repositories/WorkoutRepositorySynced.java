package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.model.DeleteObject;
import com.codecool.nopainnogain.model.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class WorkoutRepositorySynced{

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private DeleteObjectRepository deleteObjectRepository;

    public void save(Workout workout){
        workout.setLastUpdated(System.currentTimeMillis());
        workoutRepository.save(workout);
    }

    public void deleteById(Long id){
        deleteObjectRepository.save(new DeleteObject(id,System.currentTimeMillis(),DeletedObjectType.WORKOUT));
        workoutRepository.deleteById(id);
    }

    public void delete(Workout w){
        deleteById(w.getId());
    }

    @Transactional
    public Workout getById(Long id){
        return workoutRepository.getById(id);
    }

    @Transactional
    public List<Workout> getAllUpdatedWorkoutSince(Long timestamp){
        return workoutRepository.getAllUpdatedWorkoutsSince(timestamp);
    }

}
