package com.tm.tvshows.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tm.tvshows.entity.Category;
import com.tm.tvshows.entity.OmdbResponse;
import com.tm.tvshows.entity.Show;
import com.tm.tvshows.repository.CategoryRepository;
import com.tm.tvshows.repository.ShowRepository;
import com.tm.tvshows.service.api.OmdbService;
import com.tm.tvshows.service.api.ShowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

	private final ShowRepository showRepository;
	private final CategoryRepository categoryRepository;
	private final OmdbService omdbService;

	@Override
	public Show getShowFromDatabase(String title) {
		title = title.replaceAll(" ", "+");
		Show showResponse = new Show();
		Optional<Show> showOptional = showRepository.findByTitleContaining(title);
		if (showOptional.isPresent()) {
			showResponse = showOptional.get();
		} else {
			OmdbResponse omdbResponse = omdbService.getOmdbResponse(title);
			showResponse = setShow(omdbResponse);
		}
		return showResponse;
	}

	private Show setShow(OmdbResponse omdbResponse) {
		Show showResponse = new Show();
		showResponse.setTitle(omdbResponse.getTitle());
		showResponse.setDescription(omdbResponse.getPlot());
		showResponse.setReleased(omdbResponse.getReleased());
		showResponse.setWriter(omdbResponse.getWriter());
		showResponse.setAwards(omdbResponse.getAwards());
		showResponse.setImdbRating(omdbResponse.getImdbRatingInDouble());
		showResponse.setImdbVotes(omdbResponse.getImdbVotes());
		showResponse.setImdbId(omdbResponse.getImdbID());
		showResponse.setApproved(false);
		showResponse.setPosterUrl(omdbResponse.getPoster());
		showResponse.setSeasons(omdbResponse.getSeasonsInLong());
		showResponse.setCategories(getCategories(omdbResponse.getGenre()));
		showResponse.setUsers(null);
		showRepository.save(showResponse);
		return showResponse;
	}

	// TODO: bekérdez repoba, hogy létezik-e már a kategória, ha nem elmenti és hozzáadja a set-hez, ha igen akkor csak
	// hozzáadja a sethez
	private Set<Category> getCategories(String categoriesInString) {
		Set<Category> categories = new HashSet();
		String[] categoryArray = categoriesInString.split(",\\s+");
		for (String category : categoryArray) {
			Optional<Category> categoryOptional = categoryRepository.findByType(category);
			if (categoryRepository.findByType(category).isPresent()) {
				categories.add(categoryOptional.get());
			}
			Category categoryAdd = new Category();
			categoryAdd.setType(category);
			categoryRepository.save(categoryAdd);
			categories.add(categoryAdd);
		}
		return categories;
	}

}
