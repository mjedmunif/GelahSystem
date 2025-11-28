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
    public ResponseEntity<?> addNewOrder(@RequestBody @Valid OrderGelah orderGelah , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        int checkStatus = orderGelahService.addNewOrder(orderGelah);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse(" Gelah with id : " + orderGelah.getGelahId() + " Not found"));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(404).body(new ApiResponse("clint with id : " + orderGelah.getClientId() + " Not found"));
        }
        if (checkStatus == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Gelah with id : " + orderGelah.getGelahId() + " Not available"));
        }
        if (checkStatus == 4){
            return ResponseEntity.status(400).body(new ApiResponse("This time is reserved"));
        }
        if (checkStatus == 5){
            return ResponseEntity.status(400).body(new ApiResponse("You have a previous order"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The request has been accepted and will be presented to the owner of the gelah"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id , @RequestBody @Valid OrderGelah orderGelah , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        int checkUpdateStatus = orderGelahService.updateOrder(id,orderGelah);
        if (checkUpdateStatus == 1){
            return ResponseEntity.status(400).body(new ApiResponse("order with id : "+orderGelah.getId() +" not found"));
        }
        if (checkUpdateStatus == 2){
            return ResponseEntity.status(404).body(new ApiResponse("clint with id : " + orderGelah.getClientId() + " Not found"));
        }
        if (checkUpdateStatus == 3){
            return ResponseEntity.status(404).body(new ApiResponse(" Gelah with id : " + orderGelah.getGelahId() + " Not found"));
        }
        if (checkUpdateStatus == 4){
            return ResponseEntity.status(400).body(new ApiResponse("Gelah with id : " + orderGelah.getGelahId() + " Not available"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("updated order successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id){
        Boolean deleted = orderGelahService.deleteOrder(id);
        if (deleted){
            return ResponseEntity.status(200).body(new ApiResponse("Cancel order successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("not found order"));
    }
}
