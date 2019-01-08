package com.codecool.nopainnogain;

import com.codecool.nopainnogain.model.*;
import com.codecool.nopainnogain.repositories.ExerciseRepository;
import com.codecool.nopainnogain.repositories.ExerciseRepositorySynced;
import com.codecool.nopainnogain.repositories.WorkoutRepositorySynced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Main {

    @Autowired
    private ExerciseRepositorySynced exerciseRepository;

    @Autowired
    private WorkoutRepositorySynced workoutRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner init(){
        return (String... args) -> {
            Exercise ex1 = new Exercise("Push-up", "You do some push-ups, it is easy", ExerciseTarget.Chest);
            Exercise ex2 = new Exercise("Barbell Curl", "Stand up with your torso upright while holding a barbell at a shoulder-width grip. The palm of your hands should be facing forward and the elbows should be close to the torso. This will be your starting position.", ExerciseTarget.Biceps);
            Exercise ex3 = new Exercise("Bench Dip", "For this exercise you will need to place a bench behind your back. With the bench perpendicular to your body, and while looking away from it, hold on to the bench on its edge with the hands fully extended, separated at shoulder width. The legs will be extended forward, bent at the waist and perpendicular to your torso. This will be your starting position.", ExerciseTarget.Triceps);
            Exercise ex4 = new Exercise("Regular Grip Front Lat Pulldown", "Sit down on a pull-down machine with a wide bar attached to the top pulley. Make sure that you adjust the knee pad of the machine to fit your height. These pads will prevent your body from being raised by the resistance attached to the bar.", ExerciseTarget.Back);
            Exercise ex5 = new Exercise("Ab Roller", "Slowly roll the ab roller straight forward, stretching your body into a straight position. Tip: Go down as far as you can without touching the floor with your body. Breathe in during this portion of the movement.", ExerciseTarget.Abs);
            Exercise ex6 = new Exercise("Barbell Full Squat", "This exercise is best performed inside a squat rack for safety purposes. To begin, first set the bar on a rack just above shoulder level. Once the correct height is chosen and the bar is loaded, step under the bar and place the back of your shoulders (slightly below the neck) across it.", ExerciseTarget.Quads);
            Exercise ex7 = new Exercise("Standing Military Press", "Start by placing a barbell that is about chest high on a squat rack. Once you have selected the weights, grab the barbell using a pronated (palms facing forward) grip. Make sure to grip the bar wider than shoulder width apart from each other.", ExerciseTarget.Shoulders);
            Exercise ex8 = new Exercise("Seated Calf Raise", "Place your lower thighs under the lever pad, which will need to be adjusted according to the height of your thighs. Now place your hands on top of the lever pad in order to prevent it from slipping forward.", ExerciseTarget.Calves);
            Exercise ex9 = new Exercise("Cable Wrist Curl", "Use your arms to grab the cable bar with a narrow to shoulder width supinated grip (palms up) and bring them up so that your forearms are resting against the top of your thighs. Your wrists should be hanging just beyond your knees.", ExerciseTarget.Forearms);

            for (Exercise e : Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9)) {
                exerciseRepository.save(e);
            }

            List<Exercise> allExercises = exerciseRepository.getAllExercises();

            WorkoutExercise wex1 = new WorkoutExercise(10,allExercises.get(4));
            WorkoutExercise wex2 = new WorkoutExercise(10,allExercises.get(5));
            WorkoutExercise wex3 = new WorkoutExercise(10,allExercises.get(6));

            Rest rest1 = new Rest(3000);
            Rest rest2 = new Rest(3000);

            WorkoutBlock wb1 = new WorkoutBlock();
            wb1.addComponent(wex1);
            wb1.addComponent(rest1);
            wb1.addComponent(wex2);
            wb1.addComponent(rest2);
            wb1.addComponent(wex3);

            Workout testWorkout = new Workout("Short Test Workout");

            testWorkout.addBlock(wb1);

            workoutRepository.save(testWorkout);

            System.out.println(workoutRepository.getById(1L));


        };
    }

}

