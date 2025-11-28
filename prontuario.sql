--
-- PostgreSQL database dump
--

\restrict q1wyL5ckzcZWKUtgb9F8ePmM1aou6MSYYQEPmVJIOg0bt1eLkJBIn6pUCSskhud

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2025-11-28 19:20:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 240 (class 1259 OID 17326)
-- Name: agenda_consulta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agenda_consulta (
    consulta_id integer NOT NULL,
    data date NOT NULL,
    hora time without time zone NOT NULL,
    paciente_id integer NOT NULL,
    medico_id integer NOT NULL,
    especialidade_id integer,
    descricao character varying(255)
);


ALTER TABLE public.agenda_consulta OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 17325)
-- Name: agenda_consulta_consulta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.agenda_consulta_consulta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.agenda_consulta_consulta_id_seq OWNER TO postgres;

--
-- TOC entry 5125 (class 0 OID 0)
-- Dependencies: 239
-- Name: agenda_consulta_consulta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.agenda_consulta_consulta_id_seq OWNED BY public.agenda_consulta.consulta_id;


--
-- TOC entry 224 (class 1259 OID 17208)
-- Name: convenio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.convenio (
    convenio_id integer NOT NULL,
    convenio_tipo character(1) NOT NULL,
    convenio_nome character varying(100) NOT NULL,
    convenio_status character(1) NOT NULL,
    convenio_area character(1),
    convenio_coparticipacao character(1)
);


ALTER TABLE public.convenio OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17207)
-- Name: convenio_convenio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.convenio_convenio_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.convenio_convenio_id_seq OWNER TO postgres;

--
-- TOC entry 5126 (class 0 OID 0)
-- Dependencies: 223
-- Name: convenio_convenio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.convenio_convenio_id_seq OWNED BY public.convenio.convenio_id;


--
-- TOC entry 226 (class 1259 OID 17219)
-- Name: especialidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.especialidade (
    especialidade_id integer NOT NULL,
    especialidade_nome character varying(50) NOT NULL,
    especialidade_status character(1) NOT NULL,
    especialidade_cbo character varying(10),
    especialidade_escala text,
    especialidade_descricao text
);


ALTER TABLE public.especialidade OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 17218)
-- Name: especialidade_especialidade_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.especialidade_especialidade_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.especialidade_especialidade_id_seq OWNER TO postgres;

--
-- TOC entry 5127 (class 0 OID 0)
-- Dependencies: 225
-- Name: especialidade_especialidade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.especialidade_especialidade_id_seq OWNED BY public.especialidade.especialidade_id;


--
-- TOC entry 236 (class 1259 OID 17285)
-- Name: exame; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exame (
    exame_id integer NOT NULL,
    exame_nome character varying(100) NOT NULL,
    exame_tipo character(1),
    exame_status character(1),
    exame_orientacao text,
    exame_duracao_minutos integer
);


ALTER TABLE public.exame OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 17296)
-- Name: exame_agendado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exame_agendado (
    exame_agendado_id integer NOT NULL,
    data date NOT NULL,
    hora time without time zone NOT NULL,
    paciente_id integer NOT NULL,
    medico_id integer NOT NULL,
    exame_id integer NOT NULL,
    observacoes text
);


ALTER TABLE public.exame_agendado OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 17295)
-- Name: exame_agendado_exame_agendado_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exame_agendado_exame_agendado_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exame_agendado_exame_agendado_id_seq OWNER TO postgres;

--
-- TOC entry 5128 (class 0 OID 0)
-- Dependencies: 237
-- Name: exame_agendado_exame_agendado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.exame_agendado_exame_agendado_id_seq OWNED BY public.exame_agendado.exame_agendado_id;


--
-- TOC entry 235 (class 1259 OID 17284)
-- Name: exame_exame_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exame_exame_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exame_exame_id_seq OWNER TO postgres;

--
-- TOC entry 5129 (class 0 OID 0)
-- Dependencies: 235
-- Name: exame_exame_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.exame_exame_id_seq OWNED BY public.exame.exame_id;


--
-- TOC entry 232 (class 1259 OID 17265)
-- Name: funcionario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcionario (
    funcionario_id integer NOT NULL,
    funcionario_nome character varying(100) NOT NULL,
    funcionario_funcao character(1),
    funcionario_cpf character varying(11),
    funcionario_status character(1),
    funcionario_email character varying(100),
    funcionario_telefone character varying(30),
    funcionario_sexo character(1),
    funcionario_dtnascimento date,
    funcionario_endereco character varying(100)
);


ALTER TABLE public.funcionario OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 17264)
-- Name: funcionario_funcionario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.funcionario_funcionario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.funcionario_funcionario_id_seq OWNER TO postgres;

--
-- TOC entry 5130 (class 0 OID 0)
-- Dependencies: 231
-- Name: funcionario_funcionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.funcionario_funcionario_id_seq OWNED BY public.funcionario.funcionario_id;


--
-- TOC entry 234 (class 1259 OID 17274)
-- Name: medicamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medicamento (
    medicamento_id integer NOT NULL,
    medicamento_nome character varying(100) NOT NULL,
    medicamento_bula text,
    medicamento_tipo character(1),
    medicamento_tarja character(1),
    medicamento_principioativo character varying(100),
    medicamento_observacao text
);


ALTER TABLE public.medicamento OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 17273)
-- Name: medicamento_medicamento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medicamento_medicamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.medicamento_medicamento_id_seq OWNER TO postgres;

--
-- TOC entry 5131 (class 0 OID 0)
-- Dependencies: 233
-- Name: medicamento_medicamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medicamento_medicamento_id_seq OWNED BY public.medicamento.medicamento_id;


--
-- TOC entry 230 (class 1259 OID 17250)
-- Name: medico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medico (
    medico_id integer NOT NULL,
    medico_nome character varying(100) NOT NULL,
    medico_crm character varying(20) NOT NULL,
    medico_cpf character varying(11),
    medico_telefone character varying(30),
    medico_email character varying(100),
    medico_dtnascimento date,
    medico_status character(1),
    medico_sexo character(1),
    medico_endereco character varying(100),
    especialidade_id integer
);


ALTER TABLE public.medico OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17249)
-- Name: medico_medico_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medico_medico_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.medico_medico_id_seq OWNER TO postgres;

--
-- TOC entry 5132 (class 0 OID 0)
-- Dependencies: 229
-- Name: medico_medico_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medico_medico_id_seq OWNED BY public.medico.medico_id;


--
-- TOC entry 228 (class 1259 OID 17231)
-- Name: paciente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.paciente (
    paciente_id integer NOT NULL,
    paciente_nome character varying(100) NOT NULL,
    paciente_telefone character varying(30),
    paciente_cpf character varying(11),
    paciente_dnas date,
    paciente_email character varying(100),
    paciente_endereco character varying(100),
    paciente_status character(1),
    paciente_alergia character varying(50),
    paciente_sexo character(1),
    paciente_nacionalidade character varying(50),
    paciente_estadocivil character(1),
    responsavel_id integer,
    convenio_id integer
);


ALTER TABLE public.paciente OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17230)
-- Name: paciente_paciente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.paciente_paciente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.paciente_paciente_id_seq OWNER TO postgres;

--
-- TOC entry 5133 (class 0 OID 0)
-- Dependencies: 227
-- Name: paciente_paciente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.paciente_paciente_id_seq OWNED BY public.paciente.paciente_id;


--
-- TOC entry 222 (class 1259 OID 17195)
-- Name: responsavel; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsavel (
    responsavel_id integer NOT NULL,
    responsavel_nome character varying(150) NOT NULL,
    responsavel_cpf character(11) NOT NULL,
    responsavel_dtnascimento date NOT NULL,
    responsavel_telefone character varying(30),
    responsavel_email character varying(100),
    responsavel_endereco text,
    responsavel_parentesco character varying(50),
    responsavel_observacoes text
);


ALTER TABLE public.responsavel OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17194)
-- Name: responsavel_responsavel_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.responsavel_responsavel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.responsavel_responsavel_id_seq OWNER TO postgres;

--
-- TOC entry 5134 (class 0 OID 0)
-- Dependencies: 221
-- Name: responsavel_responsavel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.responsavel_responsavel_id_seq OWNED BY public.responsavel.responsavel_id;


--
-- TOC entry 220 (class 1259 OID 17179)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    usuario_id integer NOT NULL,
    usuario_nome character varying(100) NOT NULL,
    usuario_login character varying(50) NOT NULL,
    usuario_senha character varying(255) NOT NULL,
    usuario_cpf character varying(11),
    usuario_email character varying(100),
    usuario_status character(1) DEFAULT 'A'::bpchar
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17178)
-- Name: usuario_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_usuario_id_seq OWNER TO postgres;

--
-- TOC entry 5135 (class 0 OID 0)
-- Dependencies: 219
-- Name: usuario_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_usuario_id_seq OWNED BY public.usuario.usuario_id;


--
-- TOC entry 4917 (class 2604 OID 17329)
-- Name: agenda_consulta consulta_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda_consulta ALTER COLUMN consulta_id SET DEFAULT nextval('public.agenda_consulta_consulta_id_seq'::regclass);


--
-- TOC entry 4909 (class 2604 OID 17211)
-- Name: convenio convenio_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.convenio ALTER COLUMN convenio_id SET DEFAULT nextval('public.convenio_convenio_id_seq'::regclass);


--
-- TOC entry 4910 (class 2604 OID 17222)
-- Name: especialidade especialidade_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.especialidade ALTER COLUMN especialidade_id SET DEFAULT nextval('public.especialidade_especialidade_id_seq'::regclass);


--
-- TOC entry 4915 (class 2604 OID 17288)
-- Name: exame exame_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exame ALTER COLUMN exame_id SET DEFAULT nextval('public.exame_exame_id_seq'::regclass);


--
-- TOC entry 4916 (class 2604 OID 17299)
-- Name: exame_agendado exame_agendado_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exame_agendado ALTER COLUMN exame_agendado_id SET DEFAULT nextval('public.exame_agendado_exame_agendado_id_seq'::regclass);


--
-- TOC entry 4913 (class 2604 OID 17268)
-- Name: funcionario funcionario_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario ALTER COLUMN funcionario_id SET DEFAULT nextval('public.funcionario_funcionario_id_seq'::regclass);


--
-- TOC entry 4914 (class 2604 OID 17277)
-- Name: medicamento medicamento_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamento ALTER COLUMN medicamento_id SET DEFAULT nextval('public.medicamento_medicamento_id_seq'::regclass);


--
-- TOC entry 4912 (class 2604 OID 17253)
-- Name: medico medico_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medico ALTER COLUMN medico_id SET DEFAULT nextval('public.medico_medico_id_seq'::regclass);


--
-- TOC entry 4911 (class 2604 OID 17234)
-- Name: paciente paciente_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente ALTER COLUMN paciente_id SET DEFAULT nextval('public.paciente_paciente_id_seq'::regclass);


--
-- TOC entry 4908 (class 2604 OID 17198)
-- Name: responsavel responsavel_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsavel ALTER COLUMN responsavel_id SET DEFAULT nextval('public.responsavel_responsavel_id_seq'::regclass);


--
-- TOC entry 4906 (class 2604 OID 17182)
-- Name: usuario usuario_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN usuario_id SET DEFAULT nextval('public.usuario_usuario_id_seq'::regclass);


--
-- TOC entry 5119 (class 0 OID 17326)
-- Dependencies: 240
-- Data for Name: agenda_consulta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agenda_consulta (consulta_id, data, hora, paciente_id, medico_id, especialidade_id, descricao) FROM stdin;
1	2025-12-01	09:00:00	3	8	\N	
2	2025-12-29	11:00:00	3	8	\N	teste
\.


--
-- TOC entry 5103 (class 0 OID 17208)
-- Dependencies: 224
-- Data for Name: convenio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.convenio (convenio_id, convenio_tipo, convenio_nome, convenio_status, convenio_area, convenio_coparticipacao) FROM stdin;
2	m	mcodmcm	a	n	n
\.


--
-- TOC entry 5105 (class 0 OID 17219)
-- Dependencies: 226
-- Data for Name: especialidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.especialidade (especialidade_id, especialidade_nome, especialidade_status, especialidade_cbo, especialidade_escala, especialidade_descricao) FROM stdin;
2	teste	i	7	xscmmcd\tmcmd	teste
3	teste2	a	33		
4	teste3	i	23		
5	teste4	a	mi		
6	Radiologista	A	83		
\.


--
-- TOC entry 5115 (class 0 OID 17285)
-- Dependencies: 236
-- Data for Name: exame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.exame (exame_id, exame_nome, exame_tipo, exame_status, exame_orientacao, exame_duracao_minutos) FROM stdin;
3	Sangue	A	A	Tirar o emaxe para teste	15
4	ultrassom	v	A		30
5	Raio-X	A	A		15
\.


--
-- TOC entry 5117 (class 0 OID 17296)
-- Dependencies: 238
-- Data for Name: exame_agendado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.exame_agendado (exame_agendado_id, data, hora, paciente_id, medico_id, exame_id, observacoes) FROM stdin;
1	2025-12-29	15:00:00	3	8	5	
\.


--
-- TOC entry 5111 (class 0 OID 17265)
-- Dependencies: 232
-- Data for Name: funcionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.funcionario (funcionario_id, funcionario_nome, funcionario_funcao, funcionario_cpf, funcionario_status, funcionario_email, funcionario_telefone, funcionario_sexo, funcionario_dtnascimento, funcionario_endereco) FROM stdin;
2	uuuuuu	u	uuuuu	i	uuuuuuu	uuuu	f	1098-02-14	uuuu
\.


--
-- TOC entry 5113 (class 0 OID 17274)
-- Dependencies: 234
-- Data for Name: medicamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medicamento (medicamento_id, medicamento_nome, medicamento_bula, medicamento_tipo, medicamento_tarja, medicamento_principioativo, medicamento_observacao) FROM stdin;
1	teste	cjncdjininc	i	p	tdha	cncncocn
\.


--
-- TOC entry 5109 (class 0 OID 17250)
-- Dependencies: 230
-- Data for Name: medico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medico (medico_id, medico_nome, medico_crm, medico_cpf, medico_telefone, medico_email, medico_dtnascimento, medico_status, medico_sexo, medico_endereco, especialidade_id) FROM stdin;
4	vnond	3	39393	3993	innein	1990-09-21	A	m	mcimc	2
5	teste	88	4839	83838	niciniwn	1998-08-10	A	F	jcje cje	3
6	teste2	99	489298	8389	nfeninwn	1999-08-16	a	m	dindn	4
7	teste R	838	8383838	838393	njdiennc	1990-08-10	A	M	didi	6
8	Oliver	13	643796	642976	dhuii	1997-04-10	A	M	ncdnconc	6
\.


--
-- TOC entry 5107 (class 0 OID 17231)
-- Dependencies: 228
-- Data for Name: paciente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.paciente (paciente_id, paciente_nome, paciente_telefone, paciente_cpf, paciente_dnas, paciente_email, paciente_endereco, paciente_status, paciente_alergia, paciente_sexo, paciente_nacionalidade, paciente_estadocivil, responsavel_id, convenio_id) FROM stdin;
1	pac1	7483	74839	1997-05-15	bbdsui	visndvinsdn	a	n	m	brasileiro	s	\N	\N
2	pac2	4738	4738	2011-10-15	ncidnin	cnidncin	a	s	f	brasileira	s	1	2
3	Gabriel	4y3947	74398798	2001-05-15	hfuhf	ofohf	A	N	M	brasileira	S	\N	\N
\.


--
-- TOC entry 5101 (class 0 OID 17195)
-- Dependencies: 222
-- Data for Name: responsavel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.responsavel (responsavel_id, responsavel_nome, responsavel_cpf, responsavel_dtnascimento, responsavel_telefone, responsavel_email, responsavel_endereco, responsavel_parentesco, responsavel_observacoes) FROM stdin;
1	nieem	9191       	2002-12-01	15161	dsssc	cdcc	mae	
\.


--
-- TOC entry 5099 (class 0 OID 17179)
-- Dependencies: 220
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (usuario_id, usuario_nome, usuario_login, usuario_senha, usuario_cpf, usuario_email, usuario_status) FROM stdin;
1	Administrador	admin	123	\N	\N	A
\.


--
-- TOC entry 5136 (class 0 OID 0)
-- Dependencies: 239
-- Name: agenda_consulta_consulta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.agenda_consulta_consulta_id_seq', 2, true);


--
-- TOC entry 5137 (class 0 OID 0)
-- Dependencies: 223
-- Name: convenio_convenio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.convenio_convenio_id_seq', 2, true);


--
-- TOC entry 5138 (class 0 OID 0)
-- Dependencies: 225
-- Name: especialidade_especialidade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.especialidade_especialidade_id_seq', 6, true);


--
-- TOC entry 5139 (class 0 OID 0)
-- Dependencies: 237
-- Name: exame_agendado_exame_agendado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.exame_agendado_exame_agendado_id_seq', 1, true);


--
-- TOC entry 5140 (class 0 OID 0)
-- Dependencies: 235
-- Name: exame_exame_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.exame_exame_id_seq', 5, true);


--
-- TOC entry 5141 (class 0 OID 0)
-- Dependencies: 231
-- Name: funcionario_funcionario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcionario_funcionario_id_seq', 2, true);


--
-- TOC entry 5142 (class 0 OID 0)
-- Dependencies: 233
-- Name: medicamento_medicamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicamento_medicamento_id_seq', 2, true);


--
-- TOC entry 5143 (class 0 OID 0)
-- Dependencies: 229
-- Name: medico_medico_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medico_medico_id_seq', 8, true);


--
-- TOC entry 5144 (class 0 OID 0)
-- Dependencies: 227
-- Name: paciente_paciente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.paciente_paciente_id_seq', 3, true);


--
-- TOC entry 5145 (class 0 OID 0)
-- Dependencies: 221
-- Name: responsavel_responsavel_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.responsavel_responsavel_id_seq', 2, true);


--
-- TOC entry 5146 (class 0 OID 0)
-- Dependencies: 219
-- Name: usuario_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_usuario_id_seq', 1, true);


--
-- TOC entry 4941 (class 2606 OID 17336)
-- Name: agenda_consulta agenda_consulta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda_consulta
    ADD CONSTRAINT agenda_consulta_pkey PRIMARY KEY (consulta_id);


--
-- TOC entry 4925 (class 2606 OID 17217)
-- Name: convenio convenio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.convenio
    ADD CONSTRAINT convenio_pkey PRIMARY KEY (convenio_id);


--
-- TOC entry 4927 (class 2606 OID 17229)
-- Name: especialidade especialidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.especialidade
    ADD CONSTRAINT especialidade_pkey PRIMARY KEY (especialidade_id);


--
-- TOC entry 4939 (class 2606 OID 17309)
-- Name: exame_agendado exame_agendado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exame_agendado
    ADD CONSTRAINT exame_agendado_pkey PRIMARY KEY (exame_agendado_id);


--
-- TOC entry 4937 (class 2606 OID 17294)
-- Name: exame exame_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exame
    ADD CONSTRAINT exame_pkey PRIMARY KEY (exame_id);


--
-- TOC entry 4933 (class 2606 OID 17272)
-- Name: funcionario funcionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (funcionario_id);


--
-- TOC entry 4935 (class 2606 OID 17283)
-- Name: medicamento medicamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamento
    ADD CONSTRAINT medicamento_pkey PRIMARY KEY (medicamento_id);


--
-- TOC entry 4931 (class 2606 OID 17258)
-- Name: medico medico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT medico_pkey PRIMARY KEY (medico_id);


--
-- TOC entry 4929 (class 2606 OID 17238)
-- Name: paciente paciente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (paciente_id);


--
-- TOC entry 4923 (class 2606 OID 17206)
-- Name: responsavel responsavel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsavel
    ADD CONSTRAINT responsavel_pkey PRIMARY KEY (responsavel_id);


--
-- TOC entry 4919 (class 2606 OID 17191)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id);


--
-- TOC entry 4921 (class 2606 OID 17193)
-- Name: usuario usuario_usuario_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_login_key UNIQUE (usuario_login);


--
-- TOC entry 4948 (class 2606 OID 17347)
-- Name: agenda_consulta fk_consulta_especialidade; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda_consulta
    ADD CONSTRAINT fk_consulta_especialidade FOREIGN KEY (especialidade_id) REFERENCES public.especialidade(especialidade_id);


--
-- TOC entry 4949 (class 2606 OID 17342)
-- Name: agenda_consulta fk_consulta_medico; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda_consulta
    ADD CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_id) REFERENCES public.medico(medico_id);


--
-- TOC entry 4950 (class 2606 OID 17337)
-- Name: agenda_consulta fk_consulta_paciente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda_consulta
    ADD CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES public.paciente(paciente_id);


--
-- TOC entry 4945 (class 2606 OID 17320)
-- Name: exame_agendado fk_exameag_exame; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exame_agendado
    ADD CONSTRAINT fk_exameag_exame FOREIGN KEY (exame_id) REFERENCES public.exame(exame_id);


--
-- TOC entry 4946 (class 2606 OID 17315)
-- Name: exame_agendado fk_exameag_medico; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exame_agendado
    ADD CONSTRAINT fk_exameag_medico FOREIGN KEY (medico_id) REFERENCES public.medico(medico_id);


--
-- TOC entry 4947 (class 2606 OID 17310)
-- Name: exame_agendado fk_exameag_paciente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exame_agendado
    ADD CONSTRAINT fk_exameag_paciente FOREIGN KEY (paciente_id) REFERENCES public.paciente(paciente_id);


--
-- TOC entry 4944 (class 2606 OID 17259)
-- Name: medico fk_medico_especialidade; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT fk_medico_especialidade FOREIGN KEY (especialidade_id) REFERENCES public.especialidade(especialidade_id);


--
-- TOC entry 4942 (class 2606 OID 17244)
-- Name: paciente fk_paciente_convenio; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT fk_paciente_convenio FOREIGN KEY (convenio_id) REFERENCES public.convenio(convenio_id);


--
-- TOC entry 4943 (class 2606 OID 17239)
-- Name: paciente fk_paciente_responsavel; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT fk_paciente_responsavel FOREIGN KEY (responsavel_id) REFERENCES public.responsavel(responsavel_id);


-- Completed on 2025-11-28 19:20:35

--
-- PostgreSQL database dump complete
--

\unrestrict q1wyL5ckzcZWKUtgb9F8ePmM1aou6MSYYQEPmVJIOg0bt1eLkJBIn6pUCSskhud

