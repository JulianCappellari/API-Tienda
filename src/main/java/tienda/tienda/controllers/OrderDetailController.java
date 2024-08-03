package tienda.tienda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tienda.dto.OrderDetailDto;
import tienda.tienda.entities.OrderDetail;
import tienda.tienda.services.orderDetail.OrderDetailService;
import tienda.tienda.transformers.OrderDetailMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailController {
    @Autowired
    public OrderDetailService orderDetailService;
    @Autowired
    public OrderDetailMapper orderDetailMapper;

    @GetMapping
    public List<OrderDetailDto> getAllOrderDetail(){
        List<OrderDetail> orderDetails = orderDetailService.getAll();
        return orderDetails.stream().map(orderDetailMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetailById(@PathVariable Long id){
        Optional<OrderDetail> orderDetail = orderDetailService.getById(id);
        return orderDetail.map(o -> ResponseEntity.ok(orderDetailMapper.toDto(o))).orElseGet(()-> ResponseEntity.notFound().build());

    }
    @PostMapping
    public ResponseEntity<OrderDetailDto> createOrderDetail(@RequestBody OrderDetailDto orderDetailDto){
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDto);
        OrderDetail orderDetailSaved = orderDetailService.add(orderDetail);
        return ResponseEntity.ok(orderDetailMapper.toDto(orderDetailSaved));

    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDto> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailDto orderDetailDto){
        OrderDetail orderDetailToUpdate = orderDetailMapper.toEntity(orderDetailDto);
        OrderDetail orderDetailUpdated = orderDetailService.update(orderDetailToUpdate, id);
        return orderDetailUpdated != null ? ResponseEntity.ok(orderDetailMapper.toDto(orderDetailUpdated)) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDetailDto> deleteOrderDetail(@PathVariable Long id){
        if(orderDetailService.getById(id).isPresent()){
            orderDetailService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }
}
