package book.demo.java.repository;

import book.demo.java.entity.account.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
