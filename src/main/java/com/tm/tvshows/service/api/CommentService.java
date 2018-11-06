package com.tm.tvshows.service.api;

import com.tm.tvshows.entity.Comment;
import com.tm.tvshows.entity.UserPrincipal;

public interface CommentService {

	Comment addComment(Integer showId, String message, UserPrincipal currentUser);

}
