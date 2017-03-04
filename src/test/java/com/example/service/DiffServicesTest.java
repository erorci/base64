package com.example.service;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.entity.JsonBase;
import com.example.enums.JsonSide;
import com.example.repository.JsonBaseRepository;

/**
 * This class do all unit tests for DiffServices service class.
 * @author emersonr
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffServicesTest {

	
	@InjectMocks
	private DiffServices service;

	@Mock
	public JsonBaseRepository repository;
	
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Testing if a new object was created
	 * @throws Exception
	 */
	@Test
	public void saveNotFound() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(JsonBase.class));
		JsonBase left = service.save(1L, "Left", JsonSide.LEFT);
		Assert.assertThat(left.getId(), Matchers.is(1L));
		Assert.assertThat(left.getLeft(), Matchers.is("Left"));
		Assert.assertThat(left.getRight(), Matchers.isEmptyOrNullString());
	}
	
	/**
	 * Testing if a new left json was inserted
	 * @throws Exception
	 */	
	@Test
	public void saveLeftFound() throws Exception {
		JsonBase json = new JsonBase(1L, null, "Right");
		Mockito.doReturn(json).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(JsonBase.class));
		JsonBase left = service.save(1L, "Left", JsonSide.LEFT);
		Assert.assertThat(left.getId(), Matchers.is(1L));
		Assert.assertThat(left.getLeft(), Matchers.is("Left"));
		Assert.assertThat(left.getRight(), Matchers.is("Right"));
	}
	
	/**
	 * Testing if a new object was created
	 * @throws Exception
	 */
	@Test
	public void saveRightNotFound() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(JsonBase.class));
		JsonBase left = service.save(1L, "Right", JsonSide.RIGHT);
		Assert.assertThat(left.getId(), Matchers.is(1L));
		Assert.assertThat(left.getRight(), Matchers.is("Right"));
		Assert.assertThat(left.getLeft(), Matchers.isEmptyOrNullString());
	}

	/**
	 * Testing if a new right json was inserted
	 * @throws Exception
	 */	
	@Test
	public void saveRightFound() throws Exception {
		JsonBase json = new JsonBase(1L, "Left", null);
		Mockito.doReturn(json).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(JsonBase.class));
		JsonBase left = service.save(1L, "Right", JsonSide.RIGHT);
		Assert.assertThat(left.getId(), Matchers.is(1L));
		Assert.assertThat(left.getLeft(), Matchers.is("Left"));
		Assert.assertThat(left.getRight(), Matchers.is("Right"));
	}
	
	/**
	 * Testing if no records were found
	 * @throws Exception
	 */	
	@Test
	public void validateDiffNoDataFound() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		String result = service.validateJson(1L);
		Assert.assertThat(result, Matchers.is("No data found"));
	}
	
	/**
	 * Testing if a right json was inserted and not null
	 * @throws Exception
	 */	
	@Test
	public void validateDiffJsonRightMissing() throws Exception {
		JsonBase json = new JsonBase(1L, "Left", null);
		Mockito.doReturn(json).when(repository).findById(Mockito.eq(1L));
		String result = service.validateJson(1L);
		Assert.assertThat(result, Matchers.is("Json missing"));
	}

	/**
	 * Testing if a left json was inserted and not blank
	 * @throws Exception
	 */	
	@Test
	public void validateDiffJsonLeftMissing() throws Exception {
		JsonBase json = new JsonBase(1L, null, "Right");
		Mockito.doReturn(json).when(repository).findById(Mockito.eq(1L));
		String result = service.validateJson(1L);
		Assert.assertThat(result, Matchers.is("Json missing"));
	}
	
	/**
	 * Testing if a jsons are equal
	 * @throws Exception
	 */	
	@Test
	public void validateDiffEqual() throws Exception {
		JsonBase json = new JsonBase(1L, "DBVsbG8gd29ybGJK=", "DBVsbG8gd29ybGJK=");
		Mockito.doReturn(json).when(repository).findById(Mockito.eq(1L));
		String result = service.validateJson(1L);
		Assert.assertThat(result, Matchers.is("Jsons are equal"));
	}
	
	/**
	 * Testing if a right json are not same size
	 * @throws Exception
	 */	
	@Test
	public void validateDiffDifSize() throws Exception {
		JsonBase json = new JsonBase(1L, "DBVsbG8gd29ybG=", "DBVsbG8gd29ybGJK=");
		Mockito.doReturn(json).when(repository).findById(Mockito.eq(1L));
		String result = service.validateJson(1L);
		Assert.assertThat(result, Matchers.is("Jsons are not same size."));
	}
	
	/**
	 * Testing if a are same size but with different values
	 * @throws Exception
	 */	
	@Test
	public void validateDiffOffset() throws Exception {
		JsonBase json = new JsonBase(1L, "ABVsbG8gd29ybGJK=", "DBVsbG8gd29ybGJK=");
		Mockito.doReturn(json).when(repository).findById(Mockito.eq(1L));
		String result = service.validateJson(1L);
		Assert.assertThat(result, Matchers.is("Jsons are same size, but offsets are different: 0"));
	}
	
	
}
