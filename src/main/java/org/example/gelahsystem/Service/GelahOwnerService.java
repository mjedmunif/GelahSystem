package org.example.gelahsystem.Service;


import lombok.AllArgsConstructor;
import org.example.gelahsystem.API.APIExecption;
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
    private final PdfService pdfService;
    private final EmailService emailService;

    public List<GelahOwner> getAllGelah(){
        return gelahOwnerRepository.findAll();
    }


   public void addNewOwnerGelah(GelahOwner gelahOwner){
        gelahOwner.setCreatedAt(LocalDate.now());
        gelahOwnerRepository.save(gelahOwner);
   }

   public void updateInfoOwnerGelah(Integer id , GelahOwner gelahOwner){
        GelahOwner g = gelahOwnerRepository.findGelahOwnerById(id);
        if (g == null){
            throw new APIExecption("Gelah owner not found");
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
   }
   public void deleteOwnerGelah(Integer id){
        GelahOwner deleted = gelahOwnerRepository.findGelahOwnerById(id);
        if (deleted == null){
            throw new APIExecption("owner with id '"+ id + "' Not found");
        }
        gelahOwnerRepository.delete(deleted);
   }

   public void applyOrder(Integer clientId, Integer gelahId) {
       Gelah gelah = gelahRepository.findGelahById(gelahId);
       Client client = clientRepository.findClientById(clientId);
       OrderGelah order = orderGelahRepository.findOrderGelahByClientIdAndGelahId(clientId, gelahId);
       if (gelah == null) {
           throw new APIExecption("Gelah not found");
       }
       if (client == null) {
           throw new APIExecption("Client not found");
       }
       if (order == null) {
           throw new APIExecption("client has no order with Gelah id " + gelahId);
       }
       if (order.getStatus().equalsIgnoreCase("canceled")){
           throw new APIExecption("Order canceled by client");
       }
       List<OrderGelah> overlaps = orderGelahRepository.findAcceptedOverlaps(gelahId, order.getDate(), order.getTimeFrom(), order.getTimeTo());
       if (!overlaps.isEmpty()) {
           throw new APIExecption("this order accepted");
       }
       order.setStatus("accepted");
       orderGelahRepository.save(order);
       byte[] pdf = pdfService.generateInvoicePdf(
               client.getFirstName() + " " + client.getLastName(),
               "قيلة " + gelah.getLocation(),
               order.getPrice());

       emailService.sendEmailWithPdf(client.getEmail(), "طلب القيلة", "تم قبول طلبك في قيلة " + gelah.getLocation(), pdf, "order-invoice.pdf");
       List<OrderGelah> pendingOverlaps = orderGelahRepository.findPendingOverlaps(gelahId, order.getDate(), order.getTimeFrom(), order.getTimeTo());
       for (OrderGelah p : pendingOverlaps) {
           p.setStatus("rejected");
           orderGelahRepository.save(p);
       }
   }

   public void rejectOrder(Integer clientId , Integer gelahId){
       Client client = clientRepository.findClientById(clientId);
       Gelah gelah = gelahRepository.findGelahById(gelahId);
       OrderGelah order = orderGelahRepository.findOrderGelahByClientIdAndGelahId(clientId, gelahId);
       if (gelahId == null){
           throw new APIExecption("Gelah not found");
       }
       if (client == null){
           throw new APIExecption("Client bot found");
       }
       if (order == null){
           throw new APIExecption("client has no order with Gelah id " + gelahId);
       }
       if (gelah.getStatus().equalsIgnoreCase("rejected")){
           throw new APIExecption("order rejected by Owner id");
       }
       order.setStatus("rejected");
       orderGelahRepository.save(order);

   }

   public List<OrderGelah> getGelahByStatus(Integer ownerId){
        Gelah check = gelahRepository.findGelahByOwnerId(ownerId);
        if (check == null){
            throw new APIExecption("invalid id");
        }
        return orderGelahRepository.getGelahByStatus(ownerId);
   }
}
