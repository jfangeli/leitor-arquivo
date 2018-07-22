package br.com.ntconsulting.leitorarquivo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ntconsulting.leitorarquivo.service.ArquivoService;

@Component
public class JobArquivoListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobArquivoListener.class);
	
	@Autowired
	private ArquivoService arquivoService;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("JOB Finalizado, gerando relatorio final");
			
			//TODO: Pegar do contexto do Spring
			arquivoService.gerarRelatorio(0l);
			
			log.info("Relatorio gerado");
			
		}else {
			log.error("Erro ao processar o JOB. Status:"+jobExecution.getStatus() + ", Exist Status: "+jobExecution.getExitStatus());
		}
	}
}