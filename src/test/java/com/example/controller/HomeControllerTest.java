package com.example.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.entity.JsonBase;
import com.example.entity.JsonDataTO;
import com.example.enums.JsonSide;
import com.example.repository.JsonBaseRepository;
import com.example.service.DiffServices;

/**
 * Unit tests for HomeController using mock data
 * @author emersonr
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	JsonBase base = new JsonBase();
	
	@InjectMocks
	private HomeController controller;

	@Autowired
	private MockMvc mvc;

	@Mock
	private DiffServices service;

	@Autowired
	public JsonBaseRepository repository;

	/**
	 * Initial setup for Mockito and MockMvc
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	/**
	 * Return No data found when no object was found in repository
	 * @throws Exception
	 */
	
	@Test
	public void dataNotFound() throws Exception {
		Mockito.doReturn("No data found").when(service).validateJson(Mockito.eq(1L));
		mvc.perform(get("/v1/diff/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("No data found")));
	}

	/**
	 * Test if left object was persisted
	 * @throws Exception
	 */
	@Test
	public void left() throws Exception {
		Mockito.doReturn(null).when(service).save(Mockito.eq(1L), Mockito.contains("DBVsbG8gd29ybGJK="), Mockito.eq(JsonSide.LEFT));
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"DBVsbG8gd29ybGJK=\"" + "}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("OK")));
	}

	/**
	 * Test if right object was persisted
	 * @throws Exception
	 */
	@Test
	public void right() throws Exception {
		Mockito.doReturn(null).when(service).save(Mockito.eq(1L), Mockito.contains("DBVsbG8gd29ybGJK="), Mockito.eq(JsonSide.RIGHT));
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"DBVsbG8gd29ybGJK=\"" + "}")).andExpect(status().isOk());
	}

		
}
