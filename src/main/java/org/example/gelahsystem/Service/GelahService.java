package org.example.gelahsystem.Service;

import lombok.AllArgsConstructor;
import org.example.gelahsystem.API.APIExecption;
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

    public void addNewGelah(Gelah gelah){
        GelahOwner gelahOwner = gelahOwnerRepository.findGelahOwnerById(gelah.getOwnerId());
        if (gelahOwner == null){
            throw new APIExecption("owner not found");
        }
        gelah.setHasChef(false);
        gelahRepository.save(gelah);
    }

    public Integer updateGelah(Integer id,Gelah gelah){
        GelahOwner gelahOwner = gelahOwnerRepository.findGelahOwnerById(gelah.getOwnerId());
        Gelah oldGelah = gelahRepository.findGelahById(id);
        if (gelahOwner == null){
            throw new APIExecption("Gelah not found");
        }
        if (oldGelah == null){
            throw new APIExecption("gelah is not added");
        }
        oldGelah.setHasChef(gelah.getHasChef());
        oldGelah.setLocation(gelah.getLocation());
        oldGelah.setOwnerId(gelah.getOwnerId());
        oldGelah.setPrice(gelah.getPrice());
        gelahRepository.save(oldGelah);
        return 0;
    }

    public void deleteGelah(Integer id){
        Gelah deleted = gelahRepository.findGelahById(id);
        if (deleted == null){
            throw new APIExecption("Gelah not found");
        }
        if (!deleted.getStatus().equalsIgnoreCase("available")){
            throw new APIExecption("Gelah is Reserved");
        }
        gelahRepository.delete(deleted);
    }

}
