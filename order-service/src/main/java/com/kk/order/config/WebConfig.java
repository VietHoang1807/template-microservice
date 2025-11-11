package com.kk.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    Spring 7 support
//    Path Segment Versioning
//    @Override
//    public void configureApiVersioning(ApiVersionConfigurer configurer) {
//        configurer.usePathSegment(0)
//                .addSupportedVersions("1");
//    }
//Request Header Versioning Strategy
//    @Override
//    public void configureApiVersioning(ApiVersionConfigurer configurer) {
//        configurer.useRequestHeader("API-version")
//                .addSupportedVersions("1");
//    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/{v}", aClass -> true);
    }
}
