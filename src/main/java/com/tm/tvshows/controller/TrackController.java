package com.tm.tvshows.controller;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.CurrentUser;
import com.tm.tvshows.entity.Episode;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.service.api.EpisodeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/track")
@Slf4j
public class TrackController {

	@Autowired
	private EpisodeService episodeService;

	@PostMapping(value = "/episode/{episodeId}")
	@ResponseBody
	@JsonView(View.Episode.class)
	public ResponseEntity<Episode> trackEpisode(@PathVariable(value = "episodeId") Integer episodeId,
			@CurrentUser UserPrincipal currentUser) {
		try {
			Episode episodeResponse = episodeService.trackEpisode(episodeId, currentUser);
			if (episodeResponse == null) {
				log.error("Nincs ilyen epizód: {}", "/api/track/episode/" + episodeId);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(episodeResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült az epizód lekérdezés: {}", "/api/track/episode/" + episodeId);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült az epizód lekérdezés: {}", "/api/track/episode/" + episodeId);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült az epizód lekérdezés: {}", "/api/track/episode/" + episodeId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/season/{seasonId}")
	@ResponseBody
	@JsonView(View.Episode.class)
	public ResponseEntity<Boolean> trackSeason(@PathVariable(value = "seasonId") Integer seasonId,
			@CurrentUser UserPrincipal currentUser) {
		try {
			Boolean seasonResponse = episodeService.trackSeason(seasonId, currentUser);
			if (seasonResponse == null) {
				log.error("Nincs ilyen évad: {}", "/api/track/season/" + seasonId);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(seasonResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült az évad lekérdezés: {}", "/api/track/season/" + seasonId);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült az évad lekérdezés: {}", "/api/track/season/" + seasonId);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült az évad lekérdezés: {}", "/api/track/season/" + seasonId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
