package com.jb_cnsd.sqs_publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublishService {
    @Value("${cloud.aws.sqs.test.url}")
    private String queueName;

    private final QueueMessagingTemplate messagingTemplate;

    public void send() {
        try {
            messagingTemplate.convertAndSend(queueName, "Hello World");
            log.info("Message send successfully.");
        } catch (Exception e) {
            log.error("Message send unsuccessfully!");
            e.printStackTrace();
        }
    }
}
