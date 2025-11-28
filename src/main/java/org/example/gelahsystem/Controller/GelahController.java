package org.example.gelahsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Service.GelahService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gelah")
@RequiredArgsConstructor
public class GelahController {

    private final GelahService gelahService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllGelah(){
        return ResponseEntity.status(200).body(gelahService.getAllGelah());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewGelah(@RequestBody @Valid Gelah gelah , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        boolean checked = gelahService.addNewGelah(gelah);
        if (checked){
            return ResponseEntity.status(200).body(new ApiResponse("Added Gelah successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("not found owner with id : " + gelah.getOwnerId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGelah(@PathVariable Integer id , @RequestBody @Valid Gelah gelah , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        int checkStatus = gelahService.updateGelah(id, gelah);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("not found owner with id : " + gelah.getOwnerId()));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(404).body(new ApiResponse("not found gelah with id : " + id));
        }
        return ResponseEntity.status(200).body(new ApiResponse("updated gelah successfully"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGelah(@PathVariable Integer id){
        int checkStatus = gelahService.deleteGelah(id);
        if (checkStatus == 1){
        return ResponseEntity.status(404).body(new ApiResponse("not found Gelah with id : " + id));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Cannot delete an unavailable Gelah"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("deleted Gelah Successfully"));
    }
}
