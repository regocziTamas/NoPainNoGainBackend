package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {

    @Transactional
    public Workout getById(Long id);

    @Query("SELECT w from workout w WHERE w.lastUpdated > :timestamp")
    public List<Workout> getAllUpdatedWorkoutsSince( @Param("timestamp") Long timestamp);

    @Transactional
    public void deleteById(Long id);
}
