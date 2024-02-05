package com.bezbednost.email;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    ConfirmationToken findByToken(String token);

    @Transactional
    @Modifying
    @Query("update ConfirmationToken e set e.confirmedAt=?2 where e.token=?1")
    int updateDatumPotvrde(String token, LocalDateTime datumPotvrde);
}
