package com.jb_cnsd.sqs_publisher.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publish")
@AllArgsConstructor
public class PublishController {
    @PostMapping
    public ResponseEntity<Void> postMessage() {
        return ResponseEntity.ok().build();
    }
}
