package br.com.ntconsulting.leitorarquivo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ntconsulting.leitorarquivo.model.Arquivo;

@Repository
@Transactional
public interface ArquivoRepository extends JpaRepository<Arquivo, Long>{
	
	@Query("SELECT arq FROM Arquivo arq ORDER BY arq.id DESC LIMIT 1")
	Optional<Arquivo> findlast();
	
}
