package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Event;
import com.fa.BlueHouse.entities.Participants;
import com.fa.BlueHouse.repositories.ParticipantRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ParticipantService {

	@Autowired
	private ParticipantRepo partiRepo;

	public void saveParticipant(Participants par) {
		partiRepo.save(par);
	}

	public boolean isExist(String idEvent, String idParti) {
		return partiRepo.checkExist(idEvent, idParti).isEmpty();
	}
	
	public Page<Participants> findAllPartiByEvent(String eventID, Pageable pageable) {
		return partiRepo.findAllPartiByEvent(eventID, pageable);
	}
	
	public List<Participants> findListPartiByEvent(String eventID) {
		return partiRepo.findListPartiByEvent(eventID);
	}
	
	public void deleteByID(String id) {
		partiRepo.deleteById(id);
	}
	
	public Participants findByID(String id) {
		return partiRepo.findById(id).orElse(null);
	}

	public Page<Event> findEventMyJoin(String myId, Pageable pageable) {
		return partiRepo.findEventMyJoin(myId, pageable);
	}
	
}
