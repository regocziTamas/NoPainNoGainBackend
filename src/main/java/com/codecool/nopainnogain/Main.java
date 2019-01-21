package com.codecool.nopainnogain;


import com.codecool.nopainnogain.auth.NPNGAdmin;
import com.codecool.nopainnogain.model.*;
import com.codecool.nopainnogain.repositories.AdminRepository;
import com.codecool.nopainnogain.repositories.ExerciseRepositorySynced;
import com.codecool.nopainnogain.repositories.WorkoutRepositorySynced;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Main {

    @Autowired
    private ExerciseRepositorySynced exerciseRepository;

    @Autowired
    private WorkoutRepositorySynced workoutRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner init(){
        return (String... args) -> {

            NPNGAdmin admin = new NPNGAdmin();
            admin.setUsername("thomaster");
            admin.setPassword(bCryptPasswordEncoder().encode("gtasamp24"));
            adminRepository.save(admin);




            Exercise ex2 = new Exercise("Barbell Curl", "Stand up with your torso upright while holding a barbell at a shoulder-width grip. The palm of your hands should be facing forward and the elbows should be close to the torso. This will be your starting position.", ExerciseTarget.Biceps);
            Exercise ex3 = new Exercise("Bench Dip", "For this exercise you will need to place a bench behind your back. With the bench perpendicular to your body, and while looking away from it, hold on to the bench on its edge with the hands fully extended, separated at shoulder width. The legs will be extended forward, bent at the waist and perpendicular to your torso. This will be your starting position.", ExerciseTarget.Triceps);
            Exercise ex4 = new Exercise("Regular Grip Front Lat Pulldown", "Sit down on a pull-down machine with a wide bar attached to the top pulley. Make sure that you adjust the knee pad of the machine to fit your height. These pads will prevent your body from being raised by the resistance attached to the bar.", ExerciseTarget.Back);
            Exercise ex5 = new Exercise("Ab Roller", "Slowly roll the ab roller straight forward, stretching your body into a straight position. Tip: Go down as far as you can without touching the floor with your body. Breathe in during this portion of the movement.", ExerciseTarget.Abs);
            Exercise ex6 = new Exercise("Barbell Full Squat", "This exercise is best performed inside a squat rack for safety purposes. To begin, first set the bar on a rack just above shoulder level. Once the correct height is chosen and the bar is loaded, step under the bar and place the back of your shoulders (slightly below the neck) across it.", ExerciseTarget.Quads);
            Exercise ex7 = new Exercise("Standing Military Press", "Start by placing a barbell that is about chest high on a squat rack. Once you have selected the weights, grab the barbell using a pronated (palms facing forward) grip. Make sure to grip the bar wider than shoulder width apart from each other.", ExerciseTarget.Shoulders);
            Exercise ex8 = new Exercise("Seated Calf Raise", "Place your lower thighs under the lever pad, which will need to be adjusted according to the height of your thighs. Now place your hands on top of the lever pad in order to prevent it from slipping forward.", ExerciseTarget.Calves);
            Exercise ex9 = new Exercise("Cable Wrist Curl", "Use your arms to grab the cable bar with a narrow to shoulder width supinated grip (palms up) and bring them up so that your forearms are resting against the top of your thighs. Your wrists should be hanging just beyond your knees.", ExerciseTarget.Forearms);
            Exercise ex1 = new Exercise("Push-up", "You do some push-ups, it is easy", ExerciseTarget.Chest);

            for (Exercise e : Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9)) {
                exerciseRepository.save(e);
            }

            List<Exercise> allExercises = exerciseRepository.getAllExercises();

            WorkoutExercise wex1 = new WorkoutExercise(10,allExercises.get(1));
            WorkoutExercise wex2 = new WorkoutExercise(10,allExercises.get(3));
            WorkoutExercise wex3 = new WorkoutExercise(10,allExercises.get(7));
            WorkoutExercise wex4 = new WorkoutExercise(10,allExercises.get(2));
            WorkoutExercise wex5 = new WorkoutExercise(10,allExercises.get(5));
            WorkoutExercise wex6 = new WorkoutExercise(10,allExercises.get(8));

            Rest rest1 = new Rest(3000);
            Rest rest2 = new Rest(3000);
            Rest rest3 = new Rest(3000);
            Rest rest4 = new Rest(3000);

            WorkoutBlock wb1 = new WorkoutBlock();
            wb1.addComponent(wex1);
            wb1.addComponent(rest1);
            wb1.addComponent(wex2);
            wb1.addComponent(rest2);
            wb1.addComponent(wex3);

            WorkoutBlock wb2 = new WorkoutBlock();
            wb2.addComponent(wex4);
            wb2.addComponent(rest3);
            wb2.addComponent(wex5);
            wb2.addComponent(rest4);
            wb2.addComponent(wex6);

            Workout testWorkout = new Workout("Short Synced Test Workout");

            testWorkout.addBlock(wb1);
            testWorkout.addBlock(wb2);

            workoutRepository.save(testWorkout);



            String s1 = new ObjectMapper().writeValueAsString(testWorkout);
            System.out.println(s1);

            String s2 = "{\"title\":\"Short Synced Test Workout\",\"blocks\":[{\"components\":[{\"type\":\"WorkoutExercise\",\"order\":0,\"reps\":10,\"exercise\":{\"name\":\"Barbell Curl\",\"description\":\"Stand up with your torso upright while holding a barbell at a shoulder-width grip. The palm of your hands should be facing forward and the elbows should be close to the torso. This will be your starting position.\",\"target\":\"Biceps\",\"id\":2}},{\"type\":\"Rest\",\"order\":1,\"durationInMilis\":3000},{\"type\":\"WorkoutExercise\",\"order\":2,\"reps\":10,\"exercise\":{\"name\":\"Regular Grip Front Lat Pulldown\",\"description\":\"Sit down on a pull-down machine with a wide bar attached to the top pulley. Make sure that you adjust the knee pad of the machine to fit your height. These pads will prevent your body from being raised by the resistance attached to the bar.\",\"target\":\"Back\",\"id\":4}},{\"type\":\"Rest\",\"order\":3,\"durationInMilis\":3000},{\"type\":\"WorkoutExercise\",\"order\":4,\"reps\":10,\"exercise\":{\"name\":\"Seated Calf Raise\",\"description\":\"Place your lower thighs under the lever pad, which will need to be adjusted according to the height of your thighs. Now place your hands on top of the lever pad in order to prevent it from slipping forward.\",\"target\":\"Calves\",\"id\":8}}],\"order\":0}],\"id\":1,\"empty\":false}";

            Workout fuckme = new ObjectMapper().readValue(s2,new TypeReference<Workout>() {} );

            System.out.println(fuckme);

            /*exerciseRepository.deleteExercise(exerciseRepository.getAllExercises().get(1));
            exerciseRepository.deleteExercise(exerciseRepository.getAllExercises().get(2));
*/
            /*Problem: if an exercise is updated, the workout does not update its version of the exercise as it is
            * stored as json string and it is recreated from the json string, it has no connection to the exercise table.
            * Put Json ignore on Exercise field in WorkoutExercise, make a variable to store the exercise id,
            * then in workoutblock converter when its converted back collect the exercise ids that are needed
            * make a query with 'where id in ...' to get them in one go and set them in the WorkoutExercise objects,
            * because they are not initialized as they were ignored. Add this field to the angular model as well and set
            * them when an exercise is changed in a WorkoutExercise. Also implement this on android as well
            * Also handle if a workout contains an exercise that has been deleted. The deleted exercises should
            * default to pushups or something.*/



        };
    }

}

