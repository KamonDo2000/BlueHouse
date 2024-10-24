package com.fa.BlueHouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fa.BlueHouse.entities.Feedback;

public interface FeedbackRepository  extends JpaRepository<Feedback, String>{

}
