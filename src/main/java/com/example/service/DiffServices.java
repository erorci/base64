package com.example.service;

import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheOperationInvoker.ThrowableWrapper;
import org.springframework.stereotype.Service;

import com.example.controller.HomeController;
import com.example.entity.JsonBase;
import com.example.entity.JsonDataTO;
import com.example.enums.JsonSide;
import com.example.repository.JsonBaseRepository;

import ch.qos.logback.classic.spi.ThrowableProxyVO;

/**
 * Service is used for persisting objects in the repository, validate if a
 * object is equal, has different size or mapped offsets.
 * 
 * @author emersonr
 *
 */

@Service
public class DiffServices {

	@Autowired
	public JsonBaseRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(DiffServices.class);
	
	/**
	 * Persist the object in the repository
	 * @param id used for object identification
	 * @param json is the based64 data to be inserted
	 * @param side is origin, which endpoint was used
	 * @return a new JsonBase object
	 * @throws Exception
	 */
	public JsonBase save(Long id, String json, JsonSide side) throws Exception {

		validate(id, json);

		JsonBase jsonBase = repository.findById(id);
		if (jsonBase == null) {
			jsonBase = new JsonBase();
			jsonBase.setId(id);
		}

		if (JsonSide.LEFT.equals(side)) {
			jsonBase.setLeft(json);
		} else {
			jsonBase.setRight(json);
		}

		return repository.save(jsonBase);

	}

	/**
	 * Guarantee if data in use is valid avoiding further errors.
	 * @param id to test a valid identification
	 * @param json to test a valid json string
	 * @throws Exception with customized message to help user understanding
	 */
	private void validate(Long id, String json) throws Exception {
		
		logger.info("validate: " + id.toString());
		
		if (!StringUtils.isNotBlank(json)) {
			throw new Exception("Json is blank or null");
		}

	}

	/**
	 * Do the core validation in order to compare Jsons and return its results
	 * 
	 * @param id
	 *            is used by repository to find a object
	 * @return a string with comparison results
	 */
	public String validateJson(Long id) {
		JsonBase jsonBase = repository.findById(id);

		if (jsonBase == null) {
			return "No data found";
		}

		if (!StringUtils.isNotBlank(jsonBase.getLeft()) || !StringUtils.isNotBlank(jsonBase.getRight())) {
			return "Json missing";
		}
		byte[] bytesLeft = jsonBase.getLeft().getBytes();
		byte[] bytesRight = jsonBase.getRight().getBytes();

		boolean blnResult = Arrays.equals(bytesLeft, bytesRight);

		String offsets = "";

		if (blnResult) {
			return "Jsons are equal";
		} else if (bytesLeft.length != bytesRight.length) {
			return "Jsons are not same size.";

		} else {

			byte different = 0;

			for (int index = 0; index < bytesLeft.length; index++) {
				different = (byte) (bytesLeft[index] ^ bytesRight[index]);

				if (different != 0) {
					offsets = offsets + " " + index;
				}

			}

		}
		return "Jsons are same size, but offsets are different:" + offsets;
	}

}
