package org.example.gelahsystem.Service;


import lombok.AllArgsConstructor;
import org.example.gelahsystem.Model.Client;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Repository.ClientRepository;
import org.example.gelahsystem.Repository.GelahRepository;
import org.example.gelahsystem.Repository.OrderGelahRepository;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final OrderGelahRepository orderGelahRepository;
    private final GelahRepository gelahRepository;

    public List<Client> getAllClient(){
        return clientRepository.findAll();
    }

    public void addNewClient(Client client){
        client.setCreatedAt(LocalDate.now());
        clientRepository.save(client);
    }

    public Boolean updateClient(Integer id , Client client){
       Client c =  clientRepository.findClientById(id);
       if (c == null){
           return false;
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
       return true;
    }

    public Boolean deleteClient(Integer id){
        Client c = clientRepository.findClientById(id);
        if (c == null){
            return false;
        }
        clientRepository.delete(c);
        return true;
    }

    public List<Object[]> getReservedTimes(Integer gelahId){
        Gelah check = gelahRepository.findGelahById(gelahId);
        if (check == null){
            return null;
        }
        return orderGelahRepository.findOrderGelahsByGelahIdAndStatusOrderByTimeFromAsc(gelahId , "accepted");
    }


    public Integer cancelOrder(Integer clientId , Integer gelahId ){
        OrderGelah cancelOrder = orderGelahRepository.findOrderGelahByClientIdAndGelahId(clientId, gelahId);
        if (cancelOrder == null){
            return 1;
        }
        if (cancelOrder.getStatus().equalsIgnoreCase("accepted")){
            return 2;
        }
        if (cancelOrder.getStatus().equalsIgnoreCase("rejected")){
            return 3;
        }
        cancelOrder.setStatus("canceled");
        orderGelahRepository.save(cancelOrder);
        return 0;
    }
}
