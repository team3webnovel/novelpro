package com.team3webnovel.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@ComponentScan(basePackages = "com.team3webnovel")
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/example_images/**")
                .addResourceLocations("file:///C:/example_images/") // 이미지 폴더 경로
                .setCachePeriod(3600); // 캐시 설정 (선택 사항)
	}
}
