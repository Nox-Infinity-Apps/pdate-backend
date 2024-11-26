package com.noxinfinity.pdating.Primary.Category;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.noxinfinity.pdating.Applications.Category.CategoryService;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class Category {
    private CategoryService categoryService;

    @Autowired
    public Category(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @DgsQuery
    @ValidateToken
    public List<com.noxinfinity.pdating.graphql.types.Category> getListCategory() {
        return categoryService.getList();
    }
}
