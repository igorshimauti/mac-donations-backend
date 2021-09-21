CREATE TABLE donatario (
	id serial NOT NULL,
	endereco_id BIGINT,
	nome varchar(150) NOT NULL,
	cpf varchar(14) NOT NULL,
	data_nascimento DATE NOT NULL,
	celular varchar(15),
	CONSTRAINT pk_donatario PRIMARY KEY (id),
	CONSTRAINT uk_donatario_cpf UNIQUE (cpf),
	CONSTRAINT uk_donatario_celular UNIQUE (celular)
);

ALTER TABLE donatario ADD CONSTRAINT fk_donatario_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id);