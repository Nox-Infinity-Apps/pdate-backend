package com.noxinfinity.pdating.Domains.CategoryManagement;

import com.noxinfinity.pdating.Entities.Enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
