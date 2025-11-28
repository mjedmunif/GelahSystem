package org.example.gelahsystem.Service;


import lombok.AllArgsConstructor;
import org.example.gelahsystem.Model.Client;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Repository.ClientRepository;
import org.example.gelahsystem.Repository.GelahRepository;
import org.example.gelahsystem.Repository.OrderGelahRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderGelahService {


    private final OrderGelahRepository orderGelahRepository;
    private final GelahRepository gelahRepository;
    private final ClientRepository clientRepository;


//    todo has Gelah id and ClientId

    public List<OrderGelah> getAllOrders(){
        return orderGelahRepository.findAll();
    }

    public Integer addNewOrder(OrderGelah orderGelah){
        Gelah checkGelah = gelahRepository.findGelahById(orderGelah.getGelahId());
        Client checkClient = clientRepository.findClientById(orderGelah.getClientId());
        List<OrderGelah> conflict = orderGelahRepository.findAcceptedOverlaps(orderGelah.getGelahId() , orderGelah.getDate() , orderGelah.getTimeFrom() , orderGelah.getTimeTo());
        List<OrderGelah> duplicates = orderGelahRepository.findDuplicateOrder(orderGelah.getClientId(), orderGelah.getGelahId(), orderGelah.getDate(), orderGelah.getTimeFrom(), orderGelah.getTimeTo());
        if (checkGelah == null){
            return 1;
        }
        if (checkClient == null){
            return 2;
        }
        if (!conflict.isEmpty()){
            return 4;
        }
        if (!duplicates.isEmpty()){
            return 5;
        }
        orderGelah.setCreatedAt(LocalDate.now());
        orderGelah.setPrice(checkGelah.getPrice());
        orderGelah.setStatus("pending");
        orderGelahRepository.save(orderGelah);
        return 0;
    }

    public Integer updateOrder(Integer id , OrderGelah orderGelah){
       OrderGelah oldOrder = orderGelahRepository.findOrderGelahById(id);
        Client checkClient = clientRepository.findClientById(orderGelah.getClientId());
        Gelah checkGelah = gelahRepository.findGelahById(orderGelah.getGelahId());

        if (oldOrder == null){
            return 1;
        }
        if (checkClient == null){
            return 2;
        }
        if (checkGelah == null){
            return 3;
        }

        oldOrder.setCreatedAt(LocalDate.now());
        oldOrder.setPrice(checkGelah.getPrice());
        oldOrder.setDate(orderGelah.getDate());
        oldOrder.setGelahId(orderGelah.getGelahId());
        oldOrder.setTimeFrom(orderGelah.getTimeFrom());
        oldOrder.setTimeTo(orderGelah.getTimeTo());
        oldOrder.setLocation(orderGelah.getLocation());
        oldOrder.setClientId(orderGelah.getClientId());
        orderGelahRepository.save(oldOrder);
        return 0;
    }

    public Boolean deleteOrder(Integer id){
        OrderGelah deleted = orderGelahRepository.findOrderGelahById(id);
        if (deleted == null){
            return false;
        }
        orderGelahRepository.delete(deleted);
        return true;
    }

}
