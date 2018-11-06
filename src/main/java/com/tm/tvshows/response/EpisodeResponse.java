package com.tm.tvshows.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.Episode;

import lombok.Data;

@Data
@JsonView(View.Show.class)
public class EpisodeResponse {

	public EpisodeResponse() {

	}

	public EpisodeResponse(Episode episode) {
		this.episode = episode;
	}

	private Episode episode;
	private Boolean isWatched;

}
