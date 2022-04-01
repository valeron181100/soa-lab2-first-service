package valeron.bondar.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valeron.bondar.database.VehicleRepo;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.Vehicles;
import valeron.bondar.services.VehicleService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/vehicles")
@AllArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleRepo vehicleRepo;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Vehicles> getVehicles(@RequestParam(value = "from_index", required = false, defaultValue = "0") String startIndex,
                                                @RequestParam(value = "max_results", required = false) String maxResults,
                                                @RequestParam(value = "sort_by", required = false, defaultValue = "CREATION_DATE") String sortField,
                                                @RequestParam(value = "order_desc", required = false) String orderStr,
                                                @RequestParam(value = "filters", required = false) String filtersParam) throws UnsupportedEncodingException, ResponseException {
        Vehicles vehicles = this.vehicleService.getVehicles(startIndex, maxResults, sortField, orderStr, filtersParam);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Vehicle> postVehicle(@RequestBody Vehicle vehicle) throws ResponseException {
        vehicleService.createVehicle(vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @GetMapping("/generate/data")
    public ResponseEntity generateTestData() {
        this.vehicleService.generateTestData();
        return ResponseEntity.ok().body("Test data generated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable(value = "id") Integer id) throws ResponseException {
        return new ResponseEntity<>(this.vehicleService.getVehicleById(id), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable(value = "id") int id,@RequestBody Vehicle vehicle) throws ResponseException {
        Vehicle updateVehicle = this.vehicleService.updateVehicle(id, vehicle);
        return new ResponseEntity<>(updateVehicle, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Vehicle> deleteVehicle(@PathVariable(value = "id") int id) throws ResponseException {
        Vehicle removeVehicle = this.vehicleService.removeVehicle(id);
        return new ResponseEntity<>(removeVehicle, HttpStatus.OK);
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity handleException(ResponseException e) {
        return ResponseEntity.status(e.getHttpCode()).body(e.getMessage());
    }
}
