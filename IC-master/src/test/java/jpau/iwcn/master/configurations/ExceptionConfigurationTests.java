package jpau.iwcn.master.configurations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;

import jpau.iwcn.master.configurations.ExceptionConfiguration;
import jpau.iwcn.master.controllers.ProductRestController;
import jpau.iwcn.master.models.CommonResponse;
import jpau.iwcn.master.services.ProductServiceDB;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ExceptionConfigurationTests {

    @Mock
    private ProductServiceDB productServiceDB;
    
    @InjectMocks
    private ProductRestController productRestController;
    
    private MockMvc mockMvc;
    
    private ObjectMapper obj;
    
    @Before
	public void init(){
		productRestController = new ProductRestController();
		obj = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(productRestController)
				.setControllerAdvice(new ExceptionConfiguration())
	            .build();
		MockitoAnnotations.initMocks(this);
	}
    
    @Test
    public void testMongoError() throws Exception {
    	
    	when(productServiceDB.getProducts()).then(answer -> {
    		throw new MongoException("");
        });
    	
    	MvcResult result = mockMvc.perform(get("/products/findAll")
    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isInternalServerError())
    		.andReturn();
    	
    	assertFalse(obj.readValue(result.getResponse().getContentAsString(), CommonResponse.class).isSuccess());
    	assertTrue(obj.readValue(result.getResponse().getContentAsString(), CommonResponse.class).getErrors().size() > 0);

    }
    
    @Test
    public void testMongoSocketOpenError() throws Exception {
    	
    	when(productServiceDB.getProducts()).then(answer -> {
    		throw new MongoSocketOpenException("", null, null);
        });
    	
    	MvcResult result = mockMvc.perform(get("/products/findAll")
    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isInternalServerError())
    		.andReturn();
    	
    	assertFalse(obj.readValue(result.getResponse().getContentAsString(), CommonResponse.class).isSuccess());
    	assertTrue(obj.readValue(result.getResponse().getContentAsString(), CommonResponse.class).getErrors().size() > 0);

    }
    
    @Test
    public void testError() throws Exception {
    	
    	when(productServiceDB.getProducts()).then(answer -> {
    		throw new Exception();
        });
    	
    	MvcResult result = mockMvc.perform(get("/products/findAll")
    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isInternalServerError())
    		.andReturn();
  
    	assertFalse(obj.readValue(result.getResponse().getContentAsString(), CommonResponse.class).isSuccess());
    	assertTrue(obj.readValue(result.getResponse().getContentAsString(), CommonResponse.class).getErrors().size() > 0);

    }

}
