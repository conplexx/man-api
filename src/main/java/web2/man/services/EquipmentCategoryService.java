package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.models.entities.AuthToken;
import web2.man.models.entities.EquipmentCategory;
import web2.man.repositories.AuthTokenRepository;
import web2.man.repositories.EquipmentCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentCategoryService {
    @Autowired
    final EquipmentCategoryRepository equipmentCategoryRepository;

    @Transactional
    public EquipmentCategory save(EquipmentCategory category) {
        return equipmentCategoryRepository.save(category);
    }
    @Transactional
    public void deleteById(UUID id) {
        equipmentCategoryRepository.deleteById(id);
    }
    public Optional<EquipmentCategory> findById(UUID id) {
        return equipmentCategoryRepository.findById(id);
    }
    public Optional<EquipmentCategory> findByName(String name) {
        return equipmentCategoryRepository.findByName(name);
    }
    public List<EquipmentCategory> findAll() {
        return equipmentCategoryRepository.findAll();
    }
}