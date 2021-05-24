package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    // Customize a model field description
    @Schema(description = "The customer's first name", required = true)
    private String firstname;
    // Customize a model field description
    @Schema(description = "The customer's last name", defaultValue = "Biggs", required = true)
    private String lastname;

    @JsonProperty("customer_url")
    private String customerUrl;
}
