package valeron.bondar.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.Vehicles;
import valeron.bondar.services.SearchNameService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/search_by_name")
@AllArgsConstructor
public class SearchNameController {

    private final SearchNameService searchNameService;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Vehicles> doSearch(@RequestParam(value = "q") String query,
                                             @RequestParam(value = "from_index", required = false, defaultValue = "0") String startIndex,
                                             @RequestParam(value = "max_results") String maxResults,
                                             @RequestParam(value = "sort_by", required = false, defaultValue = "CREATION_DATE") String sortField,
                                             @RequestParam(value = "order_desc", required = false) String orderStr) throws UnsupportedEncodingException, ResponseException {
        return new ResponseEntity<>(this.searchNameService.searchByName(query, startIndex, maxResults, sortField, orderStr), HttpStatus.OK);
    }

}
