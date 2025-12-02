package org.example.gelahsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Service.OrderGelahService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderGelahController {

    private final OrderGelahService orderGelahService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.status(200).body(orderGelahService.getAllOrders());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addNewOrder(@RequestBody @Valid OrderGelah orderGelah ){
        orderGelahService.addNewOrder(orderGelah);
        return ResponseEntity.status(200).body(new ApiResponse("The request has been accepted and will be presented to the owner of the gelah"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id , @RequestBody @Valid OrderGelah orderGelah , Errors errors){
        orderGelahService.updateOrder(id,orderGelah);
        return ResponseEntity.status(200).body(new ApiResponse("updated order successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id){
         orderGelahService.deleteOrder(id);
         return ResponseEntity.status(200).body(new ApiResponse("Cancel order successfully"));
    }
}
