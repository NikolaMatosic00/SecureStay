package com.bezbednost.services;

import com.bezbednost.models.ResetPassword;
import com.bezbednost.models.ResetPasswordDto;

public interface ResetPasswordService {

	ResetPassword resetOldPassword(ResetPasswordDto resetPasswordDto);
	
	String confirmationPasswordToken(String token);
}
