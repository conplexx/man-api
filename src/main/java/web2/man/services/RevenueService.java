package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.models.data.FinancialReportRevenueDay;
import web2.man.models.entities.EquipmentCategory;
import web2.man.models.entities.Revenue;
import web2.man.repositories.EquipmentCategoryRepository;
import web2.man.repositories.RevenueRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RevenueService {
//    @Autowired
//    final RevenueRepository revenueRepository;
//
//    @Transactional
//    public Revenue save(Revenue revenue) {
//        return revenueRepository.save(revenue);
//    }
//    @Transactional
//    public void deleteById(UUID id) {
//        revenueRepository.deleteById(id);
//    }
//    public Optional<Revenue> findById(UUID id) {
//        return revenueRepository.findById(id);
//    }
//    public List<Revenue> findAll() {
//        return revenueRepository.findAll();
//    }
//
//    public List<Revenue> findAllWithinDateRange(Date startDate, Date endDate) { return revenueRepository.findAllWithinDateRange(startDate, endDate); }
//    public List<Revenue> findAllWithEquipmentCategoryId(UUID id) { return revenueRepository.findAllWithEquipmentCategoryId(id); }
//    public double findSumWithinDateRange(Date startDate, Date endDate) {
//        return revenueRepository.findSumWithinDateRange(startDate, endDate);
//    }
//    public List<FinancialReportRevenueDay> findDailySumWithinDateRange(Date startDate, Date endDate) {
//        return revenueRepository.findDailySumWithinDateRange(startDate, endDate);
//    }
//    public double findSumWithEquipmentCategoryId(UUID id) {
//        return revenueRepository.findSumWithEquipmentCategoryId(id);
//    }
}