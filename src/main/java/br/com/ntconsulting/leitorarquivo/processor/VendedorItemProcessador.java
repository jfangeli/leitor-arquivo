package br.com.ntconsulting.leitorarquivo.processor;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.com.ntconsulting.leitorarquivo.model.Arquivo;
import br.com.ntconsulting.leitorarquivo.model.Vendedor;
import br.com.ntconsulting.leitorarquivo.model.VendedorArquivo;

public class VendedorItemProcessador implements ItemProcessor<VendedorArquivo, Vendedor> {

    private static final Logger log = LoggerFactory.getLogger(VendedorItemProcessador.class);

    @Override
    public Vendedor process(final VendedorArquivo vendedor) throws Exception {
        
    	if(null == vendedor || !"001".equals(vendedor.getIdentificador())) {
    		return null;
    	}
    	
    	final String nome = vendedor.getNome().toUpperCase();
        
        final Vendedor vendedorProcessado = new Vendedor(vendedor.getCpf(), nome, vendedor.getSalario());
        vendedorProcessado.setArquivo(montarArquivo(vendedor.getIdArquivo(),vendedor.getNomeArquivo(), vendedorProcessado));
        
        log.debug("Vendedor processado de (" + vendedor + ") para (" + vendedorProcessado + ")");

        return vendedorProcessado;        
    }
    
    protected Arquivo montarArquivo(Long idArquivo, String nomeArquivo, Vendedor vendedor) {
    	Arquivo arquivo = new Arquivo();
    	arquivo.setId(idArquivo);
    	arquivo.setNome(nomeArquivo);
    	arquivo.addVendedor(vendedor);
    	arquivo.setData(new Date());
    	return arquivo;
    }

}
