package com.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezbednost.models.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
	
	Consumer findById(long id);
	Consumer findByEmail(String emil);

}
