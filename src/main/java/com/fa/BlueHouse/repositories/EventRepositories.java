package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.Event;

public interface EventRepositories extends JpaRepository<Event, String> {

	@Query("SELECT e FROM Event e WHERE e.IDOganizer.idResident = :idResi ORDER BY e.startTime DESC")
	Page<Event> getAllEventMyCreate(@Param("idResi") String idResi, Pageable pageable);

	@Query("SELECT e FROM Event e WHERE e.IDOganizer.idResident = :idResi ")
	List<Event> getAllEventMyCreate(@Param("idResi") String idResi);

}
