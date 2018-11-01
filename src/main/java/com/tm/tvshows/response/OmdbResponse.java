package com.tm.tvshows.response;

import java.util.List;

import lombok.Data;

@Data
public class OmdbResponse {

	private OmdbSeriesResponse show;
	private List<OmdbSeasonResponse> seasons;

}
