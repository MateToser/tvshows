package com.tm.tvshows.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tm.tvshows.entity.Show;

@Repository
public interface ShowRepository extends CrudRepository<Show, Integer> {

	Optional<Show> findByTitleContaining(String title);

}
