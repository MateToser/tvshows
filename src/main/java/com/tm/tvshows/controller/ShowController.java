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

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.Show;
import com.tm.tvshows.service.api.ShowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/show")
@Slf4j
public class ShowController {

	private final ShowService showService;

	@GetMapping(value = "/{title}")
	@ResponseBody
	@JsonView(View.Show.class)
	public ResponseEntity<Show> getShow(@PathVariable(value = "title") String title) {
		try {
			Show showResponse = showService.getShowFromDatabase(title);
			if (showResponse == null) {
				log.error("Nincs ilyen sorozat: {}", "/api/show/" + title);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(showResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a sorozat lekérdezés: {}", "/api/show/" + title);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a sorozat lekérdezés: {}", "/api/show/" + title);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a sorozat lekérdezés: {}", "/api/show/" + title);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
