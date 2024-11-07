package com.notificationsmicroserviceapi.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.notificationsmicroserviceapi.common.property.S3Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AmazonS3Config {
    private final S3Properties s3Properties;

    public AmazonS3Config(S3Properties s3Properties) {

        this.s3Properties = s3Properties;
    }

    @Bean
    public AmazonS3 s3Client() {
        log.info("S3 client initialized with bucket: {} in {}", s3Properties.getBucket(), s3Properties.getZone());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(s3Properties.getAccessKey(),
                        s3Properties.getSecretKey())))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://127.0.0.1:4566/" +
                                s3Properties.getBucket(), s3Properties.getZone())
                )
                .build();
    }
}
