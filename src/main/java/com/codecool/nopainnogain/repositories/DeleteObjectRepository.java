package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.model.DeleteObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeleteObjectRepository extends JpaRepository<DeleteObject, Long> {

   // @Query("SELECT delObj FROM DeleteObject delObj WHERE delObj.deleteTimestamp > :deleteTimestamp AND delObj.deletedObjectType = :objectType")
   @Query("SELECT delObj FROM deleteobject delObj WHERE delObj.deleteTimestamp > :deleteTimestamp AND delObj.deletedObjectType = :objectType")
   List<DeleteObject> getDeletedObjectsSince(@Param("deleteTimestamp") Long timestamp, @Param("objectType") DeletedObjectType workout);
}
