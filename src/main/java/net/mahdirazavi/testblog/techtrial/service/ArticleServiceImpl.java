package net.mahdirazavi.testblog.techtrial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.mahdirazavi.testblog.techtrial.model.Article;
import net.mahdirazavi.testblog.techtrial.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	public Article save(Article article) {
		return articleRepository.save(article);
	}

	public Article findById(Long id) {
		return articleRepository.findById(id).orElse(null);
	}

	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	public void delete(Long id) {
		articleRepository.deleteById(id);
	}

	public List<Article> search(String search) {
		return articleRepository.findTop10ByTitleContainingIgnoreCase(search);
	}

}