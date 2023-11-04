package com.jb_cnsd.sqs_publisher;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SqsConfig {
    @Value("${cloud.aws.s3.sqs-payload.name}")
    private String s3BucketName;

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(final AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    public ExtendedClientConfiguration ExtendedClientConfiguration(AmazonS3 s3Client) {
        return new ExtendedClientConfiguration().withPayloadSupportEnabled(s3Client, s3BucketName);
    }

    @Bean
    @Profile("!dev")
    public AmazonSQSExtendedClient amazonSQSExtendedClient(ExtendedClientConfiguration extendedClientConfig) {
        return new AmazonSQSExtendedClient(AmazonSQSClientBuilder.defaultClient(), extendedClientConfig);
    }
}
