package uz.muhammad.jira.services.auth;

import lombok.NonNull;
import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.ColumnCriteria;
import uz.muhammad.jira.criteria.UserCriteria;
import uz.muhammad.jira.domains.auth.Column;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.mappers.BaseMapper;
import uz.muhammad.jira.mappers.ColumnMapper;
import uz.muhammad.jira.repository.AbstractRepository;
import uz.muhammad.jira.repository.auth.ColumnRepository;
import uz.muhammad.jira.services.GenericCRUDService;
import uz.muhammad.jira.vo.auth.columnVO.ColumnCreateVO;
import uz.muhammad.jira.vo.auth.columnVO.ColumnUpdateVO;
import uz.muhammad.jira.vo.auth.columnVO.ColumnVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ErrorVO;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 16/06/22  15:00 (Thursday)
 */
public class ColumnService extends AbstractRepository<ColumnRepository, ColumnMapper> implements
        GenericCRUDService<ColumnVO, ColumnCreateVO, ColumnUpdateVO, ColumnCriteria, Long> {

    private static ColumnService instance;

    private ColumnService(ColumnRepository repository, ColumnMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ResponseEntity<Data<Long>> create(@NonNull ColumnCreateVO dto) {
        Column column = new Column();
        Optional<Column> columnOptional = repository.findByUsername(dto.getName());
        if (columnOptional.isPresent()) {
            return new ResponseEntity<>(new Data<>(ErrorVO
                    .builder()
                    .friendlyMessage("Column Name '%s' already taken".formatted(dto.getName()))
                    .status(400)
                    .build()));
        }

        column.setName(dto.getName());
        repository.create(column);

        return new ResponseEntity<>(new Data<>(column.getId()));
    }

    @Override
    public ResponseEntity<Data<String>> delete(@NonNull Long aLong) {
        repository.deleteByID(aLong);
        return new ResponseEntity<>(new Data<>("Deleted"));
    }

    @Override
    public ResponseEntity<Data<String>> update(@NonNull ColumnUpdateVO dto) {
        for (Column column : repository.findAll(new ColumnCriteria()).get()) {
            if (column.getId()==dto.getId()){
                column.setName(dto.getName());
                repository.update(column);
            }
        }
        return new ResponseEntity<>(new Data<>("Updated"));
    }

    @Override
    public ResponseEntity<Data<ColumnVO>> findById(@NonNull Long id) {

        Optional<Column> optionalColumn = repository.findById(id);

        ResponseEntity<Data<ColumnVO>> response;

        if (optionalColumn.isPresent()){
            response = new ResponseEntity<>(new Data<>(mapper.getColumnVo(optionalColumn.get())));
            return response;
        }
        ErrorVO errorVO = new ErrorVO("Column not found", "Column not found", 400);
        response = new ResponseEntity<>(new Data<>(errorVO));
        return response;

    }

    @Override
    public ResponseEntity<Data<List<ColumnVO>>> findAll(@NonNull ColumnCriteria criteria) {
        List<Column> columnList = repository.findAll(new ColumnCriteria()).get();

        List<ColumnVO> columnVOS = new ArrayList<>();

        for (Column column : columnList) {
            columnVOS.add(mapper.getColumnVo(column));
        }

        ResponseEntity<Data<List<ColumnVO>>> listResponseEntity = new ResponseEntity<>(new Data<>(columnVOS, columnVOS.size()));
        return listResponseEntity;
    }



    public ResponseEntity<Data<List<ColumnVO>>> getColumnsByIds(List<Long> ids) {

        List<ColumnVO> columns = new ArrayList<>();

        for (Long id : ids) {
            columns.add(findById(id).getData().getBody());
        }

        return new ResponseEntity<>(new Data<>(columns));
    }

    public static ColumnService getInstance() {
        if (instance == null) {
            instance = new ColumnService(
                    ApplicationContextHolder.getBean(ColumnRepository.class),
                    ApplicationContextHolder.getBean(ColumnMapper.class)
            );
        }
        return instance;
    }
}
