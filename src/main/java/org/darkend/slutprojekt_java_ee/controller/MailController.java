package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.darkend.slutprojekt_java_ee.dto.MailDto;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final MailService mailService;
    private final Logger logger = LoggerFactory.getLogger(MailController.class);

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("courses/{id}/message")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    public MailDto sendEmail(@RequestBody MailDto mail, @PathVariable Long id) {
        String jsonBody = ObjectToJson.convert(mail);
        logger.info("Received POST request with JSON body: {}", jsonBody);

        return mailService.newMail(mail, id);
    }
}
