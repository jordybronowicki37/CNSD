package com.jb_cnsd.sqs_publisher;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublishService {
    @Value("${cloud.aws.sqs.test.url}")
    private String testQueue;

    @Value("${cloud.aws.sqs.photo.name}")
    private String photoQueue;

    private final QueueMessagingTemplate messagingTemplate;

    private final AmazonSQSExtendedClient sqsExtended;

    public void sendTestMessage() {
        try {
            messagingTemplate.convertAndSend(testQueue, "Hello World");
            log.info("Message send successfully.");
        } catch (Exception e) {
            log.error("Message send unsuccessfully!");
            e.printStackTrace();
        }
    }

    public void sendPhotoMessage(MultipartFile photo) throws IOException {
        final SendMessageRequest myMessageRequest = new SendMessageRequest(photoQueue, Base64.encodeAsString(photo.getBytes()));
        log.info("Sending the photo [{}] to the Amazon SQS [{}]", photo.getOriginalFilename(), photoQueue);
        sqsExtended.sendMessage(myMessageRequest);
    }
}
