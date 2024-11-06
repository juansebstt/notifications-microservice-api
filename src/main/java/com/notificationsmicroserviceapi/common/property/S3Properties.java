package com.notificationsmicroserviceapi.common.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "s3")
public class S3Properties {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String zone;

}
