package jpau.iwcn.master.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import jpau.iwcn.master.entities.Product;
import jpau.iwcn.master.models.CommonResponse;
import jpau.iwcn.master.repositories.ProductRepository;
import jpau.iwcn.master.services.ProductServiceDB;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ProductServiceDBTests {

    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductServiceDB productServiceDB;
    
    private Product productA, productB, productC;
    
    @Before
	public void init(){
    	productServiceDB = new ProductServiceDB();
		
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
		
		productC = new Product();
		productC.setCode("code0001");
		productC.setName("Code 01 update");
		productC.setDescription("Code 02 update");
		productC.setPrice(15.45);
		
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void testGetProducts() throws Exception {
    	
    	List<Product> products = new ArrayList<>();
    	products.add(productA);
    	products.add(productB);
    	
    	when(productRepository.findAll()).then(answer -> {
            return products;
        });
    	
    	CommonResponse<List<Product>> response = productServiceDB.getProducts();
    	
    	assertTrue(response.isSuccess());
    	assertEquals(response.getResult().size(), 2);
    	assertNull(response.getErrors());

    }
    
    @Test
    public void testGetProduct() throws Exception {
    	
    	when(productRepository.findOne(anyString())).then(answer -> {
            return productA;
        });
    	
    	CommonResponse<Product> response = productServiceDB.getProduct(productA.getCode());
    	
    	assertTrue(response.isSuccess());
    	assertEquals(response.getResult().getCode(), productA.getCode());
    	assertEquals(response.getResult().getName(), productA.getName());
    	assertEquals(response.getResult().getDescription(), productA.getDescription());
    	assertEquals(response.getResult().getPrice(), productA.getPrice(),0);
    	assertNull(response.getErrors());

    }
    
    @Test
    public void testGetProductNotFound() throws Exception {
    	
    	when(productRepository.findOne(anyString())).then(answer -> {
            return null;
        });
    	
    	CommonResponse<Product> response = productServiceDB.getProduct(productA.getCode());
    	
    	assertFalse(response.isSuccess());
    	assertTrue(response.getErrors().size() > 0);

    }
    
    @Test
    public void testInsertProduct() throws Exception {
    	
    	when(productRepository.save(productA)).then(answer -> {
            return productA;
        });
    	
    	when(productRepository.exists(anyString())).then(answer -> {
            return false;
        });
    	
    	CommonResponse<Product> response = productServiceDB.insert(productA);
    	
    	assertTrue(response.isSuccess());
    	assertEquals(response.getResult().getCode(), productA.getCode());
    	assertEquals(response.getResult().getName(), productA.getName());
    	assertEquals(response.getResult().getDescription(), productA.getDescription());
    	assertEquals(response.getResult().getPrice(), productA.getPrice(),0);
    	assertNull(response.getErrors());

    }
    
    @Test
    public void testInsertProductFound() throws Exception {
    	
    	when(productRepository.exists(anyString())).then(answer -> {
            return true;
        });
    	
    	CommonResponse<Product> response = productServiceDB.insert(productA);
    	
    	assertFalse(response.isSuccess());
    	assertTrue(response.getErrors().size() > 0);

    }
    
    @Test
    public void testDeleteProduct() throws Exception {
    	
    	when(productRepository.exists(anyString())).then(answer -> {
            return true;
        });
    	
    	CommonResponse<Product> response = productServiceDB.delete(productA);
    	
    	assertTrue(response.isSuccess());
    	assertEquals(response.getResult().getCode(), productA.getCode());
    	assertEquals(response.getResult().getName(), productA.getName());
    	assertEquals(response.getResult().getDescription(), productA.getDescription());
    	assertEquals(response.getResult().getPrice(), productA.getPrice(),0);
    	assertNull(response.getErrors());

    }
    
    @Test
    public void testDeleteProductNotFound() throws Exception {
    	
    	when(productRepository.exists(anyString())).then(answer -> {
            return false;
        });
    	
    	CommonResponse<Product> response = productServiceDB.delete(productA);
    	
    	assertFalse(response.isSuccess());
    	assertTrue(response.getErrors().size() > 0);

    }
    
    @Test
    public void testUpdateProduct() throws Exception {
    	    	
    	when(productRepository.save(productA)).then(answer -> {
            return productA;
        });
    	
    	when(productRepository.exists(anyString())).then(answer -> {
            return true;
        });
    	
    	CommonResponse<Product> response = productServiceDB.update(productA);
    	
    	assertTrue(response.isSuccess());
    	assertEquals(response.getResult().getCode(), productA.getCode());
    	assertEquals(response.getResult().getName(), productA.getName());
    	assertEquals(response.getResult().getDescription(), productA.getDescription());
    	assertEquals(response.getResult().getPrice(), productA.getPrice(),0);
    	assertNull(response.getErrors());

    }
    
    @Test
    public void testUpdateNotFound() throws Exception {
    	
    	when(productRepository.exists(anyString())).then(answer -> {
            return false;
        });
    	
    	CommonResponse<Product> response = productServiceDB.update(productA);
    	
    	assertFalse(response.isSuccess());
    	assertTrue(response.getErrors().size() > 0);

    }

}
