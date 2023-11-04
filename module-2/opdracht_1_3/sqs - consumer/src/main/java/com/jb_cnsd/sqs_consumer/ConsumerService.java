package com.jb_cnsd.sqs_consumer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    @Value("${cloud.aws.s3.photo.name}")
    private String photoBucket;

    public final AmazonS3 s3Client;

    @SqsListener("${cloud.aws.sqs.test.url}")
    private void receiveMessage(String message) {
        log.info("Received SQS message: [{}]", message);
    }

    @JmsListener(destination = "${cloud.aws.sqs.photo.name}", containerFactory = "jmsListenerContainerFactory")
    public void consumeMessage(String message) throws InterruptedException {
        log.info("Received message with size [{}]", message.length());
        Thread.sleep(1000);

        String objectKey = UUID.randomUUID().toString();
        String objectUrl = "https://"+photoBucket+".s3.amazonaws.com/"+objectKey;
        final PutObjectRequest req =
                new PutObjectRequest(photoBucket, objectKey, new ByteArrayInputStream(Base64.decode(message)), new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(req);
        log.info("Photo saved to [{}] [{}] [{}]", photoBucket, objectKey, objectUrl);
    }
}
