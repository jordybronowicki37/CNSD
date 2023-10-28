package com.jb_cnsd.sqs_publisher.service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsConfig {
    @Bean
    public static AWSCredentialsProvider awsCredentialsProvider() {
        return DefaultAWSCredentialsProviderChain.getInstance();
    }

    @Bean
    public static AmazonSQSAsync setupSqs(
            AWSCredentialsProvider credentials,
            @Value("${cloud.aws.region.static}") String region
    ) {
        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(credentials)
                .withRegion(region)
                .build();
    }

    @Bean
    public static QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }
}
