package com.vivareal.spotippos.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.vivareal.spotippos.ValidationConfig;

@TestConfiguration
@ComponentScan(basePackages = {"com.vivareal.spotippos.service", "com.vivareal.spotippos.repository"})
@Import({ ValidationConfig.class})
public class PropertyServiceConfig {

	
}