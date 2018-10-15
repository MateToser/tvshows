package com.tm.tvshows.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tm.tvshows.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

	Optional<Category> findByType(String type);

}
