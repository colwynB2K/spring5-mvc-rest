package guru.springfamework.service;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.mapper.VendorMapper;
import guru.springfamework.domain.Vendor;
import guru.springfamework.controller.v1.VendorController;

import guru.springfamework.exception.ResourceNotFoundException;
import guru.springfamework.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorDTO findById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        VendorDTO vendorDTO = vendorMapper.toDTO(vendor);
        vendorDTO.setVendorUrl(VendorController.URI + "/" + id);

        return vendorDTO;
    }

    @Override
    public List<VendorDTO> findAll() {

        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.toDTO(vendor);
                    vendorDTO.setVendorUrl(VendorController.URI + "/" + vendor.getId());

                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO create(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.toEntity(vendorDTO));
    }

    @Override
    public VendorDTO update(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.toEntity(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patch(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.toEntity(vendorDTO);

        if (vendorDTO.getName() != null) {
            vendor.setName(vendorDTO.getName());
        }

        return saveAndReturnDTO(vendor);
    }

    @Override
    public void deleteById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnDto = vendorMapper.toDTO(savedVendor);

        returnDto.setVendorUrl(VendorController.URI + "/" + savedVendor.getId());

        return returnDto;
    }
}

