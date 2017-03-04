package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.JsonDataTO;
import com.example.enums.JsonSide;
import com.example.service.DiffServices;


/**
 * RestController used to map endpoints for data entry and get results
 * @author emersonr
 *
 */
@RestController
@RequestMapping("/v1/diff/{id}")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private DiffServices service;

	
	/**
	 * This endpoint is used to post a value for comparison
	 * @param id
	 * @param json is a base64 encoded value used during validations
	 * @return OK message when data was persisted successfully
	 * @throws Exception 
	 */
	@RequestMapping(value = "/left", method = RequestMethod.POST, produces = "application/json")
	private String setLeft(@PathVariable Long id, @RequestBody JsonDataTO json) throws Exception {
		service.save(id, json.getData(), JsonSide.LEFT );
		logger.info("setLeft " + json.getData());
		return "{\"message\":" + "\"" + "OK" + "\"}";
	}

	/**
	 * This endpoint is used to post a value for comparison
	 * @param id
	 * @param json is a base64 encoded value used during validations
	 * @return OK message when data was persisted successfully
	 * @throws Exception 
	 */
	@RequestMapping(value = "/right", method = RequestMethod.POST, produces = "application/json")
	private String setRight(@PathVariable Long id, @RequestBody JsonDataTO json) throws Exception {
		service.save(id, json.getData(), JsonSide.RIGHT );
		logger.info("setRight " + json.getData());
		return "{\"message\":" + "\"" + "OK" + "\"}";
	}

	/**
	 * This endpoint result data comparison
	 * @param id
	 * @return a message with comparison status
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	private String getDiff(@PathVariable Long id) {
		return "{\"message\":" + "\"" + service.validateJson(id) + "\"}";
	}

}
