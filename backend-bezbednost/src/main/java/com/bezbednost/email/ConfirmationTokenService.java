package com.bezbednost.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveEmailToken(ConfirmationToken confirmationToken){

        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getConfirmationToken(String token){

        return confirmationTokenRepository.findByToken(token);
    }

    public int setDatumPotvrde(String token){

        return  confirmationTokenRepository.updateDatumPotvrde(token, LocalDateTime.now());
    }
}
