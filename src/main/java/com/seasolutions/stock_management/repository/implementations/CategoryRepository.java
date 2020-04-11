package com.seasolutions.stock_management.repository.implementations;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.ICategoryRepository;
import com.seasolutions.stock_management.model.entity.Category;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;



@Transactional
@Repository
public class CategoryRepository extends BaseRepository<Category> implements ICategoryRepository {

}
