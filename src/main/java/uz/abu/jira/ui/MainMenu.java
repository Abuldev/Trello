package uz.muhammad.jira.ui;

import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.OrgCriteria;
import uz.muhammad.jira.services.auth.MemberService;
import uz.muhammad.jira.services.auth.OrgService;
import uz.muhammad.jira.services.auth.UserService;
import uz.muhammad.jira.utils.Color;
import uz.muhammad.jira.utils.Reader;
import uz.muhammad.jira.utils.Tools;
import uz.muhammad.jira.utils.Writer;
import uz.muhammad.jira.vo.auth.orgVO.OrgCreateVO;
import uz.muhammad.jira.vo.auth.orgVO.OrgVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team <Developers>
 * @project TrelloBY
 * @since 16/06/22   11:54   (Thursday)
 */

public class MainMenu {

    private final static OrgService orgService = ApplicationContextHolder.getBean(OrgService.class);
    private final static MemberService memberService = ApplicationContextHolder.getBean(MemberService.class);
    private final static UserService userService = ApplicationContextHolder.getBean(UserService.class);

    public static void control(){
        Tools.clear();
        Tools.userInfo(Session.userName);
        Writer.printlnMiddle("1. Add organization", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("2. Organizations", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("0. Back", 80, ' ', Color.GREEN);

        String choice = Reader.readLineMiddle(" => ");

        switch (choice){
            case "1": addOrganization(); break;
            case "2": showOrganizations(); break;
            case "0": return;
        }

        control();

    }



    private static void showOrganizations() {


        Tools.userInfo(Session.userName);
        List<OrgVO> orgVOS = new ArrayList<>();

        for (OrgVO orgVO : orgService.findAll(new OrgCriteria()).getData().getBody()) {
            if(orgVO.getMembers().containsKey(Session.userId) && !orgVO.isDeleted()){
                orgVOS.add(orgVO);
            }
        }
        showAll(orgVOS);
    }

    private static void addOrganization() {

        Tools.clear();
        Tools.userInfo(Session.userName);

        OrgCreateVO createVO = new OrgCreateVO();

        createVO.setName(Reader.readLine("Enter organization name: "));
        createVO.setCreatedBy(Session.userId);

        ResponseEntity<Data<Long>> responseData = orgService.create(createVO);

        if (responseData.getData().isSuccess()) {
           control(); // Add and back
        } else {
            Writer.println(responseData.getData().getError(), Color.RED);
        }

    }

    public static void showAll(List<OrgVO> orgs) {

        Tools.clear();
        Tools.userInfo(Session.userName);
        Writer.printMiddle("All organizations", 100, ' ', Color.CYAN);
        Writer.println("");
        int order = 1;

        if (orgs.size() == 0){
            Writer.printlnMiddle("You have no organizations", 100, ' ', Color.GREEN);
        } else {
            for (OrgVO org : orgs) {
                Writer.println("\t\t" + order++ + ") " + org.getName() + " your role:  " + org.getMembers().get(Session.userId));
            }
        }

        Writer.println("0. Back");

        int choice = Reader.readIntMiddle(" => ");

        if (choice==0) {
            return;
        } else {
            OrgMenu.showOrg(orgs.get(choice-1));
        }

    }

}
// ⌄ ›
