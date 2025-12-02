package org.example.gelahsystem.Service;


import lombok.AllArgsConstructor;
import org.example.gelahsystem.API.APIExecption;
import org.example.gelahsystem.Model.Client;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final OrderGelahRepository orderGelahRepository;
    private final GelahRepository gelahRepository;
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final GelahOwnerRepository gelahOwnerRepository;

    public List<Client> getAllClient(){
        return clientRepository.findAll();
    }

    public void addNewClient(Client client){
        client.setCreatedAt(LocalDate.now());
        clientRepository.save(client);
    }

    public void updateClient(Integer id , Client client){
       Client c =  clientRepository.findClientById(id);
       if (c == null){
           throw new APIExecption("Client not found");
       }
       c.setBirthDate(client.getBirthDate());
       c.setCity(client.getCity());
       c.setEmail(client.getEmail());
       c.setFirstName(client.getFirstName());
       c.setLastName(client.getLastName());
       c.setPhoneNumber(client.getPhoneNumber());
       c.setUsername(client.getUsername());
       c.setPassword(client.getPassword());
       clientRepository.save(c);
    }

    public void deleteClient(Integer id){
        Client c = clientRepository.findClientById(id);
        if (c == null){
            throw new APIExecption("Client not found");
        }
        clientRepository.delete(c);
    }

    public List<Object[]> getReservedTimes(Integer gelahId){
        Gelah check = gelahRepository.findGelahById(gelahId);
        if (check == null){
            throw new APIExecption("Gelah not found");
        }
        return orderGelahRepository.findOrderGelahsByGelahIdAndStatusOrderByTimeFromAsc(gelahId , "accepted");
    }


    public void cancelOrder(Integer clientId , Integer gelahId ){
        OrderGelah cancelOrder = orderGelahRepository.findOrderGelahByClientIdAndGelahId(clientId, gelahId);
        if (cancelOrder == null){
            throw new APIExecption("order not found");
        }
        if (cancelOrder.getStatus().equalsIgnoreCase("accepted")){
            throw new APIExecption("this order accepted by owner");
        }
        if (cancelOrder.getStatus().equalsIgnoreCase("rejected")){
            throw new APIExecption("order rejected by owner");
        }
        cancelOrder.setStatus("canceled");
        orderGelahRepository.save(cancelOrder);
    }

    public List<Gelah> getGelahbyPriceLowestToHighest(){
        if (gelahRepository.getGelahbyPriceFromLowestToHighest().isEmpty()){
            return null;
        }
        return gelahRepository.getGelahbyPriceFromLowestToHighest();
    }

    public List<Gelah> getGelahHasNoChef(){
        return gelahRepository.findGelahByHasChef(false);
    }

    public List<Object[]> getGelahHasChefWithChefInfo(){
        return gelahRepository.getGelahByHasChefAndChefInfo();
    }

    public List<Gelah> getGelahByRatingHighestToLowest(){
        return gelahRepository.getGelahByRatingHighestToLowest();
    }

    public List<OrderGelah> getHistoryOrders(Integer clientId){
        Client c = clientRepository.findClientById(clientId);
        if (c == null){
            throw new APIExecption("Client not found");
        }
        return orderGelahRepository.findOrderGelahByClientId(clientId);
    }

    public List<Object[]> getReviewsByGelahId(Integer gelahId){
        Gelah checkStatus = gelahRepository.findGelahById(gelahId);
        if (checkStatus == null){
            throw new APIExecption("Gelah not found");
        }
        return reviewRepository.getReviewByGelahId(gelahId);
    }

    public List<Object[]> getOwnersByFirstName(String firstName) {
        return gelahOwnerRepository.findGelahOwnersByFirstName(firstName);
    }

}
