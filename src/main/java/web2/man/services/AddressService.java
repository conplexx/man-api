package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.models.Address;
import web2.man.repositories.AddressRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }
    @Transactional
    public void deleteById(UUID id) {
        addressRepository.deleteById(id);
    }
    @Transactional
    public List<Address> findAll() {
        return addressRepository.findAll();
    }
    public Optional<Address> findById(UUID id) {
        return addressRepository.findById(id);
    }
    public boolean existsById(UUID id) {
        return addressRepository.existsById(id);
    }
}
