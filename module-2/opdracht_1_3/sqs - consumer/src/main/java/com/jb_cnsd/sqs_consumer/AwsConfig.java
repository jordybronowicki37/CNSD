package com.jb_cnsd.sqs_consumer;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.s3.sqs-payload.name}")
    private String s3BucketName;

    @Bean("extendedClientConfig")
    public ExtendedClientConfiguration extendedClientConfiguration(final AmazonS3 s3Client) {
        return new ExtendedClientConfiguration().withPayloadSupportEnabled(s3Client, s3BucketName);
    }

    @Bean("amazonSQSExtendedClient")
    @Profile("!dev")
    public AmazonSQSExtendedClient amazonSQSExtendedClient(ExtendedClientConfiguration extendedClientConfig) {
        return new AmazonSQSExtendedClient(AmazonSQSClientBuilder.defaultClient(), extendedClientConfig);
    }

    @Bean("sqsConnectionFactory")
    public ConnectionFactory sqsConnectionFactory(final AmazonSQSExtendedClient amazonSQSExtendedClient) {
        final ProviderConfiguration providerConfiguration = new ProviderConfiguration();
        providerConfiguration.setNumberOfMessagesToPrefetch(10);

        return new SQSConnectionFactory(providerConfiguration, amazonSQSExtendedClient);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory sqsConnectionFactory) {
        final DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setConnectionFactory(sqsConnectionFactory);
        jmsListenerContainerFactory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        return jmsListenerContainerFactory;
    }

    @Bean("notificationMessagingTemplate")
    public NotificationMessagingTemplate notificationMessagingTemplate(final AmazonSNS snsClient) {
        return new NotificationMessagingTemplate(snsClient);
    }
}
