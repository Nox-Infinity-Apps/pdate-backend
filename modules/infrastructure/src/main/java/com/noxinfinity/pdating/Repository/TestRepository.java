package com.noxinfinity.pdating.Repository;

import com.noxinfinity.pdating.Entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
