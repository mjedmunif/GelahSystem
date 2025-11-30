package org.example.gelahsystem.Controller;


import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.Client;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Repository.GelahRepository;
import org.example.gelahsystem.Repository.OrderGelahRepository;
import org.example.gelahsystem.Service.ClientService;
import org.hibernate.query.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final OrderGelahRepository orderGelahRepository;
    private final GelahRepository gelahRepository;

    @GetMapping("/get")
    public ResponseEntity<?> getAllClient(){
        return ResponseEntity.status(200).body(clientService.getAllClient());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewClient(@RequestBody @Valid Client client , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        clientService.addNewClient(client);
        return ResponseEntity.status(200).body(new ApiResponse("added Client successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Integer id , @RequestBody @Valid Client client , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        boolean checkedStatus = clientService.updateClient(id, client);
        if (checkedStatus){
            return ResponseEntity.status(200).body(new ApiResponse("updated client successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("client not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer id){
        boolean checkedStatus= clientService.deleteClient(id);
        if (checkedStatus){
            return ResponseEntity.status(200).body(new ApiResponse("deleted client successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("client not found"));
    }

    @GetMapping("/getReservedTime/{gelahId}")
    public ResponseEntity<?> getReservedTimes(@PathVariable Integer gelahId ){
        List<Object[]> checkStatus = clientService.getReservedTimes(gelahId);
        if (checkStatus == null){
            return ResponseEntity.status(404).body(new ApiResponse("Gelah with id '" + gelahId +"' not found"));
        }
        if (checkStatus.isEmpty()){
            return ResponseEntity.status(200).body(new ApiResponse("All times are available"));
        }
        return ResponseEntity.status(200).body(clientService.getReservedTimes(gelahId));
    }

    @GetMapping("/cancelOrder/{clientId}/{gelahId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer clientId , @PathVariable Integer gelahId){
        int checkStatus = clientService.cancelOrder(clientId, gelahId);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("order not found"));
        }
        if (checkStatus == 2 ){
            return ResponseEntity.status(400).body(new ApiResponse("sorry! the order is accepted be owner"));
        }
        if (checkStatus == 3){
            return ResponseEntity.status(400).body(new ApiResponse("The request was rejected by the owner"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("canceled successfully"));
    }

    @GetMapping("/getLowestToHighest")
    public ResponseEntity<?> getGelahLowestToHighest(){
        return ResponseEntity.status(200).body(clientService.getGelahbyPriceLowestToHighest());
    }

    @GetMapping("/getGelahHasNoChef")
    public ResponseEntity<?> getGelahHasNoChef(){
        return ResponseEntity.status(200).body(clientService.getGelahHasNoChef());
    }

    @GetMapping("/getGelahWithChefInfo")
    public ResponseEntity<?> getGelahHasChefWithChefInfo(){
        return ResponseEntity.status(200).body(clientService.getGelahHasChefWithChefInfo());
    }

    @GetMapping("/getRatingHighestToLowest")
    public ResponseEntity<?> getGelahByRatingHighestToLowest(){
        return ResponseEntity.status(200).body(clientService.getGelahByRatingHighestToLowest());
    }

    @GetMapping("/getHistoryOrders")
    public ResponseEntity<?> getHistoryOrders(@PathVariable Integer clientId){
        List<OrderGelah> checkStatus = clientService.getHistoryOrders(clientId);
        if (checkStatus == null){
            return ResponseEntity.status(404).body(new ApiResponse("Client with id '" + clientId + "' not found"));
        }
        return ResponseEntity.status(200).body(checkStatus);
    }

    @GetMapping("/getReviewGelah/{gelahId}")
    public ResponseEntity<?> getReviewByGelahId(@PathVariable Integer gelahId){
        List<Object[]> check = clientService.getReviewsByGelahId(gelahId);
        if (check == null){
            return ResponseEntity.status(404).body(new ApiResponse("Gelah with id '"+gelahId+"' not found"));
        }
        return ResponseEntity.status(200).body(clientService.getReviewsByGelahId(gelahId));
    }
}
