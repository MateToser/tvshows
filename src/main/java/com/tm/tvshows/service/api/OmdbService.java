package com.tm.tvshows.service.api;

import com.tm.tvshows.entity.OmdbResponse;

public interface OmdbService {

	OmdbResponse getOmdbResponse(String title);

}
