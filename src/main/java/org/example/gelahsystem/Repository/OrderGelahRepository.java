package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.OrderGelah;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface OrderGelahRepository extends JpaRepository<OrderGelah , Integer> {
    OrderGelah findOrderGelahById(Integer id);

    OrderGelah findOrderGelahByClientIdAndGelahId(Integer clientId, Integer gelahId);

   List <OrderGelah> findOrderGelahByGelahIdAndDate(Integer gelahId, LocalDate date);

    @Query("select o from OrderGelah o where o.clientId = ?1 and o.gelahId = ?2 and o.date = ?3 and o.timeFrom = ?4 and o.timeTo = ?5")
    List<OrderGelah> findDuplicateOrder(Integer clientId, Integer gelahId, LocalDate date, LocalTime timeFrom, LocalTime timeTo);

    @Query("select o from OrderGelah o where o.gelahId = ?1 and o.date = ?2 and o.status = 'accepted' and o.timeFrom < ?4 and o.timeTo > ?3")
    List<OrderGelah> findAcceptedOverlaps(Integer gelahId,LocalDate date,LocalTime timeFrom,LocalTime timeTo);

    @Query("select o.date, o.timeFrom , o.timeTo from OrderGelah o where o.gelahId = ?1 and o.status = ?2 order by o.date, o.timeFrom")
    List<Object[]> findOrderGelahsByGelahIdAndStatusOrderByTimeFromAsc(Integer gelahId, String status);

    @Query("select o from OrderGelah o where o.status = 'pending' and o.gelahId = ?1")
    List<OrderGelah> getGelahByStatus(Integer ownerId);

    List<OrderGelah> findOrderGelahByClientId(Integer clientId);

    @Query("select o from OrderGelah o where o.gelahId = ?1 and o.date = ?2 and o.status = 'pending' and o.timeFrom < ?4 and o.timeTo > ?3")
    List<OrderGelah> findPendingOverlaps(Integer gelahId, LocalDate date, LocalTime timeFrom, LocalTime timeTo);
}
