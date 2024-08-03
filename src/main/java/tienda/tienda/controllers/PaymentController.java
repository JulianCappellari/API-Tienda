package tienda.tienda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tienda.dto.PaymentDto;
import tienda.tienda.entities.Payment;
import tienda.tienda.services.payment.PaymentService;
import tienda.tienda.transformers.PaymentMapper;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;

    @GetMapping
    public List<PaymentDto> getAllPayment(){
        List<Payment> payments = paymentService.getAll();
        return payments.stream().map(paymentMapper::toDto).toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id){
        Optional<Payment> payment = paymentService.getById(id);
        return payment.map(p -> ResponseEntity.ok(paymentMapper.toDto(p))).orElseGet(()-> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto){
        Payment payment = paymentMapper.toEntity(paymentDto);
        Payment paymentSaved = paymentService.add(payment);
        return ResponseEntity.ok(paymentMapper.toDto(paymentSaved));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto){
        Payment paymentToUpdate = paymentMapper.toEntity(paymentDto);
        Payment paymentUpdated = paymentService.update(paymentToUpdate, id);
        return paymentUpdated != null ? ResponseEntity.ok(paymentMapper.toDto(paymentUpdated))  : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> deletePayment(@PathVariable Long id){
        if(paymentService.getById(id).isPresent()){
            paymentService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }
}
