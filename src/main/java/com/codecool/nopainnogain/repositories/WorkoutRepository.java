package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {
}
