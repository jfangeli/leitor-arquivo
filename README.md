#leitor-arquivo
App para leitura de dados de arquivos .DAT em um determinado layout.
Armazena em banco de dados e extrai um relatorio final.

Ao iniciar o app, cria as tabelas necessarias automaticamente (JPA)* e   
schedula a leitura dos arquivos em um diretorio definido por HOMEPATH 
a cada 10 minutos.

A leitura pode ser ativada/desativada atraves de http://localhost/ativar 
ou http://localhost/desativar.

Ao processar move arquivo para HOMEPATH / processado
Gera um relatorio em HOMEPATH / out com resumo:
 - Quantidade de clientes no arquivo de entrada
 - Quantidade de vendedor no arquivo de entrada
 - ID da venda mais cara
 - O pior vendedor

Formato esperado do arquivo:
Dados do vendedor 001çCPFçNameçSalary
Dados do cliente  002çCNPJçNameçBusiness Area
Dados de vendas
Os dados de vendas têm o formato id ​ 003​ . Dentro da linha de vendas, existe a lista
de itens, que é envolto por colchetes []. A linha terá o seguinte formato.
003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

Exemplo de arquivo

001ç1234567891234çDiegoç50000
001ç3245678865434çRenatoç40000.99
001ç4234567891234çDiego2ç50000
001ç5234567891234çDiego3ç70000
001ç6234567891234çDiego5ç60000
001ç7234567891234çDiego6ç40000
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
002ç3345675433444345çEduardo Pereira2çRural
002ç4345675433444345çEduardo Pereira3çRural
002ç5345675433444345çEduardo Pereira4çRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç11ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç12ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç13ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç14ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato
003ç15ç[1-34-10,2-33-1.50,3-40-0.10]çRenato
003ç16ç[1-34-10,2-33-1.50,3-40-0.10]çRenato
003ç17ç[1-34-10,2-33-1.50,3-40-0.10]çDiego2
003ç18ç[1-34-10,2-33-1.50,3-40-0.10]çDiego3
003ç19ç[1-34-10,2-33-1.50,3-40-0.10]çDiego3
003ç20ç[1-34-10,2-33-1.50,3-40-0.10]çDiego4
003ç21ç[1-34-10,2-33-1.50,3-40-0.10]çDiego4
003ç22ç[1-34-10,2-33-1.50,3-40-0.10]çDiego5
003ç24ç[1-34-10,2-33-1.50,3-40-0.10]çDiego5
003ç25ç[1-34-10,2-33-1.50,3-40-0.10]çDiego5
003ç23ç[1-34-10,2-33-1.50,3-40-0.10]çDiego6
003ç27ç[1-34-10,2-33-1.50,3-40-0.10]çDiego6
003ç28ç[1-34-10,2-33-1.50,3-40-0.10]çDiego6

*necessario alterar configs de conexao com banco postgresql


##Tecnologia
Java Spring Boot + Spring Batch
JPA
PostgreSQL

##Rodando
Os passos abaixo sao apenas do app, deve ser instalado anteriormente o banco de
dados Postgresql e alterar as configuracoes de conexao na app.
 
1) Realizar o clone
2) mvn -Dmaven.skip.test=true clean instal  (no test por hora)
3) Definir o diretorio dos arquivos HOMEPATH ou, caso queira, pode ser passado 
   como parametro homepath ao iniciar o app.
4) 