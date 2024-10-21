package web2.man.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web2.man.models.data.FinancialReportRevenueDay;
import web2.man.models.entities.Revenue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, UUID> {
//    @Query("SELECT SUM(r.value) FROM Revenue r WHERE r.equipmentCategory.id = :id")
//    Double findSumWithEquipmentCategoryId(@Param("id") UUID id);
//    List<Revenue> findAllWithEquipmentCategoryId(UUID id);
//    @Query("SELECT SUM(r.value) FROM Revenue r WHERE r.date >= :startDate AND r.date <= :endDate")
//    Double findSumWithinDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
//    @Query("SELECT r FROM Revenue r WHERE r.date >= :startDate AND s.date <= :endDate")
//    List<Revenue> findAllWithinDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
//    @Query("SELECT new web2.man.models.data.FinancialReportRevenueDay(r.date, SUM(r.value)) " +
//            "FROM Revenue r WHERE r.date >= :startDate AND r.date <= :endDate " +
//            "GROUP BY r.date ORDER BY r.date")
//    List<FinancialReportRevenueDay> findDailySumWithinDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
