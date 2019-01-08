package com.codecool.nopainnogain.model;




public class WorkoutExercise extends WorkoutComponent{

    int reps;
    Exercise exercise;



    public WorkoutExercise(int reps, Exercise exercise) {
        this.reps = reps;
        this.exercise = exercise;
    }

    public WorkoutExercise(){}

    public int getReps() {
        return reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    @Override
    public String toString() {
        return reps + " x " + exercise.getName();
    }



    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}


