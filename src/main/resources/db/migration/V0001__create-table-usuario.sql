CREATE TABLE usuario (
	id serial NOT NULL,
	nome varchar(150) NOT NULL,
	cpf varchar(14) NOT NULL,
	email varchar(200) NOT NULL,
	senha varchar(100) NOT NULL,
	autorizado boolean NOT NULL DEFAULT false,
	admin boolean NOT NULL DEFAULT false,
	CONSTRAINT pk_usuario PRIMARY KEY (id),
	CONSTRAINT uk_usuario_cpf UNIQUE (cpf),
	CONSTRAINT uk_usuario_email UNIQUE (email)
);

INSERT INTO usuario(nome, cpf, email, senha, autorizado, admin) VALUES('Igor Gonçalves Shimauti', '362.851.178-02', 'igorshimauti@gmail.com', '1EA439E455552EC2203D062EDA80073FD5EF09258258BA2CF2E78F8E929A7134', true, true);