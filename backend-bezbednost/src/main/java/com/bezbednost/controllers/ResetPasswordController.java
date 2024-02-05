package com.bezbednost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bezbednost.models.ResetPassword;
import com.bezbednost.models.ResetPasswordDto;
import com.bezbednost.services.ResetPasswordService;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {
	
	@Autowired
	private ResetPasswordService resetPasswordService;
	
	@PostMapping("/reset-password")
    public ResponseEntity<ResetPassword> forgotPassword(@Validated @RequestBody ResetPasswordDto resetPasswordDto, BindingResult errors){
    	if (errors.hasErrors()) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    	ResetPassword resetPassword = resetPasswordService.resetOldPassword(resetPasswordDto);
        if(resetPassword == null)
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
        	return new ResponseEntity<>(HttpStatus.OK);
        
        
    }
	
	@GetMapping("/confirm-reset-password")
    public ModelAndView confirmPasswordToken(@RequestParam String token){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",resetPasswordService.confirmationPasswordToken(token));
        modelAndView.addObject("link","https://localhost:3000/");
        modelAndView.setViewName("confirmation");
        return modelAndView;
    }

}
