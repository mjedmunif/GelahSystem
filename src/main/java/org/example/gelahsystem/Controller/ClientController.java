package org.example.gelahsystem.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.Client;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Repository.GelahOwnerRepository;
import org.example.gelahsystem.Repository.GelahRepository;
import org.example.gelahsystem.Repository.OrderGelahRepository;
import org.example.gelahsystem.Service.AIServer;
import org.example.gelahsystem.Service.ClientService;
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
    private final AIServer aiServer;
    private final GelahOwnerRepository gelahOwnerRepository;

    @GetMapping("/get")
    public ResponseEntity<?> getAllClient(){
        return ResponseEntity.status(200).body(clientService.getAllClient());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewClient(@RequestBody @Valid Client client , Errors errors){
        clientService.addNewClient(client);
        return ResponseEntity.status(200).body(new ApiResponse("added Client successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Integer id , @RequestBody @Valid Client client){
         clientService.updateClient(id, client);
            return ResponseEntity.status(200).body(new ApiResponse("updated client successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer id){
        clientService.deleteClient(id);
            return ResponseEntity.status(200).body(new ApiResponse("deleted client successfully"));
    }

    @GetMapping("/getReservedTime/{gelahId}")
    public ResponseEntity<?> getReservedTimes(@PathVariable Integer gelahId ){
        return ResponseEntity.status(200).body(clientService.getReservedTimes(gelahId));
    }

    @GetMapping("/cancelOrder/{clientId}/{gelahId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer clientId , @PathVariable Integer gelahId){
         clientService.cancelOrder(clientId, gelahId);
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

    @GetMapping("/getHistoryOrders/{clientId}")
    public ResponseEntity<?> getHistoryOrders(@PathVariable Integer clientId){
        List<OrderGelah> checkStatus = clientService.getHistoryOrders(clientId);
        return ResponseEntity.status(200).body(checkStatus);
    }

    @GetMapping("/getReviewGelah/{gelahId}")
    public ResponseEntity<?> getReviewByGelahId(@PathVariable Integer gelahId){
         clientService.getReviewsByGelahId(gelahId);
        return ResponseEntity.status(200).body(clientService.getReviewsByGelahId(gelahId));
    }

    @GetMapping(value = "/askAI/{message}", produces = "text/plain; charset=UTF-8")
    public String askAI(@PathVariable String message) {
        return aiServer.getCampingSuggestions(message);
    }

    @GetMapping("/gelahByFistName/{firstName}")
    public ResponseEntity<?> getOwnerByFirstName(@PathVariable String firstName) {
        return ResponseEntity.status(200).body(clientService.getOwnersByFirstName(firstName));
    }
}
