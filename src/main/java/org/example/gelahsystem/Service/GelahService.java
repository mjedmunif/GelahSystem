package org.example.gelahsystem.Service;

import lombok.AllArgsConstructor;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.GelahOwner;
import org.example.gelahsystem.Repository.GelahOwnerRepository;
import org.example.gelahsystem.Repository.GelahRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GelahService {

    private final GelahRepository gelahRepository;
    private final GelahOwnerRepository gelahOwnerRepository;

    public List<Gelah> getAllGelah(){
        return gelahRepository.findAll();
    }

    public Boolean addNewGelah(Gelah gelah){
        GelahOwner gelahOwner = gelahOwnerRepository.findGelahOwnerById(gelah.getOwnerId());
        if (gelahOwner == null){
            return false;
        }
        gelah.setHasChef(false);
        gelahRepository.save(gelah);
        return true;
    }

    public Integer updateGelah(Integer id,Gelah gelah){
        GelahOwner gelahOwner = gelahOwnerRepository.findGelahOwnerById(gelah.getOwnerId());
        Gelah oldGelah = gelahRepository.findGelahById(id);
        if (gelahOwner == null){
            return 1;
        }
        if (oldGelah == null){
            return 2;
        }
        oldGelah.setHasChef(gelah.getHasChef());
        oldGelah.setLocation(gelah.getLocation());
        oldGelah.setOwnerId(gelah.getOwnerId());
        oldGelah.setPrice(gelah.getPrice());
        gelahRepository.save(oldGelah);
        return 0;
    }

    public Integer deleteGelah(Integer id){
        Gelah deleted = gelahRepository.findGelahById(id);
        if (deleted == null){
            return 1;
        }
        if (!deleted.getStatus().equalsIgnoreCase("available")){
            return 2;
        }
        gelahRepository.delete(deleted);
        return 0;
    }

}
