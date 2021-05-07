package guru.springfamework.service;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.mapper.VendorMapper;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exception.ResourceNotFoundException;
import guru.springfamework.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    public static final String NAME_1 = "My Vendor";
    public static final long ID_1 = 1L;
    public static final String NAME_2 = "My Vendor";
    public static final long ID_2 = 1L;

    @Mock
    private VendorMapper vendorMapper;

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorServiceImpl vendorServiceImpl;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void findById() {
        //given
        Vendor vendor = getVendor1();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(vendor.getName());

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorMapper.toDTO(any(Vendor.class))).thenReturn(vendorDTO);

        //when
        VendorDTO actualVendorDTO = vendorServiceImpl.findById(1L);

        //then
        verify(vendorRepository).findById(anyLong());

        assertEquals(NAME_1, actualVendorDTO.getName());
    }

    @Test
    void findByIdNotFound() {
        //given
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        assertThrows(ResourceNotFoundException.class, () -> vendorServiceImpl.findById(1L));

        //then
        verify(vendorRepository).findById(anyLong());
    }

    @Test
    void findAll() {
        //given
        List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());
        when(vendorRepository.findAll()).thenReturn(vendors);
        when(vendorMapper.toDTO(any(Vendor.class))).thenReturn(new VendorDTO());

        //when
        List<VendorDTO> vendorDTOs = vendorServiceImpl.findAll();


        //then
        verify(vendorRepository).findAll();
        assertEquals(2, vendorDTOs.size());
    }

    @Test
    void create() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        when(vendorMapper.toEntity(any(VendorDTO.class))).thenReturn(vendor);
        when(vendorMapper.toDTO(any(Vendor.class))).thenReturn(vendorDTO);

        //when
        VendorDTO savedVendorDTO = vendorServiceImpl.create(vendorDTO);

        //then
        verify(vendorRepository).save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    @Test
    void update() {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        when(vendorMapper.toEntity(any(VendorDTO.class))).thenReturn(vendor);
        when(vendorMapper.toDTO(any(Vendor.class))).thenReturn(vendorDTO);

        //when
        VendorDTO savedVendorDTO = vendorServiceImpl.update(ID_1, vendorDTO);

        //then
        // 'should' defaults to times = 1
        verify(vendorRepository).save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    @Test
    void patch() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        when(vendorMapper.toEntity(any(VendorDTO.class))).thenReturn(vendor);
        when(vendorMapper.toDTO(any(Vendor.class))).thenReturn(vendorDTO);

        //when

        VendorDTO savedVendorDTO = vendorServiceImpl.patch(ID_1, vendorDTO);

        //then
        verify(vendorRepository).save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    @Test
    void deleteVendorById() {

        //when
        vendorServiceImpl.deleteById(1L);

        //then
        verify(vendorRepository).deleteById(anyLong());
    }

    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_1);
        vendor.setId(ID_1);
        return vendor;
    }

    private Vendor getVendor2() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_2);
        vendor.setId(ID_2);
        return vendor;
    }
}
