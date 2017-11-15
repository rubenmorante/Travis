package jpau.iwcn.master.repositories;

import org.springframework.data.repository.CrudRepository;

import jpau.iwcn.master.entities.Product;

public interface ProductRepository extends CrudRepository<Product,String> {
	
}
