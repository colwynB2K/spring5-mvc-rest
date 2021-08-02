package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    @Schema(description = "The vendor name", required = true)
    private String name;

    @JsonProperty("vendor_url")
    @Schema(description = "Url to the vendor website?", required = true)
    private String vendorUrl;

}
