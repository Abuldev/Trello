package uz.muhammad.jira.mappers;

import uz.muhammad.jira.domains.auth.Organization;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.vo.auth.orgVO.OrgVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  15:22 (Friday)
 */
public class OrgMapper implements BaseMapper {

    private static OrgMapper instance;

    public static Organization getOrganization(OrgVO orgVO){

        Organization organization = new Organization();
        organization.setId(orgVO.getId());
        organization.setName(orgVO.getName());
        organization.setProjects(orgVO.getProjects());
        organization.setCreatedBy(orgVO.getCreatedBy());
        organization.setCreatedAt(orgVO.getCreatedAt().toString());
        organization.setMembers(orgVO.getMembers());

        return organization;
    }

    public static OrgMapper getInstance() {
        if (instance == null){
            instance = new OrgMapper();
        }
        return instance;
    }

    public OrgVO getOrgVO(Organization organization) {
        OrgVO orgVO = new OrgVO();
        /**
         * Long id;
         * String name;
         * Long ownerId;
         * List<Long> projects = new ArrayLi
         * List<Long> members = new ArrayLis
         * LocalDateTime createdAt;
         * Long createdBy;
         * LocalDateTime updatedAt;
         * Long updatedBy;
         * Boolean deleted = false;
         * Boolean blocked=  false;
         */
        orgVO.setId(organization.getId());
        orgVO.setName(organization.getName());
        orgVO.setProjects(organization.getProjects());
        orgVO.setMembers(organization.getMembers());
        orgVO.setCreatedAt(organization.getCreatedAt());
        orgVO.setUpdatedAt(organization.getUpdatedAt());
        orgVO.setUpdatedBy(organization.getUpdatedBy());
        orgVO.setDeleted(organization.isDeleted());
        orgVO.setBlocked(organization.isBlocked());
        return orgVO;
    }
}
