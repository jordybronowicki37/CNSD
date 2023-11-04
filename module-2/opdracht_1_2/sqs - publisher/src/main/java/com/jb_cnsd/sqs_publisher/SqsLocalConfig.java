package com.jb_cnsd.sqs_publisher;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Profile("dev")
@Configuration
@PropertySource(value = "application-credentials.yaml", ignoreResourceNotFound = true)
public class SqsLocalConfig {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${credentials.accessKey}")
    private String awsAccessKey;

    @Value("${credentials.secretKey}")
    private String awsSecretKey;

    @Value("${credentials.sessionToken}")
    private String awsSessionToken;

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicSessionCredentials(awsAccessKey, awsSecretKey, awsSessionToken)))
                .build();
    }

    @Bean("s3Client")
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard().withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicSessionCredentials(awsAccessKey, awsSecretKey, awsSessionToken))
                ).build();
    }

    @Bean
    public AmazonSQSExtendedClient amazonSQSExtendedClient(ExtendedClientConfiguration extendedClientConfig) {
        return new AmazonSQSExtendedClient(AmazonSQSClientBuilder
                .standard().withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicSessionCredentials(awsAccessKey, awsSecretKey, awsSessionToken))
                ).withRegion(region).build(), extendedClientConfig);
    }
}
