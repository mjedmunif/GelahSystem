package org.example.gelahsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.GelahOwner;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Repository.GelahRepository;
import org.example.gelahsystem.Service.GelahOwnerService;
import org.example.gelahsystem.Service.GelahService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        gelahOwnerService.addNewOwnerGelah(gelahOwner);
        return ResponseEntity.status(200).body(new ApiResponse("Welcome "+ gelahOwner.getFirstName() +"! Added your account Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Integer id , @RequestBody @Valid GelahOwner gelahOwner){
        gelahOwnerService.updateInfoOwnerGelah(id, gelahOwner);
            return ResponseEntity.status(200).body(new ApiResponse("updated your information successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Integer id){
         gelahOwnerService.deleteOwnerGelah(id);
            return ResponseEntity.status(200).body(new ApiResponse("deleted account successfully"));
    }

    @GetMapping("applyOrder/{clientId}/{gelahId}")
    public ResponseEntity<?> applyOrder(@PathVariable Integer clientId , @PathVariable Integer gelahId){
       gelahOwnerService.applyOrder(clientId, gelahId);
        return ResponseEntity.status(200).body(new ApiResponse("Applied successfully"));
    }

    @GetMapping("rejectOrder/{clientId}/{gelahId}")
    public ResponseEntity<?> rejectOrder(@PathVariable Integer clientId , @PathVariable Integer gelahId){
        gelahOwnerService.rejectOrder(clientId, gelahId);
        return ResponseEntity.status(200).body(new ApiResponse("rejected successfully"));
    }

    @GetMapping("/getGelahStatusPending/{ownerId}")
    public ResponseEntity<?> getGelahByStatus(@PathVariable Integer ownerId){
        return ResponseEntity.status(200).body(gelahOwnerService.getGelahByStatus(ownerId));
    }
}
