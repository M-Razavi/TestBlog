package net.mahdirazavi.testblog.techtrial.service;

import java.util.List;
import net.mahdirazavi.testblog.techtrial.model.Comment;

public interface CommentService {

	/*
	 * Returns all the Comments related to article along with Pagination
	 * information.
	 */
	List<Comment> findAll(Long articleId);

	/*
	 * Return a comment specified with ID
	 */
	Comment findById(Long commentID);

	/*
	 * Save the default article.
	 */
	Comment save(Comment comment);

}
