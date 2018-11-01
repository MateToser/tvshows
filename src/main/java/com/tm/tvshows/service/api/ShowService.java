package com.tm.tvshows.service.api;

import java.util.List;

import com.tm.tvshows.entity.Show;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.response.ShowDTO;
import com.tm.tvshows.response.ShowResponse;

public interface ShowService {

	Show getShowFromDatabase(String title);

	ShowResponse getShowById(Integer id, UserPrincipal currentUser);

	Boolean likeShow(Integer id, UserPrincipal currentUser) throws Exception;

	ShowDTO getOrderedShows(String order, Integer page, UserPrincipal currentUser);

	List<ShowResponse> getAllShows(UserPrincipal currentUser);

}
