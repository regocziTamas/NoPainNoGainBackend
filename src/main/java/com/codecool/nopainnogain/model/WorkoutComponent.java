package com.codecool.nopainnogain.model;




import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WorkoutExercise.class, name = "WorkoutExercise"),
        @JsonSubTypes.Type(value = Rest.class, name = "Rest")
})
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class WorkoutComponent {

    int orderId;
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;

    public WorkoutComponent(){}

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
