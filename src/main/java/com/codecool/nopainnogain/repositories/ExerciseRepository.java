package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

    @Transactional
    public Exercise getById(Long id);

    @Query("SELECT ex FROM exercise ex WHERE ex.lastUpdated > :timestamp")
    public List<Exercise> getAllUpdatedExercisesSince(@Param("timestamp") Long timestamp);

    @Transactional
    public void deleteById(Long id);



}
