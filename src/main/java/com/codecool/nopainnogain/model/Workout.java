package com.codecool.nopainnogain.model;




import com.codecool.nopainnogain.converter.WorkoutBlockConverter;
import com.codecool.nopainnogain.repositories.DeletedObjectType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Entity(name = "workout")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Workout{
    private String title;
    @Lob
    @Convert(converter = WorkoutBlockConverter.class)
    private List<WorkoutBlock> blocks = new ArrayList<>();
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private Long lastUpdated;
    private static ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping();

    public Workout(String title) {
        this.title = title;
    }

    public Workout(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void addBlock(WorkoutBlock block){
        block.setOrder(blocks.size());
        blocks.add(block);
    }

    @JsonIgnore
    public List<WorkoutBlock> getBlocksForListing(){
        Collections.sort(blocks, new WorkoutBlockComparator());
        return blocks;
    }

    @JsonIgnore
    public List<WorkoutComponent> getBlocksForWorkoutDisplay(){
        List<WorkoutComponent> components = new ArrayList<>();

        for(int i = 0; i < blocks.size(); i++){
            components.addAll(blocks.get(i).getComponents());
        }
        return components;
    }

    public boolean isEmpty(){
        boolean empty = true;
        for(WorkoutBlock block: blocks){
            if(!block.getComponents().isEmpty()){
                empty = false;
                break;
            }
        }
        return empty;
    }

    public void deleteBlockByOrder(int order){
        blocks.remove(order);
    }

    public void swapBlocks(int order1, int order2){
        blocks.get(order1).setOrder(order2);
        blocks.get(order2).setOrder(order1);
    }

    public List<WorkoutBlock> getBlocks() {
        return getBlocksForListing();
    }

    public void setBlocks(List<WorkoutBlock> blocks) {
        this.blocks = blocks;
    }

    public static String toJsonString(Workout workout){
        String string = null;

        try {
            string = objectMapper.writeValueAsString(workout);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static Workout toWorkoutObject(String string){
        Workout workout = null;
        try {
            workout = objectMapper.readValue(string,Workout.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workout;
    }

    @Override
    public String toString() {
        String toString = "ID: " + id + "\n";

        for(WorkoutBlock block: blocks){
            toString += block.toString();
        }

        return toString;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void replaceBlockById(int id, WorkoutBlock newBlock){
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i).getOrder() == id){
                blocks.set(i,newBlock);
            }
        }
    }



    class WorkoutBlockComparator implements Comparator<WorkoutBlock> {

        @Override
        public int compare(WorkoutBlock block, WorkoutBlock t1) {
            return block.getOrder() - t1.getOrder();
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
