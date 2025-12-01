package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.GelahOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GelahOwnerRepository extends JpaRepository<GelahOwner , Integer> {
    GelahOwner findGelahOwnerById(Integer id);

    List<GelahOwner> findGelahOwnersByFirstName(String firstName);
}
