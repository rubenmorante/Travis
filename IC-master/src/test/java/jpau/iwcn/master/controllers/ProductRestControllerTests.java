package jpau.iwcn.master.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import jpau.iwcn.master.configurations.ExceptionConfiguration;
import jpau.iwcn.master.controllers.ProductRestController;
import jpau.iwcn.master.entities.Product;
import jpau.iwcn.master.models.CommonResponse;
import jpau.iwcn.master.services.ProductServiceDB;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ProductRestControllerTests {

    @Mock
    private ProductServiceDB productServiceDB;
    
    @InjectMocks
    private ProductRestController productRestController;
    
    private MockMvc mockMvc;
    
    private Product productA, productB;
    
    private ObjectMapper obj;
    
    @Before
	public void init(){
		productRestController = new ProductRestController();
		
		obj = new ObjectMapper();
		
		productA = new Product();
		productA.setCode("code0001");
		productA.setName("Code 01");
		productA.setDescription("Code 01");
		productA.setPrice(12.12);
		
		productB = new Product();
		productB.setCode("code0002");
		productB.setName("Code 02");
		productB.setDescription("Code 02");
		productB.setPrice(15.15);
		
		mockMvc = MockMvcBuilders.standaloneSetup(productRestController)
				.setControllerAdvice(new ExceptionConfiguration())
	            .build();
		
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void testGetProducts() throws Exception {
    	
    	List<Product> products = new ArrayList<>();
    	products.add(productA);
    	products.add(productB);
    	
    	when(productServiceDB.getProducts()).then(answer -> {
            return CommonResponse.success(products);
        });
    	
    	mockMvc.perform(get("/products/findAll")
    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.result.size()", is(2)));

    }
    
    @Test
    public void testGetProduct() throws Exception {
    	
    	when(productServiceDB.getProduct(anyString())).then(answer -> {
            return CommonResponse.success(productA);
        });
    	
    	mockMvc.perform(get("/products/findByCode/" + productA.getCode())
    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.result.code", is(productA.getCode())))
    		.andExpect(jsonPath("$.result.name", is(productA.getName())))
    		.andExpect(jsonPath("$.result.description", is(productA.getDescription())))
    		.andExpect(jsonPath("$.result.price", is(productA.getPrice())));

    }
    
    @Test
    public void testInsertProduct() throws Exception {
    	
    	when(productServiceDB.insert(any())).then(answer -> {
            return CommonResponse.success(productA);
        });
    	
    	mockMvc.perform(post("/products/insert")
    		.contentType(MediaType.APPLICATION_JSON)
    		.content(obj.writeValueAsString(productA)))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.result.code", is(productA.getCode())))
    		.andExpect(jsonPath("$.result.name", is(productA.getName())))
    		.andExpect(jsonPath("$.result.description", is(productA.getDescription())))
    		.andExpect(jsonPath("$.result.price", is(productA.getPrice())));

    }
    
    @Test
    public void testDeleteProduct() throws Exception {
    	
    	when(productServiceDB.delete(any())).then(answer -> {
            return CommonResponse.success(productA);
        });
    	
    	mockMvc.perform(delete("/products/delete")
    		.contentType(MediaType.APPLICATION_JSON)
    		.content(obj.writeValueAsString(productA)))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.result.code", is(productA.getCode())))
    		.andExpect(jsonPath("$.result.name", is(productA.getName())))
    		.andExpect(jsonPath("$.result.description", is(productA.getDescription())))
    		.andExpect(jsonPath("$.result.price", is(productA.getPrice())));

    }
    
    @Test
    public void testUpdateProduct() throws Exception {
    	
    	when(productServiceDB.update(any())).then(answer -> {
            return CommonResponse.success(productA);
        });
    	
    	mockMvc.perform(put("/products/update")
    		.contentType(MediaType.APPLICATION_JSON)
    		.content(obj.writeValueAsString(productA)))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.result.code", is(productA.getCode())))
    		.andExpect(jsonPath("$.result.name", is(productA.getName())))
    		.andExpect(jsonPath("$.result.description", is(productA.getDescription())))
    		.andExpect(jsonPath("$.result.price", is(productA.getPrice())));

    }

}
