package ru.xdx505.moexstatsbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class AppExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);
    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleReportException(HttpServletRequest req, Exception ex) throws JsonProcessingException {
        var error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Ошибка сервера",
                List.of(ex.getMessage(), ExceptionUtils.getRootCauseMessage(ex)));
        var json = this.objectMapper.writeValueAsString(error);
        this.logger.error(error.toString());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }
}
