package com.tm.tvshows.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.Season;

import lombok.Data;

@Data
@JsonView(View.Show.class)
public class SeasonResponse {

	public SeasonResponse() {

	}

	public SeasonResponse(Season season) {
		this.season = season;
	}

	private Season season;
	private List<EpisodeResponse> episodes;
	private Double percent;

}
