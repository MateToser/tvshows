package com.tm.tvshows.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tm.tvshows.entity.Episode;

@Repository
public interface EpisodeRepository extends PagingAndSortingRepository<Episode, Integer> {

	List<Episode> findAllBySeasonId(Integer seasonId);

}
