package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.services.EmailService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200/",exposedHeaders="Access-Control-Allow-Origin" )
@RestController
@Slf4j
public class PasswordController {
	
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private EmailService emailService;


	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
    // Process form submission from forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.PUT)
	public String processForgotPasswordForm( @RequestParam("email") String userEmail, HttpServletRequest request) {

		// Lookup user in database by e-mail
		Optional<User> optional = userService.findUserByEmail(userEmail);

		if (!optional.isPresent()) {
			return("errorMessage, We didn't find an account for that e-mail address.");
		} else {
			
			// Generate random 36-character string token for reset password 
			User user = optional.get();
			user.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			userService.resetpassword(user);

			String appUrl = request.getScheme() + "://" + request.getServerName();
			
			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("support@pfe.com");
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Demande de réinitialisation du mot de passe");
			passwordResetEmail.setText("Pour réinitialiser votre mot de passe, cliquez sur le lien ci-dessous:\n" + appUrl+":4200/#/"
					+ "/reset?token=" + user.getResetToken());
			
			emailService.sendEmail(passwordResetEmail);

			// Add success message to view
			return("successMessage, Un lien de réinitialisation du mot de passe a été envoyé à " + userEmail);
		}



	}


	// Process reset password form
	@PutMapping("/reset/{rt}")
	@ResponseBody
	public String setNewPassword(@RequestBody String us,
			//@RequestParam Map<String, String> requestParams,
			@PathVariable("rt") String rt) {

		// Find the user associated with the reset token
		//Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));
		Optional<User> user = userService.findUserByResetToken(rt);

		// This should always be non-null but we check just in case
		if (user.isPresent()) {
			
			User resetUser = user.get(); 
            
			// Set new password    
			resetUser.setPassword(bCryptPasswordEncoder.encode(us));

			// Set the reset token to null so it cannot be used again
			resetUser.setResetToken(null);

			// Save user
			userService.resetpassword(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes


			return ("successMessage, Vous avez réinitialisé avec succès votre mot de passe."+us);
			
		} else {
			return ("errorMessage, Oops!  Ceci est un lien de réinitialisation de mot de passe invalide.");

		}
		

   }
   
    // Going to reset page without a token redirects to login page
//	@ExceptionHandler(MissingServletRequestParameterException.class)
//	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
//		return new ModelAndView("redirect:login");
//	}

}
