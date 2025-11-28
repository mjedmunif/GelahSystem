package org.example.gelahsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.GelahOwner;
import org.example.gelahsystem.Service.GelahOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gelahOwner")
@RequiredArgsConstructor
public class GelahOwnerController {


    private final GelahOwnerService gelahOwnerService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllOwners(){
        return ResponseEntity.status(200).body(gelahOwnerService.getAllGelah());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewOwner(@RequestBody @Valid GelahOwner gelahOwner , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        gelahOwnerService.addNewOwnerGelah(gelahOwner);
        return ResponseEntity.status(200).body(new ApiResponse("Welcome "+ gelahOwner.getFirstName() +"! Added your account Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Integer id , @RequestBody @Valid GelahOwner gelahOwner , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(200).body(new ApiResponse(message));
        }
        Boolean update = gelahOwnerService.updateInfoOwnerGelah(id, gelahOwner);
        if (update){
            return ResponseEntity.status(200).body(new ApiResponse("updated your information successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("not found owner with ID : " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Integer id){
        Boolean deleted = gelahOwnerService.deleteOwnerGelah(id);
        if (deleted){
            return ResponseEntity.status(200).body(new ApiResponse("deleted account successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("not found owner with iD :" + id));
    }

    @GetMapping("applyOrder/{clientId}/{gelahId}")
    public ResponseEntity<?> applyOrder(@PathVariable Integer clientId , @PathVariable Integer gelahId){
        int checkStatus = gelahOwnerService.applyOrder(clientId, gelahId);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("Gelah with id ' " + gelahId + " ' Not found"));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(404).body(new ApiResponse("Client with id ' " + clientId + " ' Not found"));
        }
        if (checkStatus == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Order Not found"));
        }
        if (checkStatus == 4){
            return ResponseEntity.status(200).body(new ApiResponse("this order accepted"));
        }
        if (checkStatus == 5){
            return ResponseEntity.status(400).body(new ApiResponse("the order canceled by client"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Applied successfully"));
    }

    @GetMapping("rejectOrder/{clientId}/{gelahId}")
    public ResponseEntity<?> rejectOrder(@PathVariable Integer clientId , @PathVariable Integer gelahId){
        int checkStatus = gelahOwnerService.rejectOrder(clientId, gelahId);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("Gelah with id ' " + gelahId + " ' Not found"));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(404).body(new ApiResponse("Client with id ' " + clientId + " ' Not found"));
        }
        if (checkStatus == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Order Not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("rejected successfully"));
    }
}
