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
import tienda.tienda.dto.PaymentDto;
import tienda.tienda.entities.Category;
import tienda.tienda.entities.Payment;
import tienda.tienda.services.payment.PaymentService;
import tienda.tienda.transformers.PaymentMapper;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment", description = "Operations related to payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;


    @Operation(summary = "Get all payments")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Payment found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Payment.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Payment not found", content = @Content)
    })
    @GetMapping
    public List<PaymentDto> getAllPayment(){
        List<Payment> payments = paymentService.getAll();
        return payments.stream().map(paymentMapper::toDto).toList();
    }


    @Operation(summary = "Get payment by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Payment found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Payment.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Payment not found with id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@Parameter(description = "Payment id", example = "1")@PathVariable Long id){
        Optional<Payment> payment = paymentService.getById(id);
        return payment.map(p -> ResponseEntity.ok(paymentMapper.toDto(p))).orElseGet(()-> ResponseEntity.notFound().build());

    }


    @Operation(summary = "Create payment in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Payment created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Payment.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto){
        Payment payment = paymentMapper.toEntity(paymentDto);
        Payment paymentSaved = paymentService.add(payment);
        return ResponseEntity.ok(paymentMapper.toDto(paymentSaved));
    }


    @Operation(summary = "Update payment in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Payment updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Payment.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto){
        Payment paymentToUpdate = paymentMapper.toEntity(paymentDto);
        Payment paymentUpdated = paymentService.update(paymentToUpdate, id);
        return paymentUpdated != null ? ResponseEntity.ok(paymentMapper.toDto(paymentUpdated))  : ResponseEntity.notFound().build();

    }


    @Operation(summary = "Delete payment in database by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Payment deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Payment.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> deletePayment(@Parameter(description = "Payment id", example = "1")@PathVariable Long id){
        if(paymentService.getById(id).isPresent()){
            paymentService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }
}
