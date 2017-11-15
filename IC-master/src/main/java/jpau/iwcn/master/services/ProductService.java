package jpau.iwcn.master.services;

import java.util.List;

import javassist.NotFoundException;
import jpau.iwcn.master.models.CommonResponse;

public interface ProductService <T,K> {

    public CommonResponse<T> insert(T product) throws NotFoundException;
    
    public CommonResponse<List<T>> getProducts();

    public CommonResponse<T> delete(T product) throws NotFoundException;
    
    public CommonResponse<T> update(T product) throws NotFoundException;
    
    public CommonResponse<T> getProduct(K code) throws NotFoundException;

}
