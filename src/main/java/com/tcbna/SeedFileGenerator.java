package com.tcbna;

import java.io.File;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vavr.control.Try;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedFileGenerator {
	
	
	private final ObjectMapper objectMapper;
	private int i = 1;
	
	@Scheduled(cron = "0 0/15 * * * *")
    public void generate() {
        log.info("** Generating file **");
        
        Customer customer = new Customer();
        customer.id = UUID.randomUUID().toString();
        customer.fullName = "John Smith - v" + i;
       
        Try.run(() -> {
        	this.objectMapper.writeValue(new File("./customer-" + i + ".json"), customer);
        }).onFailure(e -> {
        	log.error("Error writing file", e);
        });
        
        
        i++;

    }
	
	
	@Data
	static class Customer {
		String id;
		String fullName;
		
	}

	
}
