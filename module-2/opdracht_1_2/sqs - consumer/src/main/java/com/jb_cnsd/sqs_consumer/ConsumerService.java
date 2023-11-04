package com.jb_cnsd.sqs_consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerService {

    @SqsListener("${cloud.aws.sqs.test.url}")
    private void receiveMessage(String message) {
        log.info("Received SQS message: [{}]", message);
    }
}
