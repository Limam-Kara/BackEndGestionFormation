package com.example.demo.controllers.authentication;

import com.example.demo.Service.PasswordResetService;
import com.example.demo.dtos.ResetPassword.ResetPasswordRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ResetPasswordController {
    private final PasswordResetService passwordResetService;

    public ResetPasswordController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }
    @PostMapping("/forgetPassword")
    public ResponseEntity<String> forgetPassword(@RequestBody String email) {
        return new ResponseEntity<>(passwordResetService.initiatePasswordReset(email), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            String message = passwordResetService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getNewPassword());
            if ("Password has been reset successfully".equals(message)) {
                return ResponseEntity.ok().body(message); // Return response as plain text
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while resetting the password");
            }
        } catch (RuntimeException e) {
            if ("Invalid or expired token".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            } else if (e.getMessage().startsWith("User with email")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while resetting the password");
            }
        }
    }



}
