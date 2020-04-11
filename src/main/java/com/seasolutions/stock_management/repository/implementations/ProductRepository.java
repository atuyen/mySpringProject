package com.seasolutions.stock_management.repository.implementations;
import com.seasolutions.stock_management.model.entity.Product;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.IProductRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public class ProductRepository extends BaseRepository<Product> implements IProductRepository {
}
