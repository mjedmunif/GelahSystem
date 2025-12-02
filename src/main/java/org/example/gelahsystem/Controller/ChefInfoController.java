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
    public ResponseEntity<?> addNewChef(@RequestBody @Valid ChefInfo chefInfo){
        chefInfoService.addChefeInfo(chefInfo);
        return ResponseEntity.status(200).body(new ApiResponse("Added chef Info successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChefInfo(@PathVariable Integer id , @RequestBody @Valid ChefInfo chefInfo){
        chefInfoService.updateChefInfo(id, chefInfo);
        return ResponseEntity.status(200).body(new ApiResponse("Updated Chef Info successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChefInfo(@PathVariable Integer id){
        chefInfoService.deleteChefInfo(id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted Chef successfully"));
    }
}
