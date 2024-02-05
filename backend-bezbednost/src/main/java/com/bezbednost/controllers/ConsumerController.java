package com.bezbednost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bezbednost.models.Consumer;
import com.bezbednost.models.ConsumerDto;
import com.bezbednost.services.ConsumerService;

@RestController
@RequestMapping("/api")
public class ConsumerController {
	
	@Autowired
    private ConsumerService consumerService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/registration")
    public ResponseEntity<Consumer> register(@Validated @RequestBody ConsumerDto consumerDto, BindingResult errors){
    	if (errors.hasErrors()) {
    		System.out.println(errors.getAllErrors());
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Consumer consumer = consumerService.registration(consumerDto);
        if(consumer == null)
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
        	return new ResponseEntity<>(consumer, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String lozinka){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, lozinka);
        return new ResponseEntity<>(authenticationManager.authenticate(authenticationToken),HttpStatus.OK);
    }
    
    @GetMapping("/confirm")
    public ModelAndView confirmationToken(@RequestParam String token){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",consumerService.confirmationEmailToken(token));
        modelAndView.addObject("link","https://localhost:3000/");
        modelAndView.setViewName("confirmation");
        return modelAndView;
    }

}
