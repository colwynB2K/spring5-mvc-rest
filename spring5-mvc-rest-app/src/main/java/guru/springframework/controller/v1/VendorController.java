package guru.springframework.controller.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.service.VendorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Vendor Controller", description = "This is the Vendor controller")
@RequestMapping(VendorController.URI)
public class VendorController {

    public static final String URI = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @Operation(summary = "Get list of all vendors", description = "This actually returns a list of VendorDTO objects.")
    public VendorListDTO getVendorList(){
        return new VendorListDTO(vendorService.findAll());
    }

    @GetMapping({"/{id}"})
    @Operation(summary = "Get a specific vendor by id", description = "Returns a VendorDTO")
    public VendorDTO getVendorById(@PathVariable Long id){
        return vendorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Vendor", description = "Returns HTTP status Created (201)")
    public VendorDTO create(@RequestBody VendorDTO vendorDTO){
        return vendorService.create(vendorDTO);
    }

    @PutMapping({"/{id}"})
    @Operation(summary = "Update all fields of a Vendor", description = "Returns updated VendorDTO")
    public VendorDTO update(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.update(id, vendorDTO);
    }

    @PatchMapping({"/{id}"})
    @Operation(summary = "Update only submitted fields of a Vendor", description = "Returns updated VendorDTO")
    public VendorDTO patch(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.patch(id, vendorDTO);
    }

    @DeleteMapping({"/{id}"})
    @Operation(summary = "Delete a Vendor matching the specified id", description = "Returns absolutely nothing!")
    public void deleteVendor(@PathVariable Long id){
        vendorService.deleteById(id);
    }
}
