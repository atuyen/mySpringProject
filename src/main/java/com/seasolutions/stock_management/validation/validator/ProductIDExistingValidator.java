package com.seasolutions.stock_management.validation.validator;

import com.seasolutions.stock_management.service.interfaces.ICategoryService;
import com.seasolutions.stock_management.validation.ananotation.ProductIDExisting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Component
public class ProductIDExistingValidator implements ConstraintValidator<ProductIDExisting, Long> {
    @Autowired
    private ICategoryService categoryService;

    @Override
    public boolean isValid(Long categoryId, ConstraintValidatorContext context) {
        return  categoryService.findById(categoryId)!=null;
    }
}