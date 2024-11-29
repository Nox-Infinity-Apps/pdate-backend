package com.noxinfinity.pdating.Applications.Category;

import com.noxinfinity.pdating.Applications.Base.BaseServices;
import com.noxinfinity.pdating.Domains.CategoryManagement.CategoryRepository;
import com.noxinfinity.pdating.graphql.types.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategory{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Category> getList(){
        return BaseServices.mapCategory(categoryRepository.findAll());
    }
}
