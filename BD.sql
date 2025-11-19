-- Criação das Tabelas

CREATE TABLE public.usuario (
    usuario_id integer NOT NULL,
    usuario_nome character varying,
    usuario_cpf integer,
    usuario_email character varying,
    usuario_senha character varying,
    usuario_status character(1)
);

CREATE TABLE public.paciente (
    paciente_id SERIAL NOT NULL,
    paciente_nome character varying(100),
    paciente_telefone character varying(30),
    paciente_cpf character varying(11),
    paciente_dnas date,
    paciente_email character varying(30),
    paciente_endereco character varying(50),
    paciente_status character(1),
    paciente_alergia character varying(30),
    paciente_sexo character(1),
    paciente_nacionalidade character varying(30),
    paciente_estadocivil character(1)
);

CREATE TABLE public.medico (
    medico_id integer NOT NULL,
    medico_nome character varying(50),
    medico_crm integer,
    medico_cpf character varying(11),
    medico_telefone integer,
    medico_email character varying,
    medico_dtnascimento date,
    medico_status character(1),
    medico_sexo character(1),
    medico_endereco character varying(50)
);

CREATE TABLE public.responsavel (
    responsavel_id integer NOT NULL,
    responsavel_nome character varying(150),
    responsavel_cpf character(11),
    responsavel_dtnascimento date,
    responsavel_telefone character varying,
    responsavel_email character varying(100),
    responsavel_endereco text,
    responsavel_parentesco character varying(50),
    responsavel_observacoes text
);

CREATE TABLE public.funcionario (
    funcionario_id integer NOT NULL,
    funcionario_nome character varying(100),
    funcionario_funcao character(1),
    funcionario_cpf character varying(11),
    funcionario_status character(1),
    funcionario_email character varying(50),
    funcionario_telefone character varying(15),
    funcionario_sexo character(1),
    funcionario_dtnascimento date,
    funcionario_endereco character varying(50)
);

CREATE TABLE public.medicamento (
    medicamento_id integer NOT NULL,
    medicamento_nome character varying(50),
    medicamento_bula text,
    medicamento_tipo character(1),
    medicamento_tarja character(1),
    medicamento_principioativo character varying(100),
    medicamento_observacao text
);

CREATE TABLE public.especialidade (
    especialidade_id integer NOT NULL,
    especialidade_nome character varying(50),
    especialidade_status character(1),
    especialidade_cbo character varying(10),
    especialidade_escala text,
    especialidade_descricao text
);

CREATE TABLE public.exame (
    exame_id integer NOT NULL,
    exame_nome character varying(50),
    exame_tipo character(1),
    exame_status character(1),
    exame_orientacao text,
    exame_horai time without time zone,
    exame_horaf time without time zone
);

CREATE TABLE public.convenio (
    convenio_id integer NOT NULL,
    convenio_tipo character(1),
    convenio_nome character varying,
    convenio_status character(1),
    convenio_area character(1),
    convenio_coparticipacao character(1)
);

---

-- Definição das Chaves Primárias

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id);

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (paciente_id);

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT medico_pkey PRIMARY KEY (medico_id);

ALTER TABLE ONLY public.responsavel
    ADD CONSTRAINT responsavel_pkey PRIMARY KEY (responsavel_id);

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (funcionario_id);

ALTER TABLE ONLY public.medicamento
    ADD CONSTRAINT medicamento_pkey PRIMARY KEY (medicamento_id);

ALTER TABLE ONLY public.especialidade
    ADD CONSTRAINT especialidade_pkey PRIMARY KEY (especialidade_id);

ALTER TABLE ONLY public.exame
    ADD CONSTRAINT exame_pkey PRIMARY KEY (exame_id);

ALTER TABLE ONLY public.convenio
    ADD CONSTRAINT convenio_pkey PRIMARY KEY (convenio_id);