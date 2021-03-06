package br.com.ntconsulting.leitorarquivo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ntconsulting.leitorarquivo.model.Vendedor;

@Repository
@Transactional
public interface VendedorRepository extends CrudRepository<Vendedor, Long>{
	
	@Query("SELECT vend FROM Vendedor vend WHERE vend.arquivo.id=(:pIdArquivo)")
	List<Vendedor> findByArquivo(@Param("pIdArquivo") Long pIdArquivo);		
}
