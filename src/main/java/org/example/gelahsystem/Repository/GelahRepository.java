package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.Gelah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GelahRepository extends JpaRepository<Gelah , Integer> {
    Gelah findGelahById(Integer id);
}
