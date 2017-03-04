package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This application provides 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints,
 * and provided data needs to be diff-ed and the results shall be available on a third end point.
 * <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
 * <host>/v1/diff/<ID>
 * 
 * @author emersonr
 *
 */

@SpringBootApplication
public class Base64Application {

	public static void main(String[] args) {
		SpringApplication.run(Base64Application.class, args);
	}
}
