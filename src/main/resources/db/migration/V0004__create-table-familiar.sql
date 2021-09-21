CREATE TABLE familiar (
	id serial NOT NULL,
	donatario_id BIGINT NOT NULL,
	nome varchar(100) NOT NULL,
	idade smallint NOT NULL,
	nivel_parentesco varchar(20) NOT NULL,
	CONSTRAINT pk_familiar PRIMARY KEY (id)
);

ALTER TABLE familiar ADD CONSTRAINT fk_familiar_donatario FOREIGN KEY (donatario_id) REFERENCES donatario(id);