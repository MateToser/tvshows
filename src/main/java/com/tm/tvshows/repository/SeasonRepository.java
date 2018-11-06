package com.tm.tvshows.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tm.tvshows.entity.Season;

@Repository
public interface SeasonRepository extends PagingAndSortingRepository<Season, Integer> {

	List<Season> findAllByShowId(Integer showId);

}
