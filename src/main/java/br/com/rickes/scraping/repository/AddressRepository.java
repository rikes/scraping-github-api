package br.com.rickes.scraping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rickes.scraping.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long >{

	Optional<Address> findByUrl(String pUrl);
    
}