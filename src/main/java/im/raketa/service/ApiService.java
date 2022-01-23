package im.raketa.service;

import im.raketa.db.Value;
import im.raketa.api.ValueDTO;
import im.raketa.db.ValuesRepo;
import im.raketa.model.constants.Field;
import im.raketa.model.constants.OrderDirection;
import im.raketa.model.QueryFilter;
import im.raketa.model.constants.SearchOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    private final ValuesRepo valuesRepo;

    public List<Value> valuesCreation(int number) {
        List<Value> newValues = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            Value val = new Value(0L, Instant.now(), "Запись " + i);
            newValues.add(val);
        }
        valuesRepo.saveAll(newValues);
        logger.info("{} values were added", number);
        return newValues;
    }

    public void eraseDataBase() {
        valuesRepo.deleteAll();
        logger.info("All values were removed from Database");
    }

    public Value updateValue(long id, ValueDTO json) {
        if (!valuesRepo.existsById(id)) throw new RuntimeException();
        Value val = valuesRepo.findValueById(id);
        String value = json.getValue();
        val.setDate(Instant.now());
        val.setValue(value);
        valuesRepo.save(val);
        logger.info("{} was updated", val);
        return val;
    }

    public List<Value> findWithFilter(Map<String, String> queries) {
        List<QueryFilter> queryFilters = new ArrayList<>();

        for (Map.Entry<String, String> query : queries.entrySet()) {
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
