package org.example.gelahsystem.Service;
import lombok.AllArgsConstructor;
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

    public Integer addChefeInfo(ChefInfo chefInfo){
        Gelah checkGelah = gelahRepository.findGelahById(chefInfo.getGelahId());
        if (checkGelah == null){
            return 1;
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
        return 0;
    }

    public Integer updateChefInfo(Integer id , ChefInfo chefInfo){
        ChefInfo chefInfo1 = chefInfoRepository.findChefInfoById(id);
        Gelah checkGelah = gelahRepository.findGelahById(chefInfo.getGelahId());
        if (chefInfo1 == null){
            return 1;
        }
        if (checkGelah == null){
            return 2;
        }
        checkGelah.setHasChef(true);
        chefInfo1.setExperience(chefInfo.getExperience());
        chefInfo1.setGelahId(chefInfo.getGelahId());
        chefInfo1.setName(chefInfo.getName());
        chefInfo1.setPhoneNumber(chefInfo.getPhoneNumber());
        chefInfo1.setPrice(chefInfo.getPrice());
        chefInfo1.setSpeciality(chefInfo.getSpeciality());

//        add price chef to gelah cost
        checkGelah.setPrice(chefInfo1.getPrice() + checkGelah.getPrice());

        chefInfoRepository.save(chefInfo1);
        return 0;
    }

    public Integer deleteChefInfo(Integer id){
        ChefInfo chefInfo = chefInfoRepository.findChefInfoById(id);
        Gelah checkGelah = gelahRepository.findGelahById(chefInfo.getGelahId());
        if (chefInfo == null){
            return 1;
        }
        if (!checkGelah.getStatus().equalsIgnoreCase("available")){
            return 2;
        }
        checkGelah.setHasChef(false);
        chefInfoRepository.delete(chefInfo);
        return 0;
    }
}
