package com.example.service;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.JsonBase;
import com.example.repository.JsonBaseRepository;


/**
 * Service is used for persisting objects in the repository,
 * validate if a object is equal, has different size or mapped offsets.
 * @author emersonr
 *
 */

@Service
public class DiffServices {

	@Autowired
	public JsonBaseRepository repository;

	/**
	 * Checking if data is persisted
	 * @param id is used for the repository to identify an object
	 * @param value is the json based64 to be persisted.
	 * @return a JsonBase object
	 */
	public JsonBase saveLeft(Long id, String value) {
		JsonBase jsonBase = repository.findById(id);
		if (jsonBase == null) {
			jsonBase = new JsonBase();
			jsonBase.setId(id);
		}
		jsonBase.setLeft(value);
		return repository.save(jsonBase);		

	}
	
	/**
	 * Checking if data is persisted
	 * @param id is used by repository to identify an object
	 * @param value is the json based64 to be persisted.
	 * @return a JsonBase object
	 */
	public JsonBase saveRight(Long id, String value) {
		JsonBase jsonBase = repository.findById(id);
		if (jsonBase == null) {
			jsonBase = new JsonBase();
			jsonBase.setId(id);
		}
		jsonBase.setRight(value);
		return repository.save(jsonBase);
	}
	
	
	/**
	 * Do the core validation in order to compare Jsons and return its results
	 * @param id is used by repository to find a object
	 * @return a string with comparison results
	 */
	public String validateJson(Long id) {
		JsonBase jsonBase = repository.findById(id);
		
		if (jsonBase == null) {
			return "No data found";
		}
		
		if ( !StringUtils.isNotBlank(jsonBase.getLeft()) || !StringUtils.isNotBlank(jsonBase.getRight())) {
			return "Json missing";
		}
		byte[] bytesLeft = jsonBase.getLeft().getBytes();
		byte[] bytesRight = jsonBase.getRight().getBytes();

		boolean blnResult = Arrays.equals(bytesLeft, bytesRight);
		
		String offsets = "";
		
		if(blnResult) {
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
