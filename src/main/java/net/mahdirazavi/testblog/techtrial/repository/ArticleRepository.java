package net.mahdirazavi.testblog.techtrial.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import net.mahdirazavi.testblog.techtrial.model.Article;

@RepositoryRestResource(exported = false)
public interface ArticleRepository extends CrudRepository<Article, Long> {

    List<Article> findAll();

    List<Article> findTop10ByTitleContainingIgnoreCase(String title);

}
