package com.fa.BlueHouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Feedback;
import com.fa.BlueHouse.repositories.FeedbackRepository;

@Service
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepository;
	
	public Page<Feedback> showAll(Pageable pageable){
		return feedbackRepository.findAll(pageable);
	}
}
