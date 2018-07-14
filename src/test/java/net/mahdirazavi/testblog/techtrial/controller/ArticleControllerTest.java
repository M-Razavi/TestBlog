package net.mahdirazavi.testblog.techtrial.controller;

import net.mahdirazavi.testblog.techtrial.model.Article;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticleControllerTest {

    Random random;
    @Autowired
    private TestRestTemplate template;

    @Before
    public void setup() throws Exception {
        random = new Random();
    }

    @Test
    public void testArticleShouldBeCreated() throws Exception {
        HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"test-Create" + random.nextDouble() + "\" , \"content\":\" test content\",\"date\":\"2018-06-05T00:00:00\", \"published\": true}");
        ResponseEntity<Article> resultAsset = template.postForEntity("/articles", article, Article.class);
        Assert.assertNotNull(resultAsset.getBody().getId());
    }

    @Test
    public void testArticleFindById() throws Exception {
        HttpEntity<Object> article = getHttpEntity("{\"email\": \"test2@test.com\", \"title\": \"test-FindById" + random.nextDouble() + "\" , \"content\":\" test content\",\"date\":\"2018-06-05T00:00:00\", \"published\": true}");
        ResponseEntity<Article> resultAssetPost = template.postForEntity("/articles", article, Article.class);
        System.out.println("testArticleFindById-post-status=" + resultAssetPost.getStatusCodeValue());
        Long newArticleId = resultAssetPost.getBody().getId();

        ResponseEntity<Article> resultAssetGet = template.getForEntity("/articles/" + newArticleId, Article.class);

        Assert.assertEquals(resultAssetGet.getBody(), resultAssetPost.getBody());
    }

    @Test
    public void testArticleUpdate() throws Exception {
        HttpEntity<Object> article = getHttpEntity("{\"email\": \"test3@test.com\", \"title\": \"test-Update" + random.nextDouble() + "\" , \"content\":\" test content\",\"date\":\"2018-06-05T00:00:00\", \"published\": true}");
        ResponseEntity<Article> resultAssetPost = template.postForEntity("/articles", article, Article.class);

        long lastInsertedArticle = 0;
        if (resultAssetPost.getBody() != null) {
            lastInsertedArticle = resultAssetPost.getBody().getId();

            String newContent = "Updated Content";
            ResponseEntity<Article> resultAssetGet = template.getForEntity("/articles/" + lastInsertedArticle, Article.class);
            resultAssetGet.getBody().setContent(newContent);

            template.put("/articles/" + lastInsertedArticle, resultAssetGet.getBody());

            resultAssetGet = template.getForEntity("/articles/" + lastInsertedArticle, Article.class);

            Assert.assertEquals(resultAssetGet.getBody().getContent(), newContent);
        }
    }

    @Test
    public void testArticleSearch() throws Exception {
        HttpEntity<Object> article = getHttpEntity("{\"email\": \"test4@test.com\", \"title\": \"test-Search" + random.nextDouble() + "\" , \"content\":\" test content\",\"date\":\"2018-06-05T00:00:00\", \"published\": true}");
        ResponseEntity<Article> resultAssetPost = template.postForEntity("/articles", article, Article.class);

        if (resultAssetPost.getBody() != null) {
            ResponseEntity<Article[]> resultAssetGet = template.getForEntity("/articles/search/?text=" + "Search", Article[].class);

            Assert.assertNotNull(resultAssetGet.getBody());
        }
    }


    @Test
    public void testArticleFindAll() throws Exception {
        HttpEntity<Object> article = getHttpEntity("{\"email\": \"test5@test.com\", \"title\": \"test-FindAll" + random.nextDouble() + "\" , \"content\":\" test content\",\"date\":\"2018-06-05T00:00:00\", \"published\": true}");
        ResponseEntity<Article> resultAssetPost = template.postForEntity("/articles", article, Article.class);
        if (resultAssetPost.getBody() != null) {
            ResponseEntity<Article[]> resultAssetGet = template.getForEntity("/articles", Article[].class);
            Assert.assertNotNull(resultAssetGet);
        }
    }

    @Test
    public void testArticleDelete() throws Exception {
        HttpEntity<Object> article = getHttpEntity("{\"email\": \"test7@test.com\", \"title\": \"test-Delete\" , \"content\":\" test content\",\"date\":\"2018-06-05T00:00:00\", \"published\": true}");
        ResponseEntity<Article> resultAssetPost = template.postForEntity("/articles", article, Article.class);
        long recentlyAddedArticleId = resultAssetPost.getBody().getId();

        ResponseEntity<Article[]> resultAssetGet = template.getForEntity("/articles", Article[].class);
        for (Article ar : resultAssetGet.getBody()) {
            template.delete("/articles/" + ar.getId());
        }

        ResponseEntity<Article> result = template.getForEntity("/articles/" + recentlyAddedArticleId, Article.class);
        Assert.assertNull(result.getBody());
    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }
}
