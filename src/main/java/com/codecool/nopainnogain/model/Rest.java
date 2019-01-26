package com.codecool.nopainnogain.model;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Rest extends WorkoutComponent{

    int durationInMilis;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rest(int durationInMilis) {
        this.durationInMilis = durationInMilis;
    }

    public Rest(){}

    public int getDurationInMilis() {
        return durationInMilis;
    }

    public void setDurationInMilis(int durationInMilis) {
        this.durationInMilis = durationInMilis;
    }

    @Override
    public String toString() {
        return durationInMilis/1000 + " seconds of rest";
    }


}
