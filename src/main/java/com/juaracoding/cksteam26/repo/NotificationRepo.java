package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 06/08/25 02.01
@Last Modified 06/08/25 02.01
Version 1.0
*/

import com.juaracoding.cksteam26.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
}
