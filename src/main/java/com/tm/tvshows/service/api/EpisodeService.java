package com.tm.tvshows.service.api;

import com.tm.tvshows.entity.Episode;
import com.tm.tvshows.entity.UserPrincipal;

public interface EpisodeService {

	Episode trackEpisode(Integer episodeId, UserPrincipal currentUser);

	Boolean trackSeason(Integer seasonId, UserPrincipal currentUser);

}
