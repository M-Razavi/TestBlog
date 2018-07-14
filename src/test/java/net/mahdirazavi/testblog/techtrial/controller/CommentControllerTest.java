package net.mahdirazavi.testblog.techtrial.controller;

import net.mahdirazavi.testblog.techtrial.model.Article;
import net.mahdirazavi.testblog.techtrial.model.Comment;
import net.mahdirazavi.testblog.techtrial.repository.CommentRepository;
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
public class CommentControllerTest {

    Random random;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private TestRestTemplate template;

    @Before
    public void setup() throws Exception {
        random = new Random();
    }

    @Test
    public void testCommentCreate() throws Exception {
        HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"test-CommentCreate" + random.nextDouble() + "\" , \"content\":\" test content\",\"date\":\"2018-06-05T00:00:00\", \"published\": true}");
        ResponseEntity<Article> resultArticle = template.postForEntity("/articles", article, Article.class);
        if (resultArticle.getBody() != null) {
            Long recentlyAddedArticleId = resultArticle.getBody().getId();
            HttpEntity<Object> newComment = getHttpEntity("{\"email\":\"test2@email.com\",\"message\":\"comment message\",\"date\":\"2018-06-05T00:00:00\"}");
            ResponseEntity<Comment> resultComment = template.postForEntity("/articles/" + recentlyAddedArticleId + "/comments/", newComment, Comment.class);
            Assert.assertNotNull(resultComment);
        }
    }


    @Test
    public void testCommentFindByArticle() throws Exception {

        HttpEntity<Object> article = getHttpEntity("{\"email\": \"user2@gmail.com\", \"title\": \"test-CommentFind" + random.nextDouble() + "\" , \"content\":\" test content\",\"date\":\"2018-06-05T00:00:00\", \"published\": true}");
        ResponseEntity<Article> resultArticle = template.postForEntity("/articles", article, Article.class);
        if (resultArticle.getBody() != null) {
            Long recentlyAddedArticleId = resultArticle.getBody().getId();
            HttpEntity<Object> newComment1 = getHttpEntity("{\"email\":\"test2@email.com\",\"message\":\"comment message 1\",\"date\":\"2018-06-05T00:00:00\"}");
            HttpEntity<Object> newComment2 = getHttpEntity("{\"email\":\"test3@email.com\",\"message\":\"comment message 2\",\"date\":\"2018-06-05T00:00:00\"}");
            ResponseEntity<Comment> resultComment1 = template.postForEntity("/articles/" + recentlyAddedArticleId + "/comments/", newComment1, Comment.class);
            ResponseEntity<Comment> resultComment2 = template.postForEntity("/articles/" + recentlyAddedArticleId + "/comments/", newComment2, Comment.class);

            ResponseEntity<Comment[]> resultComments = template.getForEntity("/articles/" + recentlyAddedArticleId + "/comments/", Comment[].class);

            for (Comment cmt : resultComments.getBody()) {
                Comment foundComment = commentRepository.findById(cmt.getId()).orElse(null);
                if (foundComment != null) {
                    if (!foundComment.getArticle().getId().equals(recentlyAddedArticleId)) {
                        Assert.fail();
                    }
                }
            }
            Assert.assertTrue(resultComments.getBody().length > 0);
        }
    }


    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }
}
