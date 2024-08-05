package tienda.tienda.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tienda.dto.OrderDto;
import tienda.tienda.entities.Category;
import tienda.tienda.entities.Order;
import tienda.tienda.services.order.OrderService;
import tienda.tienda.transformers.OrderMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Operations related to orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;


    @Operation(summary = "Get all orders")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Orders found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Order not found", content = @Content)
    })
    @GetMapping
    public List<OrderDto> getAllOrder(){
        List<Order> orders = orderService.getAll();
        return orders.stream().map(orderMapper::toDto).toList();

    }

    @Operation(summary = "Get order by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Order not found with id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@Parameter(description = "Order id", example = "1")@PathVariable Long id) {
        Optional<Order> order = orderService.getById(id);
        return  order.map(o -> ResponseEntity.ok(orderMapper.toDto(o))).orElseGet(()-> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Create order in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        Order order = orderMapper.toEntity(orderDto);
        Order orderSaved = orderService.add(order);
        return ResponseEntity.ok(orderMapper.toDto(orderSaved));

    }


    @Operation(summary = "Update order in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto){
        Order orderToUpdate = orderMapper.toEntity(orderDto);
        Order orderUpdate = orderService.update(orderToUpdate, id);
        return orderUpdate != null ? ResponseEntity.ok(orderMapper.toDto(orderUpdate)) : ResponseEntity.notFound().build();

    }


    @Operation(summary = "Delete order in database by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDto> deleteOrder(@Parameter(description = "Order id", example = "1")@PathVariable Long id){
        if(orderService.getById(id).isPresent()){
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }
}
