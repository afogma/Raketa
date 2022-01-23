package im.raketa.db;

import im.raketa.model.QueryFilter;
import im.raketa.db.ValueSpecification;
import im.raketa.db.Value;
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