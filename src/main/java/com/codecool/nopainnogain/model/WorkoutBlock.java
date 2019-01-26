package com.codecool.nopainnogain.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class WorkoutBlock{

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<WorkoutComponent> components = new ArrayList<>();
    private int orderId;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*private static ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping();*/

    public WorkoutBlock(){

    }

    public void addComponent(WorkoutComponent component){
        component.setOrderId(components.size());
        components.add(component);
    }

    public void prepareForJSON(){
        ArrayList arr = new ArrayList();
        arr.addAll(components);
        components = arr;
    }

    public List<WorkoutComponent> getComponents(){
        Collections.sort(components,new WorkoutComponentComparator());
        return components;
    }

    public void setComponents(List<WorkoutComponent> components) {
        this.components = components;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String toString = "";

        for(WorkoutComponent comp: getComponents()){
            toString+=comp.toString()+"\n";
        }
        return toString;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*public static String toJsonString(WorkoutBlock block){
        String string = null;
        try {
            string = objectMapper.writeValueAsString(block);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static WorkoutBlock toWorkoutBlockObject(String string){
        WorkoutBlock block = null;
        try {
            block = objectMapper.readValue(string,WorkoutBlock.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return block;
    }*/

    public void reorderComponents(){
        for(int i = 0; i < getComponents().size(); i++){
            WorkoutComponent component = getComponents().get(i);
            component.setOrderId(i);
        }
    }

    public void cloneComponent(WorkoutComponent component){
        if(component instanceof WorkoutExercise){
            WorkoutExercise ex = (WorkoutExercise) component;
            addComponent(new WorkoutExercise(ex.getReps(),ex.getExercise()));
        }else if(component instanceof Rest){
            Rest rest = (Rest) component;
            addComponent(new Rest(rest.getDurationInMilis()));
        }
    }

    public void swapTwoComponents(int order1, int order2){
        for(WorkoutComponent component: getComponents()){
            if(component.getOrderId() == order1){
                component.setOrderId(order2);
            }else if(component.getOrderId() == order2){
                component.setOrderId(order1);
            }
        }
    }

    public void deleteComponentByOrder(int i){
        for(WorkoutComponent component: components){
            if(component.getOrderId() == i){
                components.remove(component);
                reorderComponents();
            }
        }

    }

    class WorkoutComponentComparator implements java.util.Comparator<WorkoutComponent>{

        @Override
        public int compare(WorkoutComponent t1, WorkoutComponent t2) {
            return t1.getOrderId() - t2.getOrderId();
        }
    }
}
