package br.com.ntconsulting.leitorarquivo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ntconsulting.leitorarquivo.model.Cliente;

@Repository
@Transactional
public interface ClienteRepository extends CrudRepository<Cliente, Long>{

	@Query("SELECT cli FROM Cliente cli WHERE cli.arquivo.id=(:pIdArquivo)")
	List<Cliente> findByArquivo(@Param("pIdArquivo") Long pIdArquivo);
	
}
