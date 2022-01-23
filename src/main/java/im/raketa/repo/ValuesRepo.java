package im.raketa.repo;

import im.raketa.utility.QueryFilter;
import im.raketa.utility.ValueSpecification;
import im.raketa.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValuesRepo extends JpaRepository<Value, Long>, JpaSpecificationExecutor<Value> {

    Value findValueById(long id);

    default List<Value> findAllWithFilter(List<QueryFilter> queryFilters) {
        ValueSpecification spec = new ValueSpecification(queryFilters);
        return findAll(spec);
    }
}