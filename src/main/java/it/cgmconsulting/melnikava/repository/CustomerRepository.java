package it.cgmconsulting.melnikava.repository;

import it.cgmconsulting.melnikava.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT c FROM Customer c WHERE c.customerId = :customerId")
    Optional<Customer> getCustomerByCustomerId(@Param("customerId") long customerId);


}
