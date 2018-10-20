package com.tm.tvshows.service.api;

import com.tm.tvshows.entity.Show;
import com.tm.tvshows.entity.UserPrincipal;

public interface ShowService {

	Show getShowFromDatabase(String title);

	Boolean likeShow(Integer id, UserPrincipal currentUser) throws Exception;

}
