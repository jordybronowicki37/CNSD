package com.jb_cnsd.sqs_consumer;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("dev")
@Configuration
@PropertySource(value = "application-credentials.yaml", ignoreResourceNotFound = true)
public class AwsLocalConfig {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${credentials.accessKey}")
    private String awsAccessKey;

    @Value("${credentials.secretKey}")
    private String awsSecretKey;

    @Value("${credentials.sessionToken}")
    private String awsSessionToken;

    @Bean("awsCredentialsProvider")
    public AWSStaticCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicSessionCredentials(awsAccessKey, awsSecretKey, awsSessionToken));
    }

    @Bean("s3Client")
    public AmazonS3 amazonS3Client(final AWSStaticCredentialsProvider awsCredentialsProvider) {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(awsCredentialsProvider)
                .build();
    }

    @Bean("amazonSQSExtendedClient")
    public AmazonSQSExtendedClient amazonSQSExtendedClient(final ExtendedClientConfiguration extendedClientConfig, final AWSStaticCredentialsProvider awsCredentialsProvider) {
        return new AmazonSQSExtendedClient(
                AmazonSQSClientBuilder
                    .standard()
                    .withRegion(region)
                    .withCredentials(awsCredentialsProvider)
                    .build(),
                extendedClientConfig);
    }
}
