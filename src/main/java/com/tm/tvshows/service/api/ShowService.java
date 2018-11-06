package com.tm.tvshows.service.api;

import java.util.List;

import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.response.SearchResponse;
import com.tm.tvshows.response.ShowDTO;
import com.tm.tvshows.response.ShowResponse;
import com.tm.tvshows.response.SingleShowPageResponse;

public interface ShowService {

	Integer addShowToDatabase(String title);

	SingleShowPageResponse getShowById(Integer id, UserPrincipal currentUser);

	Boolean likeShow(Integer id, UserPrincipal currentUser) throws Exception;

	ShowDTO getOrderedShows(String order, Integer page, UserPrincipal currentUser);

	List<ShowResponse> getAllShows(UserPrincipal currentUser);

	List<SearchResponse> searchShows(String title);

}
