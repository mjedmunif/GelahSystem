package org.example.gelahsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Service.GelahService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addNewGelah(@RequestBody @Valid Gelah gelah){
        gelahService.addNewGelah(gelah);
        return ResponseEntity.status(200).body(new ApiResponse("added gelah successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGelah(@PathVariable Integer id , @RequestBody @Valid Gelah gelah ){
        gelahService.updateGelah(id, gelah);
        return ResponseEntity.status(200).body(new ApiResponse("updated gelah successfully"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGelah(@PathVariable Integer id){
        gelahService.deleteGelah(id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted Gelah Successfully"));
    }
}
