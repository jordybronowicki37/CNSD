package com.jb_cnsd.sqs_publisher;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/publish")
@AllArgsConstructor
public class PublishController {
    private final PublishService publishService;

    @PostMapping
    public ResponseEntity<Void> postMessage() {
        publishService.sendTestMessage();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/photo")
    public ResponseEntity<Void> postImage(@RequestParam("photo") MultipartFile photo) throws IOException {
        publishService.sendPhotoMessage(photo);
        return ResponseEntity.ok().build();
    }
}
