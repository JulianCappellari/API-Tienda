package tienda.tienda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
}
