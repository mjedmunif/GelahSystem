package org.example.gelahsystem.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.ChefInfo;
import org.example.gelahsystem.Service.ChefInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/chef")
public class ChefInfoController {

    private final ChefInfoService chefInfoService;

    @GetMapping("/get")
    public ResponseEntity<?> getChefs(){
        return ResponseEntity.status(200).body(chefInfoService.getChefs());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewChef(@RequestBody @Valid ChefInfo chefInfo , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        int checkStatus = chefInfoService.addChefeInfo(chefInfo);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("Geleh with id : " + chefInfo.getGelahId() + " Not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Added chef Info successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChefInfo(@PathVariable Integer id , @RequestBody @Valid ChefInfo chefInfo , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        int checkStatus = chefInfoService.updateChefInfo(id, chefInfo);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("chef with id : " + chefInfo.getId() + " Not found"));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(404).body(new ApiResponse("Gelah with id : " + chefInfo.getGelahId() + " Not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Updated Chef Info successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChefInfo(@PathVariable Integer id){
        int checkStatus = chefInfoService.deleteChefInfo(id);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("Chef With id : " + id + " Not found"));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Sorry, Currently You have a order , come after that!"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("deleted Chef successfully"));
    }
}
