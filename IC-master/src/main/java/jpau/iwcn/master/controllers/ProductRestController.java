package jpau.iwcn.master.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import jpau.iwcn.master.entities.Product;
import jpau.iwcn.master.models.CommonResponse;
import jpau.iwcn.master.services.ProductServiceDB;

@RestController
@RequestMapping(value = "/products")
@Api(value="products", tags={"Allowed operations for product management"})
public class ProductRestController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);
	
	@Value("${application.message}")
	String message;

	@Value("${application.appname}")
	String appname;

    @Autowired
    ProductServiceDB productServiceDB;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> init() {
    	String init = message + " to " + appname;
        return new ResponseEntity<>(init, HttpStatus.OK);
    }
    
    @Cacheable("findAll")
    @ApiOperation(value = "Search all products",response = CommonResponse.class)
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<Product>>> findAll() {
    	logger.info("getting products");
    	CommonResponse<List<Product>> response = productServiceDB.getProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
 
    @ApiOperation(value = "Search a product with a code",response = CommonResponse.class)
    @RequestMapping(value = "/findByCode/{code}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<Product>> findByCode(@PathVariable("code") String code) throws NotFoundException {
    	logger.info("getting product by code ", code);
    	CommonResponse<Product> response = productServiceDB.getProduct(code);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Add a new product",response = CommonResponse.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Product>> insert(@RequestBody Product product) throws NotFoundException {
    	logger.info("adding new product ", product.getCode());
    	CommonResponse<Product> response = productServiceDB.insert(product);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Delete a product",response = CommonResponse.class)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Product>> delete(@RequestBody Product product) throws NotFoundException {
    	logger.info("deleting new product ", product.getCode());
    	CommonResponse<Product> response = productServiceDB.delete(product);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Update a product",response = CommonResponse.class)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse<Product>> update(@RequestBody Product product) throws NotFoundException {
    	logger.info("updating new product ", product.getCode());
    	CommonResponse<Product> response = productServiceDB.update(product);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
