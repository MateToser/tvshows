package com.tm.tvshows.service.impl;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.tvshows.entity.Episode;
import com.tm.tvshows.entity.User;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.repository.EpisodeRepository;
import com.tm.tvshows.repository.UserRepository;
import com.tm.tvshows.service.api.EpisodeService;

@Service
public class EpisodeServiceImpl implements EpisodeService {

	@Autowired
	private EpisodeRepository episodeRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Episode trackEpisode(Integer episodeId, UserPrincipal currentUser) {
		User user = userRepository.findById(currentUser.getId()).get();
		Optional<Episode> episodeOptional = episodeRepository.findById(episodeId);
		if (episodeOptional.isPresent()) {
			if (episodeOptional.get().getUsers() != null) {
				episodeOptional.get().getUsers().add(user);
				episodeRepository.save(episodeOptional.get());
				return episodeOptional.get();
			} else {
				episodeOptional.get().setUsers(new HashSet<>());
				episodeOptional.get().getUsers().add(user);
				episodeRepository.save(episodeOptional.get());
				return episodeOptional.get();
			}
		}
		return null;
	}

}
