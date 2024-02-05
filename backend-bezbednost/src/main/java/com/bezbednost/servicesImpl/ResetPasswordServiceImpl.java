package com.bezbednost.servicesImpl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bezbednost.email.EmailService;
import com.bezbednost.models.Consumer;
import com.bezbednost.models.ResetPassword;
import com.bezbednost.models.ResetPasswordDto;
import com.bezbednost.repositories.ConsumerRepository;
import com.bezbednost.repositories.ResetPasswordRepository;
import com.bezbednost.services.ResetPasswordService;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService{
	
	@Autowired
	private ResetPasswordRepository resetPasswordRepository;
	
	@Autowired
    private ConsumerRepository consumerRepository;
	 
	@Autowired
	private PasswordEncoder passwordEncoder;
	 
	@Autowired
	private EmailService emailService;

	@Override
	public ResetPassword resetOldPassword(ResetPasswordDto resetPasswordDto) {
		  Consumer consumer = consumerRepository.findByEmail(resetPasswordDto.getEmail());
		  if(consumer!=null) {
			  String token = UUID.randomUUID().toString();
	          ResetPassword resetPassword = new ResetPassword();
	          resetPassword.setToken(token);
	          resetPassword.setCreatedAt(LocalDateTime.now());
	          resetPassword.setExpiredAt(LocalDateTime.now().plusMinutes(5));
	          resetPassword.setConsumer(consumer);
	          resetPassword.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
	          String link = "http://localhost:8080/api/confirm-reset-password?token=" + token;
	          emailService.send(consumer.getEmail(),
	                  emailService.buildEmail(consumer.getFirstName(),link));
	          return resetPasswordRepository.save(resetPassword);
		  }
          return null;

          
	}

	@Override
	public String confirmationPasswordToken(String token) {
	 	 ResetPassword resetPassword = resetPasswordRepository.findByToken(token);
	 	 
	 	 if(resetPassword == null || resetPassword.getConfirmedAt() != null || 
        		 resetPassword.getExpiredAt().isBefore(LocalDateTime.now())){
             throw new IllegalStateException("Email or token is not valid or token is expired");
         }
         Consumer consumer = resetPassword.getConsumer();
         consumer.setPassword(resetPassword.getPassword());
         resetPasswordRepository.updateDatumPotvrde(token, LocalDateTime.now());
         consumerRepository.save(consumer);
         return "Consumer successfully reseted password";
	}

}
