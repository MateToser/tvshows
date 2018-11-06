package com.tm.tvshows.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.Comment;
import com.tm.tvshows.entity.Show;

import lombok.Data;

@Data
@JsonView(View.Show.class)
public class SingleShowPageResponse {

	public SingleShowPageResponse() {

	}

	public SingleShowPageResponse(Show show) {
		this.show = show;
	}

	private Show show;
	private Boolean isLiked;
	private List<Comment> comments = new ArrayList<>();
	private List<SeasonResponse> seasons = new ArrayList<>();

}
