package com.jb_cnsd.sqs_publisher;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publish")
@AllArgsConstructor
public class PublishController {
    private final PublishService publishService;

    @PostMapping
    public ResponseEntity<Void> postMessage() {
        publishService.send();
        return ResponseEntity.ok().build();
    }
}
