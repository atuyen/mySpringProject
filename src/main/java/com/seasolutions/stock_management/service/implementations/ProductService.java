package com.seasolutions.stock_management.service.implementations;


import com.seasolutions.stock_management.model.entity.Product;
import com.seasolutions.stock_management.model.view_model.ProductViewModel;
import com.seasolutions.stock_management.repository.interfaces.IProductRepository;
import com.seasolutions.stock_management.service.base.BaseService;
import com.seasolutions.stock_management.service.interfaces.IProductService;
import com.seasolutions.stock_management.util.MappingUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ProductService extends BaseService<Product,ProductViewModel> implements IProductService {
    @Autowired
    IProductRepository productRepository;


    @Override
    public List<ProductViewModel> test() {
       String query= String.join(" ", new String[] {
                       "select distinct (p)  from Product p ",
                       "join InvoiceDetail ind on p.id = ind.product.id ",
                       "join Invoice i on ind.invoice.id = i.id ",
                       "where 1 = ( select count (ind) from InvoiceDetail ind where ind.product.id = p.id ) ",
                       "AND YEAR(i.orderDate) = 1996"
               });

        List<Product> products = productRepository.findDataByQuery(query,null);
        List<ProductViewModel> productViewModels = MappingUtils.map(products,new TypeToken<List<ProductViewModel>>(){}.getType());
        return productViewModels;
    }
}
