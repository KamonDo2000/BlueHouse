package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.BlueHouse.entities.Position;

public interface PositionRepositories extends JpaRepository<Position, String> {
	@Query("FROM Position where idPosition Like %:seacrch% or namePosition Like %:seacrch% or prorogue LIKE %:seacrch% ")
	public List<Position> searchPosi(String seacrch);
	
	@Query("FROM Position where idPosition Like %:seacrch% or namePosition Like %:seacrch% or prorogue LIKE %:seacrch% ")
	public Page<Position> searchPosition(String seacrch, Pageable pageable);
   
	@Query("SELECT MAX(i.idIncomeBill) FROM IncomeBill i")
    String findMaxId();
}
