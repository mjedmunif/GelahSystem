package org.example.gelahsystem.Service;
import lombok.AllArgsConstructor;
import org.example.gelahsystem.API.APIExecption;
import org.example.gelahsystem.Model.ChefInfo;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Repository.ChefInfoRepository;
import org.example.gelahsystem.Repository.GelahRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class ChefInfoService {

    private final ChefInfoRepository chefInfoRepository;
    private final GelahRepository gelahRepository;

    public List<ChefInfo> getChefs(){
        return chefInfoRepository.findAll();
    }

    public void addChefeInfo(ChefInfo chefInfo){
        Gelah checkGelah = gelahRepository.findGelahById(chefInfo.getGelahId());
        if (checkGelah == null){
            throw new APIExecption("Gelah not found");
        }
        checkGelah.setPrice(chefInfo.getPrice() + checkGelah.getPrice());
        checkGelah.setHasChef(true);
        ChefInfo newChef = new ChefInfo();
        newChef.setName(chefInfo.getName());
        newChef.setExperience(chefInfo.getExperience());
        newChef.setGelahId(chefInfo.getGelahId());
        newChef.setPhoneNumber(chefInfo.getPhoneNumber());
        newChef.setSpeciality(chefInfo.getSpeciality());
        newChef.setPrice(chefInfo.getPrice());
        chefInfoRepository.save(newChef);
    }

    public void updateChefInfo(Integer id , ChefInfo chefInfo){
        ChefInfo chefInfo1 = chefInfoRepository.findChefInfoById(id);
        Gelah checkGelah = gelahRepository.findGelahById(chefInfo.getGelahId());
        if (chefInfo1 == null){
            throw new APIExecption("chef not found");
        }
        if (checkGelah == null){
            throw new APIExecption("Gelah not found");
        }
        checkGelah.setHasChef(true);
        chefInfo1.setExperience(chefInfo.getExperience());
        chefInfo1.setGelahId(chefInfo.getGelahId());
        chefInfo1.setName(chefInfo.getName());
        chefInfo1.setPhoneNumber(chefInfo.getPhoneNumber());
        chefInfo1.setPrice(chefInfo.getPrice());
        chefInfo1.setSpeciality(chefInfo.getSpeciality());
        checkGelah.setPrice(chefInfo1.getPrice() + checkGelah.getPrice());
        chefInfoRepository.save(chefInfo1);
    }

    public void deleteChefInfo(Integer id){
        ChefInfo chefInfo = chefInfoRepository.findChefInfoById(id);
        Gelah checkGelah = gelahRepository.findGelahById(chefInfo.getGelahId());
        if (chefInfo == null){
            throw new APIExecption("chef not found");
        }
        if (!checkGelah.getStatus().equalsIgnoreCase("available")){
            throw new APIExecption("chef is not available");
        }
        checkGelah.setHasChef(false);
        chefInfoRepository.delete(chefInfo);
    }
}
