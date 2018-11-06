package com.tm.tvshows.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.Comment;
import com.tm.tvshows.entity.Show;

import lombok.Data;

@Data
@JsonView(View.Show.class)
public class ShowResponse {

	public ShowResponse() {

	}

	public ShowResponse(Show show) {
		this.show = show;
	}

	private Show show;
	private List<Comment> comments;
	private Boolean isLiked;

}
