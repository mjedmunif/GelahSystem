package org.example.gelahsystem.Service;


import lombok.AllArgsConstructor;
import org.example.gelahsystem.Model.Client;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.GelahOwner;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Repository.ClientRepository;
import org.example.gelahsystem.Repository.GelahOwnerRepository;
import org.example.gelahsystem.Repository.GelahRepository;
import org.example.gelahsystem.Repository.OrderGelahRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GelahOwnerService {

    private final GelahOwnerRepository gelahOwnerRepository;
    private final ClientRepository clientRepository;
    private final OrderGelahRepository orderGelahRepository;
    private final GelahRepository gelahRepository;

    public List<GelahOwner> getAllGelah(){
        return gelahOwnerRepository.findAll();
    }


   public void addNewOwnerGelah(GelahOwner gelahOwner){
        gelahOwner.setCreatedAt(LocalDate.now());
        gelahOwnerRepository.save(gelahOwner);
   }

   public Boolean updateInfoOwnerGelah(Integer id , GelahOwner gelahOwner){
        GelahOwner g = gelahOwnerRepository.findGelahOwnerById(id);
        if (g == null){
            return false;
        }
        g.setBirthDate(gelahOwner.getBirthDate());
        g.setCity(gelahOwner.getCity());
        g.setEmail(gelahOwner.getEmail());
        g.setFirstName(gelahOwner.getFirstName());
        g.setLastName(gelahOwner.getLastName());
        g.setPassword(gelahOwner.getPassword());
        g.setUsername(gelahOwner.getUsername());
        g.setVehicleType(gelahOwner.getVehicleType());
        g.setPhoneNumber(gelahOwner.getPhoneNumber());
        gelahOwnerRepository.save(g);
        return true;
   }

   public Boolean deleteOwnerGelah(Integer id){
        GelahOwner deleted = gelahOwnerRepository.findGelahOwnerById(id);
        if (deleted == null){
            return false;
        }
        gelahOwnerRepository.delete(deleted);
        return true;
   }

   public Integer applyOrder(Integer clientId, Integer gelahId) {
       Gelah gelah = gelahRepository.findGelahById(gelahId);
       Client client = clientRepository.findClientById(clientId);
       OrderGelah order = orderGelahRepository.findOrderGelahByClientIdAndGelahId(clientId, gelahId);
       if (gelahId == null) {
           return 1;
       }
       if (client == null) {
           return 2;
       }
       if (order == null) {
           return 3;
       }
       if (order.getStatus().equalsIgnoreCase("canceled")){
           return 5;
       }
       List<OrderGelah> sameDay = orderGelahRepository.findOrderGelahByGelahIdAndDate(gelahId , order.getDate());
       for (OrderGelah existing : sameDay){
           if (existing.getStatus().equalsIgnoreCase("accepted")){
               if (order.getTimeFrom().isBefore(existing.getTimeTo()) && order.getTimeTo().isAfter(existing.getTimeFrom())){
                   return 4;
               }
           }
       }
       order.setStatus("accepted");
       orderGelahRepository.save(order);

//       if accepted one order reject all order same time
       for (OrderGelah pending : sameDay){
           if (!pending.getId().equals(order.getId()) && pending.getStatus().equalsIgnoreCase("pending")){
               if (order.getTimeFrom().isBefore(pending.getTimeTo()) && order.getTimeTo().isAfter(pending.getTimeFrom())){
                   pending.setStatus("rejected");
                   orderGelahRepository.save(pending);
               }
           }
       }
       return 0;
   }

   public Integer rejectOrder(Integer clientId , Integer gelahId){
       Client client = clientRepository.findClientById(clientId);
       Gelah gelah = gelahRepository.findGelahById(gelahId);
       OrderGelah order = orderGelahRepository.findOrderGelahByClientIdAndGelahId(clientId, gelahId);
       if (gelahId == null){
           return 1;
       }
       if (client == null){
           return 2;
       }
       if (order == null){
           return 3;
       }
       if (gelah.getStatus().equalsIgnoreCase("rejected")){
           return 4;
       }
       order.setStatus("rejected");
       orderGelahRepository.save(order);
       return 0;
   }


}
