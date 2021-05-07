package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.service.VendorService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.URI)
public class VendorController {

    public static final String URI = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public VendorListDTO getVendorList(){
        return new VendorListDTO(vendorService.findAll());
    }

    @GetMapping({"/{id}"})
    public VendorDTO getVendorById(@PathVariable Long id){
        return vendorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO create(@RequestBody VendorDTO vendorDTO){
        return vendorService.create(vendorDTO);
    }

    @PutMapping({"/{id}"})
    public VendorDTO update(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.update(id, vendorDTO);
    }

    @PatchMapping({"/{id}"})
    public VendorDTO patch(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.patch(id, vendorDTO);
    }

    @DeleteMapping({"/{id}"})
    public void deleteVendor(@PathVariable Long id){
        vendorService.deleteById(id);
    }
}
