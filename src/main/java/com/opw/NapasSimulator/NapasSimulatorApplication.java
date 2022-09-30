package com.opw.NapasSimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.*;

@SpringBootApplication
@EnableScheduling
public class NapasSimulatorApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(NapasSimulatorApplication.class, args);

	}

}
