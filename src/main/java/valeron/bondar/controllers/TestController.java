package valeron.bondar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${val.test.property}")
    private String testProp;

    @GetMapping("/api/hello")
    public ResponseEntity test() {
        return ResponseEntity.ok(testProp);
    }
}
