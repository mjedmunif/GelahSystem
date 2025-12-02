package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.GelahOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GelahOwnerRepository extends JpaRepository<GelahOwner , Integer> {
    GelahOwner findGelahOwnerById(Integer id);

    @Query("select g.firstName,g.lastName, g.phoneNumber from GelahOwner g where g.firstName like %?1")
    List<Object[]> findGelahOwnersByFirstName(String firstName);
}
