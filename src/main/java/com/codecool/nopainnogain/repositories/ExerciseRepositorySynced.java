package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.model.DeleteObject;
import com.codecool.nopainnogain.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ExerciseRepositorySynced {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private DeleteObjectRepository deleteObjectRepository;

    public void save(Exercise ex){
        ex.setLastUpdated(System.currentTimeMillis());
        exerciseRepository.save(ex);
    }

    public void deleteById(Long id){
        deleteObjectRepository.save(new DeleteObject(id,System.currentTimeMillis(),DeletedObjectType.EXERCISE));
        exerciseRepository.deleteById(id);
    }

    public void deleteExercise(Exercise ex){
        deleteById(ex.getId());
    }

    @Transactional
    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }

    @Transactional
    public List<Exercise> getAllExercisesUpdatedSince(Long timestamp){
        return exerciseRepository.getAllUpdatedExercisesSince(timestamp);
    }




}
