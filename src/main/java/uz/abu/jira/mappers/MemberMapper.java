package uz.muhammad.jira.mappers;

import uz.muhammad.jira.domains.auth.Column;
import uz.muhammad.jira.domains.auth.Member;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.vo.auth.userVO.UserVO;

import java.time.LocalDateTime;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  15:23 (Friday)
 */
public class MemberMapper implements BaseMapper {

    private static MemberMapper instance;

//    public static Member getMember(MemberVO memberVO){
//        Member member = new Member();
//        member.setName(memberVO.getName());
//        member.setId(System.currentTimeMillis());
//        member.setCreatedAt(LocalDateTime.now());
//        member.setCreatedBy(memberVO.getId());
//
//        return member;
//    }

    public static MemberMapper getInstance() {
        if (instance==null){
            instance = new MemberMapper();
        }
        return instance;
    }

}
