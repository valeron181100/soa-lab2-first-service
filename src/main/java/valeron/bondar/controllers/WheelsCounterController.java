package valeron.bondar.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.services.WheelsCounterService;

@RestController
@RequestMapping("/api/wheels_avg_count")
@AllArgsConstructor
public class WheelsCounterController {

    private final WheelsCounterService wheelsCounterService;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> doCount() {
        return new ResponseEntity<>("<avg>" + String.format("%.3f", this.wheelsCounterService.countAvgWheels()) + "</avg>", HttpStatus.OK);
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<String> handleException(ResponseException e) {
        return ResponseEntity.status(e.getHttpCode()).body(e.getMessage());
    }
}
