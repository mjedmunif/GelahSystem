package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.Gelah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GelahRepository extends JpaRepository<Gelah , Integer> {
    Gelah findGelahById(Integer id);

    Gelah findGelahByOwnerId(Integer id);

    @Query("select g from Gelah g order by g.price asc")
    List<Gelah> getGelahbyPriceFromLowestToHighest();

    List<Gelah> findGelahByHasChef(boolean hasChef);

    @Query("select g.id, g.location,g.price ,c.name, c.speciality from Gelah g join ChefInfo c on g.id = c.gelahId where g.hasChef = true ")
    List<Object[]>getGelahByHasChefAndChefInfo();


    @Query("select g from Gelah g order by g.rating desc ")
    List<Gelah> getGelahByRatingHighestToLowest();



}
