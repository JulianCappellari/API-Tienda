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
import tienda.tienda.dto.OrderDetailDto;
import tienda.tienda.entities.Category;
import tienda.tienda.entities.OrderDetail;
import tienda.tienda.services.orderDetail.OrderDetailService;
import tienda.tienda.transformers.OrderDetailMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orderdetails")
@Tag(name = "Order detail", description = "Operations related to order details")
public class OrderDetailController {
    @Autowired
    public OrderDetailService orderDetailService;
    @Autowired
    public OrderDetailMapper orderDetailMapper;


    @Operation(summary = "Get all order details")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order details found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDetail.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Order detail not found", content = @Content)
    })
    @GetMapping
    public List<OrderDetailDto> getAllOrderDetail(){
        List<OrderDetail> orderDetails = orderDetailService.getAll();
        return orderDetails.stream().map(orderDetailMapper::toDto).toList();
    }


    @Operation(summary = "Get order detail by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order detail found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDetail.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Order detail not found with id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetailById(@Parameter(description = "Order detail id", example = "1")@PathVariable Long id){
        Optional<OrderDetail> orderDetail = orderDetailService.getById(id);
        return orderDetail.map(o -> ResponseEntity.ok(orderDetailMapper.toDto(o))).orElseGet(()-> ResponseEntity.notFound().build());

    }


    @Operation(summary = "Create order detail in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order detail created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDetail.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<OrderDetailDto> createOrderDetail(@RequestBody OrderDetailDto orderDetailDto){
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDto);
        OrderDetail orderDetailSaved = orderDetailService.add(orderDetail);
        return ResponseEntity.ok(orderDetailMapper.toDto(orderDetailSaved));

    }


    @Operation(summary = "Update order detail in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order detail updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDetail.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDto> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailDto orderDetailDto){
        OrderDetail orderDetailToUpdate = orderDetailMapper.toEntity(orderDetailDto);
        OrderDetail orderDetailUpdated = orderDetailService.update(orderDetailToUpdate, id);
        return orderDetailUpdated != null ? ResponseEntity.ok(orderDetailMapper.toDto(orderDetailUpdated)) : ResponseEntity.noContent().build();
    }


    @Operation(summary = "Delete order detail in database by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Order detail deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDetail.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDetailDto> deleteOrderDetail(@Parameter(description = "Order detail id", example = "1")@PathVariable Long id){
        if(orderDetailService.getById(id).isPresent()){
            orderDetailService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }
}
