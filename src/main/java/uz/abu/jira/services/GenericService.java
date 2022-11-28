package uz.muhammad.jira.services;

import lombok.NonNull;
import uz.muhammad.jira.criteria.GenericCriteria;
import uz.muhammad.jira.vo.GenericVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author "Elmurodov Javohir"
 * @since 14/06/22/15:07 (Tuesday)
 * jira/IntelliJ IDEA
 */
public interface GenericService<
        Vo extends GenericVO,
        C extends GenericCriteria,
        ID extends Serializable> {

    ResponseEntity<Data<Vo>> findById(@NonNull ID id);

    ResponseEntity<Data<List<Vo>>> findAll(@NonNull C criteria);
}
