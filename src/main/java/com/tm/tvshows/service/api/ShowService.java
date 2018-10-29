package com.tm.tvshows.service.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.tvshows.entity.Show;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.response.ShowResponse;

public interface ShowService {

	Show getShowFromDatabase(String title);

	ShowResponse getShowById(Integer id, UserPrincipal currentUser);

	Boolean likeShow(Integer id, UserPrincipal currentUser) throws Exception;

	Page<Show> getOrderedShows(String order, Integer page, Integer count, UserPrincipal currentUser);

	List<ShowResponse> getAllShows(UserPrincipal currentUser);

}
