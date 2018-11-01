package com.tm.tvshows.controller;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.tvshows.response.OmdbResponse;
import com.tm.tvshows.service.api.OmdbService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/omdb")
@Slf4j
public class OmdbController {

	private final OmdbService omdbService;

	@GetMapping(value = "/tvshow/{title}")
	@ResponseBody
	public ResponseEntity<OmdbResponse> getOmdbResponse(@PathVariable(value = "title") String title) {
		try {
			OmdbResponse omdbResponse = omdbService.getOmdbResponse(title);
			if (omdbResponse == null) {
				log.error("Nincs ilyen sorozat az omdb-ben: {}", "/api/omdb/tvshow/" + title);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(omdbResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült az Omdb lekérdezés: {}", "/api/omdb/tvshow/" + title);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült az Omdb lekérdezés: {}", "/api/omdb/tvshow/" + title);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült az Omdb lekérdezés: {}", "/api/omdb/tvshow/" + title);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
