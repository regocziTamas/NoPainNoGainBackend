package com.codecool.nopainnogain.model;

import com.codecool.nopainnogain.repositories.DeletedObjectType;

import javax.persistence.*;

@Entity(name = "deleteobject")
public class DeleteObject {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long deletedObjectId;
    private Long deleteTimestamp;
    @Enumerated(EnumType.STRING)
    private DeletedObjectType deletedObjectType;

    public DeleteObject() {}

    public DeleteObject(Long deletedObjectId, Long timestamp, DeletedObjectType type) {
        this.deletedObjectId = deletedObjectId;
        this.deleteTimestamp = timestamp;
        this.deletedObjectType = type;
    }

    public Long getDeletedObjectId() {
        return deletedObjectId;
    }

    public Long getTimestamp() {
        return deleteTimestamp;
    }

    public DeletedObjectType getdeletedObjectType() {
        return deletedObjectType;
    }
}
