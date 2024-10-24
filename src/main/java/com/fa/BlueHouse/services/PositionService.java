package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Position;
import com.fa.BlueHouse.repositories.PositionRepositories;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepositories position;
	
	public void savePosition(Position posi) {
		position.save(posi);
	}
	
	public void deletePosition(String posi) {
		position.deleteById(posi);
	}
	
	public Position findById(String id) {
		return position.findById(id).orElse(null);
	}
	
	public List<Position> findall(){
		return position.findAll();
	}
	
	public Page<Position> searchPosition(String search, Pageable pageable){
		return position.searchPosition(search, pageable);
	}
	
	public Page<Position> findpagePosition(Pageable page){
		return position.findAll(page);
	}
	
	public String generateNewId() {
		String maxId = position.findMaxId();
		
		if(maxId == null) return "PS001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("PS%03d", numberic);
	}
}
