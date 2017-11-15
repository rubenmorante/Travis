package jpau.iwcn.master.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import jpau.iwcn.master.entities.Product;
import jpau.iwcn.master.models.CommonResponse;
import jpau.iwcn.master.repositories.ProductRepository;

@Service
public class ProductServiceDB implements ProductService<Product, String> {
	
	private static final String PRODUCT_NOT_EXISTS = "Product not exists";
	private static final String PRODUCT_ALREADY_EXISTS = "Product already exists";

    @Autowired
    ProductRepository productRepository;

    @Override
    public CommonResponse<Product> insert(Product product) throws NotFoundException {
    	
    	if (productRepository.exists(product.getCode()))
    		return CommonResponse.error(PRODUCT_ALREADY_EXISTS);
    		
    	productRepository.save(product);
    	return CommonResponse.success(product);
    }

    @Override
    public CommonResponse<List<Product>> getProducts() {
        return CommonResponse.success((List<Product>) productRepository.findAll());
    }

    @Override
    public CommonResponse<Product> delete(Product product) throws NotFoundException {
    	
    	if (!productRepository.exists(product.getCode()))
    		return CommonResponse.error(PRODUCT_NOT_EXISTS);
    	
    	productRepository.delete(product);
    	return CommonResponse.success(product);
    }
    
    @Override
    public CommonResponse<Product> update(Product product) throws NotFoundException {
    	
    	if (!productRepository.exists(product.getCode()))
    		return CommonResponse.error(PRODUCT_NOT_EXISTS);
    	
    	productRepository.save(product);
    	return CommonResponse.success(product);
    }
    
    @Override
    public CommonResponse<Product> getProduct(String code) throws NotFoundException {
    	
    	Product product = productRepository.findOne(code);
    	
    	if (product != null)
    		return CommonResponse.success(productRepository.findOne(code));
    	else
    		return CommonResponse.error(PRODUCT_NOT_EXISTS);
    	
    }

}
