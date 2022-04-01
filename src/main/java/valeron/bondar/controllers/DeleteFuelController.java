package valeron.bondar.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.services.DeleteFuelService;

@RestController
@RequestMapping("/api/delete_by_fuel_type")
@AllArgsConstructor
public class DeleteFuelController {

    private final DeleteFuelService deleteFuelService;

    @DeleteMapping
    public void doDelete(@RequestParam(value = "q") String query) throws ResponseException {
        this.deleteFuelService.deleteByFuelType(query);
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<String> handleException(ResponseException e) {
        return ResponseEntity.status(e.getHttpCode()).body(e.getMessage());
    }
}
