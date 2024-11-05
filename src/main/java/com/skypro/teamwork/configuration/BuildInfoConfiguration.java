package com.skypro.teamwork.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.stereotype.Component;

@Component
public class BuildInfoConfiguration {
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    public void buildInfo(Info.Builder builder) {
        builder.withDetail("name", appName)
                .withDetail("version", appVersion);
    }
}
