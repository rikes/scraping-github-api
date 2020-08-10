package br.com.rickes.scraping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rickes.scraping.model.Archive;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long >{
    
    List<Archive> findByUrl(String pUrl);

}