package im.raketa.service;

import im.raketa.entity.Value;
import im.raketa.dto.ValueDTO;
import im.raketa.repo.ValuesRepo;
import im.raketa.utility.constants.Field;
import im.raketa.utility.constants.OrderDirection;
import im.raketa.utility.QueryFilter;
import im.raketa.utility.constants.SearchOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ValuesService {

    private final ValuesRepo valuesRepo;

    public List<Value> findAllValues() {
        return valuesRepo.findAll();
    }

    public void valuesCreation(int number) {
        for (int i = 1; i <= number; i++) {
            Value val = new Value(0L, Instant.now(), "Запись " + i);
            valuesRepo.save(val);
        }
    }

    public void eraseDataBase() {
        valuesRepo.deleteAll();
    }

    public Value updateValue(long id, ValueDTO json) {
        Value val = valuesRepo.findValueById(id);
        String value = json.getValue();
        val.setDate(Instant.now());
        val.setValue(value);
        valuesRepo.save(val);
        return val;
    }

    public List<Value> findWithFilter(Map<String, String> querys) {
        List<QueryFilter> queryFilters = new ArrayList<>();

        for (Map.Entry<String, String> query : querys.entrySet()) {
            QueryFilter filter = parseFilter(query.getKey(), query.getValue());
            queryFilters.add(filter);
        }

        return valuesRepo.findAllWithFilter(queryFilters);
    }

    private QueryFilter parseFilter(String query, String value) {

        String[] key = query.split("\\.");

        QueryFilter filter = new QueryFilter();
        if (key[0].equalsIgnoreCase("order")) {
            filter.setOrder(OrderDirection.valueOf(value));
        } else if (key[0].equalsIgnoreCase("filter")) {
            filter.setValue(value);
            filter.setOperation(SearchOperation.valueOf(key[2]));
        }
        filter.setField(Field.valueOf(key[1].toUpperCase()));
        return filter;
    }
}
