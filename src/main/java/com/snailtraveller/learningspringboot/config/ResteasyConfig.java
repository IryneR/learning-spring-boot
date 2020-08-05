package com.snailtraveller.learningspringboot.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Configuration
@ApplicationPath("/")
@EnableAutoConfiguration()
@ComponentScan(basePackages = "com.snailtraveller")
public class ResteasyConfig extends Application {
}
