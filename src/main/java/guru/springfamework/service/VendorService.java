package guru.springfamework.service;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;

import java.util.List;

public interface VendorService {
    VendorDTO findById(Long id);

    List<VendorDTO> findAll();

    VendorDTO create(VendorDTO vendorDTO);

    VendorDTO update(Long id, VendorDTO vendorDTO);

    VendorDTO patch(Long id, VendorDTO vendorDTO);

    void deleteById(Long id);
}
