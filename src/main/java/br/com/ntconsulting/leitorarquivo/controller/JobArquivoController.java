package br.com.ntconsulting.leitorarquivo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ntconsulting.leitorarquivo.scheduler.JobArquivoScheduler;

@Controller
public class JobArquivoController {

	private static final Logger log = LoggerFactory.getLogger(JobArquivoController.class);
	
	@Autowired
	private JobArquivoScheduler scheduler;

	@RequestMapping("/ativar")
	public String ativar() throws Exception {
		scheduler.ativar();
		log.info("JOB ATIVADO, AGUARDANDO PROCESSAMENTO PELO SCHEDULLER");
		return "job ativado";
	}
	
	@RequestMapping("/desativar")
	public String desativar() throws Exception {
		scheduler.desativar();
		log.info("JOB DESATIVADO");
		return "job desativado";
	}
}
