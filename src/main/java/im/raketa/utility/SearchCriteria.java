package im.raketa.utility;

import im.raketa.utility.constants.SearchOperation;
import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;
}