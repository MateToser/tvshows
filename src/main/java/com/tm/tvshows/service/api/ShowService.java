package com.tm.tvshows.service.api;

import com.tm.tvshows.entity.Show;

public interface ShowService {

	Show getShowFromDatabase(String title);

}
