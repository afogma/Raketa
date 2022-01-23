package im.raketa.model;

import lombok.Data;

@Data
public class QueryFilter {

    private String value;
    private Field field;
    private SearchOperation operation;
    private OrderDirection order;
}
