CREATE TABLE doacao (
	id serial NOT NULL,
	donatario_id BIGINT NOT NULL,
	data DATE NOT NULL,
	descricao varchar(255) NOT NULL,
	CONSTRAINT pk_doacao PRIMARY KEY (id)
);

ALTER TABLE doacao ADD CONSTRAINT fk_doacao_donatario FOREIGN KEY (donatario_id) REFERENCES donatario(id);