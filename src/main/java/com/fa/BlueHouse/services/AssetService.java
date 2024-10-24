package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Assets;
import com.fa.BlueHouse.repositories.AssetRepository;

@Service
public class AssetService {
	@Autowired
	AssetRepository assetRepository;
	
	public Page<Assets> showAll(Pageable pageable){
		return assetRepository.findAll(pageable);
	}
	public void save(Assets assets) {
		assetRepository.save(assets);
	}
	public void delete(Assets assets) {
		assetRepository.delete(assets);
	}
	public Assets findById(String id) {
		return assetRepository.findById(id).orElse(null);
	}
	public List<Assets> findAll(){
		return assetRepository.findAll();
	}
	
	public Page<Assets> findByKeyword(Pageable pageable, String keyword){
		return assetRepository.findByKeyword(keyword, pageable);
	}
	public String generateNewId() {
		String maxId = assetRepository.findMaxId();
		
		if(maxId == null) return "AS001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("AS%03d", numberic);
	}
	public List<Assets> findByIds(List<String> id){
		return assetRepository.findAllById(id);
	}
}
