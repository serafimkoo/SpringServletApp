package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long>, JpaSpecificationExecutor<Ship> {
    Page<Ship> findAll(@Nullable Specification<Ship> spec, Pageable pageable);

    List<Ship> findAll();
}
