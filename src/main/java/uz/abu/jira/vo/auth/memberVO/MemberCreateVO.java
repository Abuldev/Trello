package uz.muhammad.jira.vo.auth.memberVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.muhammad.jira.enums.MemberStatus;
import uz.muhammad.jira.vo.BaseVO;

@Getter
@Setter
@Builder
public class MemberCreateVO implements BaseVO {
    private Long id;
    private Long userId;
    private Long organizationId;
    private Long projectId;
    private MemberStatus status;
}
