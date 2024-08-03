package tienda.tienda.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private Integer number;
}
