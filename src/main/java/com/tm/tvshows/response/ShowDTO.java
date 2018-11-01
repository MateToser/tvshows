package com.tm.tvshows.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;

import lombok.Data;

@Data
@JsonView(View.Show.class)
public class ShowDTO {

	private List<ShowResponse> shows;
	private Integer totalPage;

}
