package com.tm.tvshows.service.api;

import org.springframework.data.domain.Page;

import com.tm.tvshows.entity.Show;
import com.tm.tvshows.entity.UserPrincipal;

public interface ShowService {

	Show getShowFromDatabase(String title);

	Boolean likeShow(Integer id, UserPrincipal currentUser) throws Exception;

	Page<Show> getOrderedShows(String order, Integer page, Integer count);

}
