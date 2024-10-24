package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.Event;
import com.fa.BlueHouse.entities.Participants;

public interface ParticipantRepo extends JpaRepository<Participants, String> {
	
	@Query("SELECT p FROM Participants p WHERE p.IDEvent.idEvent = :idEvent AND (p.participantEmp.employeeID = :idParti OR p.participantResi.idResident = :idParti) ")
	List<Participants> checkExist(@Param("idEvent") String idEvent, @Param("idParti") String idParti);
	
	@Query("SELECT p FROM Participants p WHERE p.IDEvent.idEvent = :eventID ")
	Page<Participants> findAllPartiByEvent(@Param("eventID") String eventID, Pageable pageable);
	
	@Query("SELECT p FROM Participants p WHERE p.IDEvent.idEvent = :eventID ")
	List<Participants> findListPartiByEvent(@Param("eventID") String eventID);
	
	@Query("SELECT p.IDEvent FROM Participants p WHERE p.participantEmp.employeeID = :myID OR p.participantResi.idResident = :myID ORDER BY p.IDEvent.startTime DESC ")
	Page<Event> findEventMyJoin(@Param("myID") String myID, Pageable pageable);
	
}
