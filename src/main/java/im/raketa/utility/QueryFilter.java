package im.raketa.utility;

import im.raketa.utility.constants.Field;
import im.raketa.utility.constants.OrderDirection;
import im.raketa.utility.constants.SearchOperation;
import lombok.Data;

@Data
public class QueryFilter {

    private String value;
    private Field field;
    private SearchOperation operation;
    private OrderDirection order;
}
