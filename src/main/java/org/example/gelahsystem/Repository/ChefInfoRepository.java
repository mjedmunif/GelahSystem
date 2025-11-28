package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.ChefInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefInfoRepository extends JpaRepository<ChefInfo , Integer> {
    ChefInfo findChefInfoById(Integer id);
}
