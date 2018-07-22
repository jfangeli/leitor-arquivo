package br.com.ntconsulting.leitorarquivo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ntconsulting.leitorarquivo.model.Venda;

@Repository
@Transactional
public interface VendaRepository extends CrudRepository<Venda, Long>{

	@Query(value = "select v2.* "
			+"from venda v2, "
			+"( select v.id, sum(vi.preco) from venda v, venda_item vi "
			+"where vi.venda_id = v.id "
			+"and v.arquivo_id = (:pIdArquivo)  "
			+"group by v.id "
			+"order by 2 desc  "
			+"limit 1 ) d  "
			+"where d.id = v2.id  ", nativeQuery = true)
	Venda findVendaMaisCara(@Param("pIdArquivo") Long pIdArquivo);

	
	@Query(value = "select v2.* " + 
			"	from venda v2, " + 
			"	( select v.nomevendedor, sum(vi.preco) from venda v, venda_item vi " + 
			"	where vi.venda_id = v.id " + 
			"	and v.arquivo_id = (:pIdArquivo)  " + 
			"	group by v.id " + 
			"	order by 2 asc " + 
			"	limit 1 ) d " + 
			"	where d.nomevendedor = v2.nomevendedor " + 
			"	and v2.arquivo_id = (:pIdArquivo)  " + 
			"	limit 1 ", nativeQuery = true)
	Venda findPiorVendedor(@Param("pIdArquivo") Long pIdArquivo);
	
}
