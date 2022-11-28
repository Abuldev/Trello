package uz.muhammad.jira.vo.auth.memberVO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.muhammad.jira.domains.auth.Member;
import uz.muhammad.jira.enums.MemberStatus;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor

public class MemberVO extends GenericVO {

    private Long id;
    private Long userId;
    private Long organizationId;
    private Long projectId;
    private MemberStatus status;
    private boolean blocked;
    private boolean deleted;

}
