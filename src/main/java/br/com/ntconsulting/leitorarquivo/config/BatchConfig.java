package br.com.ntconsulting.leitorarquivo.config;

import java.io.File;
import java.io.FilenameFilter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.ntconsulting.leitorarquivo.model.Cliente;
import br.com.ntconsulting.leitorarquivo.model.ClienteArquivo;
import br.com.ntconsulting.leitorarquivo.model.Vendedor;
import br.com.ntconsulting.leitorarquivo.model.VendedorArquivo;
import br.com.ntconsulting.leitorarquivo.processor.ClienteItemProcessador;
import br.com.ntconsulting.leitorarquivo.processor.VendedorItemProcessador;
import br.com.ntconsulting.leitorarquivo.service.JobArquivoListener;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@ComponentScan
@EnableTransactionManagement
public class BatchConfig {


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
		lef.setJpaVendorAdapter(jpaVendorAdapter);
		//lef.setJpaProperties(new Properties());		
		return lef;
	}
	
	@Bean("bTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("bEntityManager") EntityManagerFactory entityManager) {
		return new JpaTransactionManager(entityManager);
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
	public JobLauncher createJobLauncher(@Qualifier("bJobRepository") JobRepository jobRepository) throws Exception {
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
	
	@Bean
	public Job processarArquivo(@Qualifier("bJobRepository") JobRepository jobRepository, 
			JobArquivoListener listener, 
			@Qualifier("stepVendedor") Step stepVendedor,
			@Qualifier("stepCliente") Step stepCliente) {
		
		return jobBuilderFactory.get("importUserJob")
				.repository(jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(stepVendedor)
				.next(stepCliente)
				.build();
	}

	@Bean("stepCliente")
	public Step stepCliente(ItemReader<ClienteArquivo> reader, ItemProcessor<ClienteArquivo, Cliente> processor,
			ItemWriter<Cliente> writer) {
		return stepBuilderFactory.get("stepVendedor")
				.<ClienteArquivo, Cliente>chunk(100)
				.reader(reader)
				.processor(processor)
				// .startLimit(3)
				.writer(writer).build();
	}
		
	@Bean
    public FlatFileItemReader<ClienteArquivo> readerCliente() {
        definirArquivo();
		FileSystemResource resource = new FileSystemResource(getArquivo());
        FlatFileItemReaderBuilder<ClienteArquivo> builder =  new FlatFileItemReaderBuilder<ClienteArquivo>()
                .name("clienteItemReader")
                .resource(resource)
                .delimited()
                .delimiter("ç")
                .names(new String[]{"identificador", "cnpj", "nome", "areaNegocio"});
        
        builder.fieldSetMapper(new ClienteArquivo.ClienteFieldSetMapper(resource.getFilename()));
        return builder.build();        
    }

    @Bean
    public ClienteItemProcessador processorCliente() {
        return new ClienteItemProcessador();
    }
    

    @Bean
	public JdbcBatchItemWriter<Cliente> writerCliente(@Qualifier("bDataSource") DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Cliente>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO cliente (id, arquivo, cnpj, nome, areaNegocio) VALUES ((select nextval ('hibernate_sequence')), :arquivo, :cnpj, :nome, :areaNegocio)").dataSource(dataSource)
				.build();
	}
		
	
	@Bean("stepVendedor")
	public Step stepVendedor(ItemReader<VendedorArquivo> reader, ItemProcessor<VendedorArquivo, Vendedor> processor,
			ItemWriter<Vendedor> writer) {
		return stepBuilderFactory.get("stepVendedor")
				.<VendedorArquivo, Vendedor>chunk(100)
				.reader(reader)
				.processor(processor)
				// .startLimit(3)
				.writer(writer).build();
	}
	
	@Bean
    public FlatFileItemReader<VendedorArquivo> readerVendedor() {
        definirArquivo();
		FileSystemResource resource = new FileSystemResource(getArquivo());
        FlatFileItemReaderBuilder<VendedorArquivo> builder =  new FlatFileItemReaderBuilder<VendedorArquivo>()
                .name("vendedorItemReader")
                .resource(resource)
                .delimited()
                .delimiter("ç")
                .names(new String[]{"identificador", "cpf", "nome", "salario"});
        
        builder.fieldSetMapper(new VendedorArquivo.VendedorFieldSetMapper(resource.getFilename()));
        return builder.build();        
    }

    @Bean
    public VendedorItemProcessador processorVendedor() {
        return new VendedorItemProcessador();
    }
    

    @Bean
	public JdbcBatchItemWriter<Vendedor> writerVendedor(@Qualifier("bDataSource") DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Vendedor>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO vendedor (id, arquivo, cpf, nome, salario) VALUES ((select nextval ('hibernate_sequence')), :arquivo, :cpf, :nome, :salario)").dataSource(dataSource)
				.build();
	}
	
	protected String getArquivo() {
		return System.getProperty("diretorio") +"/"+ System.getProperty("arquivo");
	}
	
	protected String getNomeArquivo() {
		return System.getProperty("arquivo");
	}
    
	protected  void definirArquivo() {
		String diretorio = System.getProperty("diretorio");
		String arquivo = System.getProperty("arquivo");
		
		if(null == diretorio || null == arquivo) {
			diretorio = System.getenv("HOMEPATH") + "/data";	
			System.setProperty("diretorio", diretorio);
			
			File file = new File(diretorio);
			File[] f = file.listFiles(new FilenameFilter() {
			    @Override
			    public boolean accept(File dir, String name) {
			        name.toUpperCase().endsWith(".DAT");
			        return true;
			    }
			});
			
			System.setProperty("arquivo",(f.length>0 ? f[0].getName() : ""));
		}
		
	}

}
