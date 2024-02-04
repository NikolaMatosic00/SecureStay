package com.bezbednost.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bezbednost.models.ResetPassword;

import jakarta.transaction.Transactional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
	
	ResetPassword findById(long id);
	
	ResetPassword findByToken(String token);
	
	@Transactional
    @Modifying
    @Query("update ResetPassword e set e.confirmedAt=?2 where e.token=?1")
    int updateDatumPotvrde(String token, LocalDateTime datumPotvrde);

}
