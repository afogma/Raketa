package im.raketa.db;

import im.raketa.model.QueryFilter;
import im.raketa.model.SearchCriteria;
import im.raketa.model.OrderDirection;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static im.raketa.model.Field.VALUE;
import static im.raketa.model.SearchOperation.*;

@Data
public class ValueSpecification implements Specification<Value> {

    private final List<QueryFilter> queryFilters;

    private List<SearchCriteria> list;

    @Override
    public Predicate toPredicate(Root<Value> root, CriteriaQuery<?> query, CriteriaBuilder criteria) {
        List<Predicate> predicates = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        for (QueryFilter queryFilter : queryFilters) {

            if (queryFilter.getOrder() != null) {
                orders.add(toOrder(queryFilter, criteria, root));
            } else predicates.add(createPredicate(queryFilter, criteria, root));
        }
        query.orderBy(orders);

        return criteria.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate createPredicate(QueryFilter queryFilter, CriteriaBuilder criteria, Root<Value> root) {
        // TODO add validation and error handling for incoming data

        if (queryFilter.getOperation() == eq) {
            return criteria.equal(root.get(queryFilter.getField().name().toLowerCase()), queryFilter.getValue());
        }

        if (queryFilter.getOperation() == ctn && queryFilter.getField() == VALUE) {
            return criteria.like(root.get(VALUE.name().toLowerCase()), "%" + queryFilter.getValue() + "%");
        }

        if (queryFilter.getOperation() == gt) {
            return criteria.greaterThan(root.get(queryFilter.getField().name().toLowerCase()), queryFilter.getValue());
        }

        if (queryFilter.getOperation() == lt) {
            return criteria.lessThan(root.get(queryFilter.getField().name().toLowerCase()), queryFilter.getValue());
        }

        if (queryFilter.getOperation() == in) {
            String[] values = queryFilter.getValue().split(",");
            return criteria.in(root.get(queryFilter.getField().name().toLowerCase())).in(Arrays.asList(values));
        }
        throw new RuntimeException("Invalid filter");
    }

    private Order toOrder(QueryFilter queryFilter, CriteriaBuilder criteria, Root<Value> root) {
        if (queryFilter.getOrder() == OrderDirection.asc) {
            return criteria.asc(root.get(queryFilter.getField().name().toLowerCase()));
        } else return criteria.desc(root.get(queryFilter.getField().name().toLowerCase()));
    }
}