package net.mahdirazavi.testblog.techtrial.service;

import net.mahdirazavi.testblog.techtrial.model.Comment;
import net.mahdirazavi.testblog.techtrial.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    /*
     * Returns all the Comments related to article along with Pagination
     * information.
     */
    public List<Comment> findAll(Long articleId) {
//        return commentRepository.findAll().stream().filter(comment -> comment.getArticle().getId().equals(articleId)).collect(Collectors.toList());
        return commentRepository.findByArticleIdOrderByDate(articleId);
    }


    public Comment findById(Long commentID) {
        return commentRepository.findById(commentID).orElse(null);
    }

    /*
     * Save the default article.
     */
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

}
