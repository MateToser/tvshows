package com.tm.tvshows.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tm.tvshows.entity.Episode;

@Repository
public interface EpisodeRepository extends PagingAndSortingRepository<Episode, Integer> {

}
