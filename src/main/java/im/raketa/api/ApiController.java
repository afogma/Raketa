package im.raketa.api;

import im.raketa.db.Value;
import im.raketa.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;


@RestController
@RequestMapping("/api/values")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ApiController {

    private final ApiService valuesService;

    @PostMapping()
    public List<Value> createValues(@RequestParam("number") int number) {
       return valuesService.valuesCreation(number);
    }

    @DeleteMapping
    public ResponseEntity deleteValues() {
        valuesService.eraseDataBase();
        return ResponseEntity.ok("all values removed from database");
    }

    @PutMapping("/:{id}")
    public Value updateValue(@RequestBody ValueDTO json, @PathVariable("id") long id) {
        Value value = valuesService.updateValue(id, json);
        return value;
    }

    @GetMapping()
    public List<Value> findValuesWithFilterAndOrdering(HttpServletRequest request) {
        Map<String, String> queries = getStringStringMap(request.getQueryString());
        return valuesService.findWithFilter(queries);
    }

    private Map<String, String> getStringStringMap(String query) {
        if (query == null) {
            return Collections.emptyMap();
        }

        String[] queryParams = query.split("&");
        Map<String, String> queries = new HashMap<>();

        for (String queryParam : queryParams) {
            String[] operation = queryParam.split("=");
            queries.put(operation[0], URLDecoder.decode(operation[1]));
        }
        return queries;
    }
}
