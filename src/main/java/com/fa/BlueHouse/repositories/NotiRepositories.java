package com.fa.BlueHouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fa.BlueHouse.entities.Notification;

public interface NotiRepositories extends JpaRepository<Notification, String> {

}
