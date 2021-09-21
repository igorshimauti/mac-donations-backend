CREATE TABLE endereco (
	id serial NOT NULL,
	cep char(9) NOT NULL,
	uf char(2) NOT NULL,
	cidade varchar(50) NOT NULL,
	bairro varchar(50) NOT NULL,
	logradouro varchar(100) NOT NULL,
	numero varchar(20) NOT NULL,
	complemento varchar(50),
	CONSTRAINT pk_endereco PRIMARY KEY (id),
	CONSTRAINT uk_endereco_completo UNIQUE (cep, uf, cidade, bairro, logradouro, numero, complemento)
);