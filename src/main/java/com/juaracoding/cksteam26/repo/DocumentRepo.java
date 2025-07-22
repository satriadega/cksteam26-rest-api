package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.16
@Last Modified 23/07/25 03.16
Version 1.0
*/

import com.juaracoding.cksteam26.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Long> {
}
