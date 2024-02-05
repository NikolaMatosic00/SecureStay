package com.bezbednost.servicesImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bezbednost.email.ConfirmationToken;
import com.bezbednost.email.ConfirmationTokenService;
import com.bezbednost.email.EmailService;
import com.bezbednost.models.Consumer;
import com.bezbednost.models.ConsumerDto;
import com.bezbednost.repositories.ConsumerRepository;
import com.bezbednost.services.ConsumerService;

@Service
public class ConsumerServiceImpl implements UserDetailsService, ConsumerService {
	
	@Autowired
    private ConsumerRepository consumerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Consumer consumer = consumerRepository.findByEmail(email);
		if(consumer == null || consumer.isStatus() == false){
            throw new UsernameNotFoundException("Korisnik nije registrovan ili nije aktivirao svoj nalog preko email-a");
        }
		else {
			 Collection<SimpleGrantedAuthority> authority = new ArrayList<>();
		     authority.add(new SimpleGrantedAuthority(consumer.getRole().toString()));
		     return new User(consumer.getEmail(),consumer.getPassword(), authority);
		}
       
	}

	@Override
	public Consumer registration(ConsumerDto consumerDto) {
		Consumer consumer = new Consumer();
		consumer.setFirstName(consumerDto.getFirstName());
		consumer.setLastName(consumerDto.getLastName());
		consumer.setEmail(consumerDto.getEmail());
		consumer.setPassword(passwordEncoder.encode(consumerDto.getPassword()));
		consumer.setRole("ROLE_REGISTRED");
		consumer.setStatus(false);
		String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
        		LocalDateTime.now(),LocalDateTime.now().plusMinutes(5),consumer);
        confirmationTokenService.saveEmailToken(confirmationToken);
        String link = "http://localhost:8080/api/confirm?token=" + token;
        emailService.send(consumer.getEmail(),
                emailService.buildEmail(consumer.getFirstName(),link));
		return consumerRepository.save(consumer);
	}
	
	 public String confirmationEmailToken(String token) {

	        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token);

	        if(confirmationToken == null || confirmationToken.getConfirmedAt() != null || 
	        		confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())){
	            throw new IllegalStateException("Email or token is not valid or token is expired");
	        }
	        Consumer consumer = confirmationToken.getConsumer();
	        consumer.setStatus(true);
	        confirmationToken.setConfirmedAt(LocalDateTime.now());
	        confirmationTokenService.saveEmailToken(confirmationToken);
	        consumerRepository.save(consumer);
	        return "Consumer successfully activated account";
	 }
	 
	 public Long getByEmail(String email) {
		 Consumer consumer = consumerRepository.findByEmail(email);
		 if(consumer == null) {
			 return null;
		 } else {
			 return consumer.getId();
		 }
	 }

}
