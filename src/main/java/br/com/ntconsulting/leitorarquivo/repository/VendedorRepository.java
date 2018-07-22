package br.com.ntconsulting.leitorarquivo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.ntconsulting.leitorarquivo.model.Vendedor;

@Repository
public interface VendedorRepository extends CrudRepository<Vendedor, Long>{

}
