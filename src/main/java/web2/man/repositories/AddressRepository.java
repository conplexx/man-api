package web2.man.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web2.man.models.Address;
import web2.man.models.Address;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Address save(Address address);
    boolean existsById(UUID id);
    Optional<Address> findById(UUID id);
    void deleteById(UUID id);
}
