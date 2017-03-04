package com.example.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.entity.JsonBase;
import com.example.repository.JsonBaseRepository;

/**
 * Integration Tests using H2 repository
 * @author emersonr
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

	JsonBase base = new JsonBase();

	@Autowired
	private MockMvc mvc;

	@Autowired
	public JsonBaseRepository repository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	/**
	 * Initial setup
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		this.mvc = webAppContextSetup(webApplicationContext).build();
		this.repository.deleteAll();
	}
	
	/**
	 * Validate if left endpoint is working and persisting the object
	 * @throws Exception
	 */
	@Test
	public void left() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"DBVsbG8gd29ybGJK=\"" + "}")).andExpect(status().isOk()).andReturn();
		JsonBase base = repository.findById(1L);
		Assert.assertThat(base.getId(), Matchers.is(1L));
		Assert.assertThat(base.getLeft(), Matchers.is("DBVsbG8gd29ybGJK="));
		Assert.assertThat(base.getRight(), Matchers.isEmptyOrNullString());
	}

	/**
	 * Validate if right endpoint is working and persisting the object
	 * @throws Exception
	 */
	@Test
	public void right() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"DBVsbG8gd29ybGJK=\"" + "}")).andExpect(status().isOk()).andReturn();
		Assert.assertThat(base.getId(), Matchers.is(1L));
		Assert.assertThat(base.getRight(), Matchers.is("DBVsbG8gd29ybGJK="));
		Assert.assertThat(base.getLeft(), Matchers.isEmptyOrNullString());
	}
	
	/**
	 * Testing equals base64 enconed jsons.
	 * @throws Exception
	 */
	@Test
	public void equal() throws Exception {
		repository.save(new JsonBase(1l, "DBVsbG8gd29ybGJK=", "DBVsbG8gd29ybGJK="));
		mvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"DBVsbG8gd29ybGJK=\"" + "}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("Jsons are equal")))
				.andReturn();		
	}

	
}
