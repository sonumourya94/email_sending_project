package com.email.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.email.model.Email;
import com.email.response.EmailResponse;
import com.email.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@CrossOrigin
public class EmailController {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private ObjectMapper mapper;

	@PostMapping("/sendMailWithFile")
	public ResponseEntity<?> sendMailWithFile(@RequestParam("file") MultipartFile multiPartfile,
			@RequestParam("emailData") String emailData) {

		try {

			File file = new File(multiPartfile.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multiPartfile.getBytes());
			fos.close();

			Email email = mapper.readValue(emailData, Email.class);

			boolean result = this.emailService.sendEmailWithFile(email.getTo(), email.getSubject(), email.getText(), file);

			if (result) {
				return ResponseEntity.ok(new EmailResponse("Email send successfully.."));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new EmailResponse("Email has not sent successfully..."));
			}

		} catch (IOException e) {
			logger.error("sendMailWithFile controller Exception ::" + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Data");
		}

	}
	
	@PostMapping("/sendMail")
	public ResponseEntity<?> sendMail(@RequestParam("emailData") String emailData) {

		try {
			Email email = mapper.readValue(emailData, Email.class);

			boolean result = this.emailService.sendEmail(email.getTo(), email.getSubject(), email.getText());

			if (result) {
				return ResponseEntity.ok(new EmailResponse("Email send successfully.."));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new EmailResponse("Email has not sent successfully..."));
			}

		} catch (IOException e) {
			logger.error("sendMail controller Exception ::" + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Data");
		}

	}
}
