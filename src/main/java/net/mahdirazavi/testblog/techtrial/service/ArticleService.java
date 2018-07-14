package net.mahdirazavi.testblog.techtrial.service;

import java.util.List;

import net.mahdirazavi.testblog.techtrial.model.Article;

/*
 * This interface provides all methods to access the functionality. See ArticleServiceImpl for implementation.
 * 
 * @author mahdi
 */
public interface ArticleService {
	/*
	 * Save the default article.
	 */
	Article save(Article article);

	/*
	 * FindById will find the specific user form list.
	 * 
	 */
	Article findById(Long id);

	/*
    * findAll will find all articles form list.
    *
    */
	List<Article> findAll();

	/*
	 * Delete a particular article with id
	 */
	void delete(Long id);

	/*
	 * Search Articles Table matching the title and return result with pagination.
	 */
	List<Article> search(String title);

}
