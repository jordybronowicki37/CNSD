package com.jb_cnsd.sqs_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableSqs
@EnableJms
public class SqsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqsConsumerApplication.class, args);
    }

}
