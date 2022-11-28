package uz.muhammad.jira.services.auth;

import lombok.NonNull;
import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.ProjectCriteria;
import uz.muhammad.jira.domains.auth.Project;
import uz.muhammad.jira.mappers.ProjectMapper;
import uz.muhammad.jira.repository.AbstractRepository;
import uz.muhammad.jira.repository.auth.ProjectRepository;
import uz.muhammad.jira.services.GenericCRUDService;
import uz.muhammad.jira.vo.auth.projectVO.ProjectCreateVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectUpdateVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ErrorVO;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  14:49 (Friday)
 */
public class ProjectService extends AbstractRepository<ProjectRepository, ProjectMapper> implements
        GenericCRUDService<ProjectVO, ProjectCreateVO, ProjectUpdateVO, ProjectCriteria, Long> {

    private static ProjectService instance;

    protected ProjectService(ProjectRepository repository, ProjectMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ResponseEntity<Data<Long>> create(@NonNull ProjectCreateVO dto) {
        Optional<Project> projectOptional = repository.findByUsername(dto.getName());
        if (projectOptional.isPresent()) {
            return new ResponseEntity<>(new Data<>(ErrorVO
                    .builder()
                    .friendlyMessage("Task name '%s' already taken".formatted(dto.getName()))
                    .status(400)
                    .build()));
        }
        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(System.currentTimeMillis());
        projectVO.setName(dto.getName());
        projectVO.setCreatedBy(dto.getCreatedBy());
        projectVO.setDeadline(dto.getDeadline());
        projectVO.setCreatedAt(LocalDateTime.now().toString());

        repository.create(ProjectMapper.getProject(projectVO));

        return new ResponseEntity<>(new Data<>(projectVO.getId()));
    }

    @Override
    public ResponseEntity<Data<String>> delete(@NonNull Long aLong) {
        repository.deleteByID(aLong);
        return new ResponseEntity<>(new Data<>("Deleted"));
    }

    @Override
    public ResponseEntity<Data<String>> update(@NonNull ProjectUpdateVO dto) {
        Project project = repository.findById(dto.getId()).get();
        project.setName(dto.getName());
        project.setUpdatedAt(dto.getUpdatedAt());
        project.setUpdatedBy(dto.getUpdatedBy());

        repository.update(project);
        return new ResponseEntity<>(new Data<>("Updated"));

    }

    @Override
    public ResponseEntity<Data<ProjectVO>> findById(@NonNull Long id) {
        Optional<Project> optionalProject = repository.findById(id);

        ResponseEntity<Data<ProjectVO>> response;

        if (optionalProject.isPresent()){
            response = new ResponseEntity<>(new Data<>(mapper.getProjectVo(optionalProject.get())));
            return response;
        }
        ErrorVO errorVO = new ErrorVO("Project not found", "Project not found", 400);
        response = new ResponseEntity<>(new Data<>(errorVO));
        return response;

    }

    @Override
    public ResponseEntity<Data<List<ProjectVO>>> findAll(@NonNull ProjectCriteria criteria) {
        List<ProjectVO> projectList = repository.findAll(criteria)
                .orElse(new ArrayList<>())
                .stream().map(ProjectVO::new)
                .toList();

        return new ResponseEntity<>(new Data<>(projectList, projectList.size()));
    }

    public static ProjectService getInstance() {
        if (instance == null) {
            instance = new ProjectService(
                    ApplicationContextHolder.getBean(ProjectRepository.class),
                    ApplicationContextHolder.getBean(ProjectMapper.class)
            );
        }
        return instance;
    }

    public ResponseEntity<Data<List<ProjectVO>>> getProjectsByIds(List<Long> ids) {

        List<ProjectVO> projects = new ArrayList<>();

        for (Long id : ids) {
            projects.add(findById(id).getData().getBody());
        }

        return new ResponseEntity<>(new Data<>(projects));
    }

    public ResponseEntity<Data<String>> addColToPro(Long columnId, Long proId) {

        for (Project project : repository.findAll(new ProjectCriteria()).get()) {
            if (project.getId()==proId){
                List<Long> columns = project.getColumns();
                columns.add(columnId);
                project.setColumns(columns);
                repository.update(project);
                return new ResponseEntity<>(new Data<>("Column added"));
            }
        }
        return new ResponseEntity<>(new Data<>(new ErrorVO("Column not found", "Org not found", 400)));

    }
}
