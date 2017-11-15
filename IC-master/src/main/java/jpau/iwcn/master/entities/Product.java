package jpau.iwcn.master.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "products")
public class Product {
	
	@Id
	@NotNull
	@NotEmpty
	@Size(min = 5, max = 20, message = "the code's attributte's size must be between these values")
	@ApiModelProperty(notes = "A product code")
	private String code;

	@NotNull
	@NotEmpty
	@ApiModelProperty(notes = "A product name")
    private String name;

	@NotNull
	@NotEmpty
	@ApiModelProperty(notes = "A product description")
    private String description;

	@NotNull
	@ApiModelProperty(notes = "A product price")
    private double price;

}
