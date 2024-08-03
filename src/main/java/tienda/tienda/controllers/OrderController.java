package tienda.tienda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tienda.dto.OrderDto;
import tienda.tienda.entities.Order;
import tienda.tienda.services.order.OrderService;
import tienda.tienda.transformers.OrderMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public List<OrderDto> getAllOrder(){
        List<Order> orders = orderService.getAll();
        return orders.stream().map(orderMapper::toDto).toList();

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getById(id);
        return  order.map(o -> ResponseEntity.ok(orderMapper.toDto(o))).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        Order order = orderMapper.toEntity(orderDto);
        Order orderSaved = orderService.add(order);
        return ResponseEntity.ok(orderMapper.toDto(orderSaved));

    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto){
        Order orderToUpdate = orderMapper.toEntity(orderDto);
        Order orderUpdate = orderService.update(orderToUpdate, id);
        return orderUpdate != null ? ResponseEntity.ok(orderMapper.toDto(orderUpdate)) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable Long id){
        if(orderService.getById(id).isPresent()){
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }
}
