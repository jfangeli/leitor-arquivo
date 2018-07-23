package br.com.ntconsulting.leitorarquivo.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.ntconsulting.leitorarquivo.listener.JobArquivoListener;
import br.com.ntconsulting.leitorarquivo.model.Cliente;
import br.com.ntconsulting.leitorarquivo.model.ClienteArquivo;
import br.com.ntconsulting.leitorarquivo.model.Venda;
import br.com.ntconsulting.leitorarquivo.model.VendaArquivo;
import br.com.ntconsulting.leitorarquivo.model.Vendedor;
import br.com.ntconsulting.leitorarquivo.model.VendedorArquivo;
import br.com.ntconsulting.leitorarquivo.processor.ClienteItemProcessador;
import br.com.ntconsulting.leitorarquivo.processor.VendaItemProcessador;
import br.com.ntconsulting.leitorarquivo.processor.VendedorItemProcessador;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@ComponentScan
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "bEntityManager", basePackages = {"br.com.ntconsulting.leitorarquivo.repository"})
@EnableScheduling
public class BatchConfig {

	private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	// @ConfigurationProperties(prefix = "spring.data.datasource")
	@Bean(name = "bDataSource")
	public DataSource datasource() {
		return DataSourceBuilder.create().driverClassName("org.postgresql.Driver")
				.url("jdbc:postgresql://localhost:5432/leitor-arquivo").username("dev").password("12qwaszx").build();
	}

	@Bean("bJpaVendor")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		return jpaVendorAdapter;
	}
	
	@Bean("bEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Qualifier("bDataSource") DataSource dataSource,
			@Qualifier("bJpaVendor") JpaVendorAdapter jpaVendorAdapter) {

		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setPackagesToScan("br.com.ntconsulting.leitorarquivo.model");
		lef.setDataSource(dataSource);
		//lef.setJtaDataSource(dataSource);
		lef.setJpaVendorAdapter(jpaVendorAdapter);
		//lef.setJpaProperties(new Properties());
		return lef;
	}
	
	@Bean("bTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("bEntityManager") EntityManagerFactory entityManager) {
		return new JpaTransactionManager(entityManager	);
	}
	
	@Bean("bJobRepository")
	public JobRepository createJobRepository(@Qualifier("bDataSource") DataSource dataSource, 
			@Qualifier("bTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource);		
		//factory.setDatabaseType("");
		factory.setTransactionManager(transactionManager);
		factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
		factory.setTablePrefix("BATCH_");
		factory.setMaxVarCharLength(1000);		
		return factory.getObject();
	}
	
	@Bean
	public JobLauncher createJobLauncher(@Qualifier("bJobRepository")JobRepository jobRepository
			) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());		
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
		
	}
	
	@Bean
	public Job processarArquivo(@Qualifier("bJobRepository")JobRepository jobRepository 
			,JobArquivoListener listenerJob
			,@Qualifier("stepVendedor") Step stepVendedor
			,@Qualifier("stepCliente") Step stepCliente
			,@Qualifier("stepVenda") Step stepVenda
			) {
		 
		 return jobBuilderFactory.get("processarArquivoJob")
				.repository(jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listenerJob)
				.start(stepVendedor)
				.next(stepCliente)
				.next(stepVenda)
				.build();		 
	}
	
	@Bean("stepVenda")
	public Step stepVenda(ItemReader<VendaArquivo> reader, 
			ItemProcessor<VendaArquivo, Venda> processor,
			ItemWriter<Venda> writer,
			@Qualifier("bTransactionManager")PlatformTransactionManager transactionManager) {
		return stepBuilderFactory.get("stepVenda")
				.<VendaArquivo, Venda>chunk(100)
				.reader(reader)
				.processor(processor)
				// .startLimit(3)				
				.writer(writer)
				.transactionManager(transactionManager)
				.build();
	}
	
	@StepScope
	@Bean
    public FlatFileItemReader<VendaArquivo> readerVenda(
    		@Value("#{jobParameters['arquivo']}") String arquivo,
    		@Value("#{jobParameters['arquivo.id']}") Long idArquivo) {
		FileSystemResource resource = new FileSystemResource(arquivo);
        FlatFileItemReaderBuilder<VendaArquivo> builder =  new FlatFileItemReaderBuilder<VendaArquivo>()
                .name("vendaItemReader")
                .resource(resource)
                .delimited()
                .delimiter("ç")
                .names(new String[]{"identificador", "cnpj", "nome", "areaNegocio"});
        
        builder.fieldSetMapper(new VendaArquivo.VendaFieldSetMapper(arquivo, idArquivo));
        return builder.build();        
    }

    @Bean
    public VendaItemProcessador processorVenda() {
        return new VendaItemProcessador();
    }

    
    @Bean
	public ItemWriter<Venda> writerVenda(
			@Qualifier("bEntityManager") LocalContainerEntityManagerFactoryBean entityManager) {
		JpaItemWriter<Venda> writer = new JpaItemWriter<Venda>();
		writer.setEntityManagerFactory(entityManager.getObject());
		return writer; 	
				
	}

	@Bean("stepCliente")
	public Step stepCliente(ItemReader<ClienteArquivo> reader, 
			ItemProcessor<ClienteArquivo, Cliente> processor,
			ItemWriter<Cliente> writer,
			@Qualifier("bTransactionManager")PlatformTransactionManager transactionManager) {
		return stepBuilderFactory.get("stepCliente")
				.<ClienteArquivo, Cliente>chunk(100)
				.reader(reader)
				.processor(processor)
				// .startLimit(3)
				.writer(writer)
				.transactionManager(transactionManager)
				.build();
	}
		
	@StepScope
	@Bean
    public FlatFileItemReader<ClienteArquivo> readerCliente(
    		@Value("#{jobParameters['arquivo']}") String arquivo,
    		@Value("#{jobParameters['arquivo.id']}") Long idArquivo) {
		FileSystemResource resource = new FileSystemResource(arquivo);
        FlatFileItemReaderBuilder<ClienteArquivo> builder =  new FlatFileItemReaderBuilder<ClienteArquivo>()
                .name("clienteItemReader")
                .resource(resource)
                .delimited()
                .delimiter("ç")
                .names(new String[]{"identificador", "cnpj", "nome", "areaNegocio"});
        
        builder.fieldSetMapper(new ClienteArquivo.ClienteFieldSetMapper(arquivo, idArquivo));
        return builder.build();        
    }

    @Bean
    public ClienteItemProcessador processorCliente() {
        return new ClienteItemProcessador();
    }
    
    @Bean
	public ItemWriter<Cliente> writerCliente(
			@Qualifier("bEntityManager") LocalContainerEntityManagerFactoryBean entityManager) {
		JpaItemWriter<Cliente> writer = new JpaItemWriter<Cliente>();
		writer.setEntityManagerFactory(entityManager.getObject());
		return writer; 	
				
	}
	
	@Bean("stepVendedor")
	public Step stepVendedor(ItemReader<VendedorArquivo> reader, 
			ItemProcessor<VendedorArquivo, Vendedor> processor,
			ItemWriter<Vendedor> writer, 
			@Qualifier("bTransactionManager")PlatformTransactionManager transactionManager) {
		return stepBuilderFactory.get("stepVendedor")
				.<VendedorArquivo, Vendedor>chunk(100)
				.reader(reader)
				.processor(processor)
				// .startLimit(3)
				.writer(writer)
				.transactionManager(transactionManager)
				.build();
	}
	
	@StepScope
	@Bean
    public FlatFileItemReader<VendedorArquivo> readerVendedor(
    		@Value("#{jobParameters['arquivo']}") String arquivo,
    		@Value("#{jobParameters['arquivo.id']}") Long idArquivo) {
		
		FileSystemResource resource = new FileSystemResource(arquivo);
        FlatFileItemReaderBuilder<VendedorArquivo> builder =  new FlatFileItemReaderBuilder<VendedorArquivo>()
                .name("vendedorItemReader")
                .resource(resource)
                .delimited()
                .delimiter("ç")                
                .names(new String[]{"identificador", "cpf", "nome", "salario"});
        
        builder.fieldSetMapper(new VendedorArquivo.VendedorFieldSetMapper(arquivo, idArquivo));
        return builder.build();        
    }

    @Bean
    public VendedorItemProcessador processorVendedor() {
        return new VendedorItemProcessador();
    }
        
	@Bean
	public ItemWriter<Vendedor> writerVendedor(
			@Qualifier("bEntityManager") LocalContainerEntityManagerFactoryBean entityManager) {
		JpaItemWriter<Vendedor> writer = new JpaItemWriter<Vendedor>();
		writer.setEntityManagerFactory(entityManager.getObject());
		return writer; 	
				
	}

}
