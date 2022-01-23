package im.raketa.model;

import im.raketa.model.constants.Field;
import im.raketa.model.constants.OrderDirection;
import im.raketa.model.constants.SearchOperation;
import lombok.Data;

@Data
public class QueryFilter {

    private String value;
    private Field field;
    private SearchOperation operation;
    private OrderDirection order;
}
