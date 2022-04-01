package valeron.bondar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class TestController {

    @Autowired
    private CoordinatesRepository repository;

    @GetMapping
    public String test() {
        return repository.findAll().toString();
    }
}
