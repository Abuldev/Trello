package uz.muhammad.jira.mappers;

import uz.muhammad.jira.domains.auth.Column;
import uz.muhammad.jira.domains.auth.Project;
import uz.muhammad.jira.domains.auth.Task;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.vo.auth.columnVO.ColumnVO;
import uz.muhammad.jira.vo.auth.taskVO.TaskVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;

import java.time.LocalDateTime;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  15:22 (Friday)
 */
public class TaskMapper implements BaseMapper {

    public static Task getTask(TaskVO taskVO){
        Task task = new Task();
        task.setName(taskVO.getName());
        task.setId(System.currentTimeMillis());
        task.setCreatedAt(LocalDateTime.now().toString());
        task.setCreatedBy(taskVO.getId());

        return task;
    }

    private static TaskMapper instance;

    public static TaskMapper getInstance() {
        if (instance == null){
            instance = new TaskMapper();
        }
        return instance;
    }

    /**
     *
     * @param task
     * @return
     */
    public TaskVO getTaskVo(Task task) {

        /**
         * id;
         * name;
         * comments
         * members
         * createdAt
         * createdBy
         * updatedAt
         * updatedBy
         * status
         * deleted
         */

        TaskVO taskVO = new TaskVO();

        taskVO.setId(taskVO.getId());
        taskVO.setName(task.getName());
        taskVO.setComments(task.getComments());
        taskVO.setMembers(task.getMembers());
        taskVO.setCreatedAt(task.getCreatedAt());
        taskVO.setCreatedBy(task.getCreatedBy());
        taskVO.setUpdatedAt(task.getUpdatedAt());
        taskVO.setUpdatedBy(task.getUpdatedBy());
        taskVO.setStatus(task.getStatus());
        taskVO.setDeleted(task.isDeleted());
        return taskVO;
    }

}