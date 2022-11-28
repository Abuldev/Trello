package uz.muhammad.jira.vo.auth.commentVO;

import lombok.Builder;
import uz.muhammad.jira.domains.auth.Comment;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;

public class CommentVO extends GenericVO {
    private String createdAt;
    private Long createdBy;

    public CommentVO(Long id, String  createdAt, Long createdBy) {
        super(id);
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    @Builder(builderMethodName = "childBuilder")
    public CommentVO(String  createdAt, Long createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public CommentVO(Comment comment) {
    }
}
