package com.tm.tvshows.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tm.tvshows.entity.Show;

@Repository
public interface ShowRepository extends PagingAndSortingRepository<Show, Integer> {

	Optional<Show> findByTitleContaining(String title);

}
