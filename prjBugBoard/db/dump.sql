--
-- PostgreSQL database dump
--

-- Dumped from database version 12.11
-- Dumped by pg_dump version 12.11

-- Started on 2026-03-20 23:27:54

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--




ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2854 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 207 (class 1259 OID 37054)
-- Name: issue_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.issue_history (
    id integer NOT NULL,
    issue_id integer,
    field_name character varying(50),
    old_value text,
    new_value text,
    changed_by character varying(255),
    changed_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.issue_history OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 37052)
-- Name: issue_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.issue_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.issue_history_id_seq OWNER TO postgres;

--
-- TOC entry 2855 (class 0 OID 0)
-- Dependencies: 206
-- Name: issue_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.issue_history_id_seq OWNED BY public.issue_history.id;


--
-- TOC entry 205 (class 1259 OID 36988)
-- Name: issues; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.issues (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    description text NOT NULL,
    type character varying(50) NOT NULL,
    priority character varying(20),
    attachment bytea,
    status character varying(20) DEFAULT 'todo'::character varying,
    created_by character varying(50),
    assigned_to character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.issues OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 36986)
-- Name: issues_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.issues_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.issues_id_seq OWNER TO postgres;

--
-- TOC entry 2856 (class 0 OID 0)
-- Dependencies: 204
-- Name: issues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.issues_id_seq OWNED BY public.issues.id;


--
-- TOC entry 203 (class 1259 OID 36977)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(100) NOT NULL,
    password_hash character varying(100) NOT NULL,
    role character varying(20) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 36975)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 2857 (class 0 OID 0)
-- Dependencies: 202
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 2707 (class 2604 OID 37057)
-- Name: issue_history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue_history ALTER COLUMN id SET DEFAULT nextval('public.issue_history_id_seq'::regclass);


--
-- TOC entry 2703 (class 2604 OID 36991)
-- Name: issues id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issues ALTER COLUMN id SET DEFAULT nextval('public.issues_id_seq'::regclass);


--
-- TOC entry 2701 (class 2604 OID 36980)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 2848 (class 0 OID 37054)
-- Dependencies: 207
-- Data for Name: issue_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.issue_history VALUES (11, 18, 'Status', '', 'todo', 'admin1@bb26.com', '2026-03-17 09:28:20.007263');
INSERT INTO public.issue_history VALUES (16, 19, 'Status', '', 'todo', 'luigi@bb26.com', '2026-03-17 17:53:04.71968');
INSERT INTO public.issue_history VALUES (17, 20, 'Status', '', 'todo', 'anna@bb26.com', '2026-03-17 18:12:10.874623');
INSERT INTO public.issue_history VALUES (35, 21, 'Status', '', 'todo', 'admin1@bb26.com', '2026-03-18 22:15:23.26409');
INSERT INTO public.issue_history VALUES (38, 22, 'Status', '', 'todo', 'admin1@bb26.com', '2026-03-18 22:17:21.957215');
INSERT INTO public.issue_history VALUES (39, 23, 'Status', '', 'todo', 'admin1@bb26.com', '2026-03-18 22:18:12.295631');
INSERT INTO public.issue_history VALUES (40, 24, 'Status', '', 'todo', 'admin1@bb26.com', '2026-03-18 22:19:10.093785');
INSERT INTO public.issue_history VALUES (56, 18, 'status', 'todo', 'done', 'admin1@bb26.com', '2026-03-18 23:30:38.276427');
INSERT INTO public.issue_history VALUES (57, 18, 'ultimo commento', NULL, 'Risolto. Sono intervenuto sulla classe che verifica il token', 'admin1@bb26.com', '2026-03-18 23:30:38.959911');


--
-- TOC entry 2846 (class 0 OID 36988)
-- Dependencies: 205
-- Data for Name: issues; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.issues VALUES (19, 'Implementazione grafica delle statistiche', 'Alcuni clienti hanno richiesto un''implementazione grafica delle modifiche', 'FEATURE', 'MEDIUM', NULL, 'todo', 'luigi@bb26.com', NULL, '2026-03-17 17:53:04.43183', '2026-03-17 17:53:04.43183');
INSERT INTO public.issues VALUES (21, 'Errore caricamento immagini', 'Upload immagini fallisce con file grandi', 'BUG', 'HIGH', NULL, 'todo', 'admin1@bb26.com', 'paolo@bb26.com', '2026-03-18 22:15:22.274827', '2026-03-18 22:15:22.274827');
INSERT INTO public.issues VALUES (22, 'Menù non visibile', 'Il menu laterale non appare su mobile', 'BUG', 'HIGH', NULL, 'todo', 'admin1@bb26.com', 'mario@bb26.com', '2026-03-18 22:17:21.194021', '2026-03-18 22:17:21.194021');
INSERT INTO public.issues VALUES (23, 'Problema filtro issue', 'Il filtro status non funziona correttamente', 'BUG', 'LOW', NULL, 'todo', 'admin1@bb26.com', 'anna@bb26.com', '2026-03-18 22:18:11.570264', '2026-03-18 22:18:11.570264');
INSERT INTO public.issues VALUES (24, 'Errore assegazione bug', 'Non è possibile assegnare un bug ad un utente', 'BUG', 'HIGH', NULL, 'todo', 'admin1@bb26.com', 'paolo@bb26.com', '2026-03-18 22:19:09.165866', '2026-03-18 22:19:09.165866');
INSERT INTO public.issues VALUES (20, 'Pagina dashboard lenta', 'La dashboard impiega troppo tempo per caricarsi', 'BUG', 'LOW', NULL, 'todo', 'anna@bb26.com', 'anna@bb26.com', '2026-03-17 18:12:10.536867', '2026-03-18 23:18:56.377206');
INSERT INTO public.issues VALUES (18, 'Errore Login', 'La login mostra errore 500 quando si prova ad accedere all''applicazione.', 'BUG', 'HIGH', NULL, 'done', 'admin1@bb26.com', 'luigi@bb26.com', '2026-03-17 09:28:19.72909', '2026-03-18 23:30:39.65076');


--
-- TOC entry 2844 (class 0 OID 36977)
-- Dependencies: 203
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (5, 'admin1@bb26.com', '$2a$12$BAzGPD50DnYIHFLtOt05mO4jP8Jgi.zF8r5B6tbIm.v4JZ.YDiVT2', 'ADMIN', '2026-03-17 00:13:46.401025');
INSERT INTO public.users VALUES (6, 'admin2@bb26.com', '$2a$12$BAzGPD50DnYIHFLtOt05mO4jP8Jgi.zF8r5B6tbIm.v4JZ.YDiVT2', 'ADMIN', '2026-03-17 00:13:46.401025');
INSERT INTO public.users VALUES (7, 'mario@bb26.com', '$2a$12$x9hBrw6vQjxW1tdIUUKo0uYTz6HRi9cg8EmTdeJNrETBwiWHAltga', 'USER', '2026-03-17 00:13:46.401025');
INSERT INTO public.users VALUES (8, 'anna@bb26.com', '$2a$12$x9hBrw6vQjxW1tdIUUKo0uYTz6HRi9cg8EmTdeJNrETBwiWHAltga', 'USER', '2026-03-17 00:13:46.401025');
INSERT INTO public.users VALUES (9, 'luigi@bb26.com', '$2a$12$x9hBrw6vQjxW1tdIUUKo0uYTz6HRi9cg8EmTdeJNrETBwiWHAltga', 'USER', '2026-03-17 00:13:46.401025');
INSERT INTO public.users VALUES (10, 'sara@bb26.com', '$2a$12$x9hBrw6vQjxW1tdIUUKo0uYTz6HRi9cg8EmTdeJNrETBwiWHAltga', 'USER', '2026-03-17 00:13:46.401025');
INSERT INTO public.users VALUES (11, 'paolo@bb26.com', '$2a$12$x9hBrw6vQjxW1tdIUUKo0uYTz6HRi9cg8EmTdeJNrETBwiWHAltga', 'USER', '2026-03-17 00:13:46.401025');


--
-- TOC entry 2858 (class 0 OID 0)
-- Dependencies: 206
-- Name: issue_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.issue_history_id_seq', 57, true);


--
-- TOC entry 2859 (class 0 OID 0)
-- Dependencies: 204
-- Name: issues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.issues_id_seq', 24, true);


--
-- TOC entry 2860 (class 0 OID 0)
-- Dependencies: 202
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 11, true);


--
-- TOC entry 2716 (class 2606 OID 37063)
-- Name: issue_history issue_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue_history
    ADD CONSTRAINT issue_history_pkey PRIMARY KEY (id);


--
-- TOC entry 2714 (class 2606 OID 36999)
-- Name: issues issues_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issues
    ADD CONSTRAINT issues_pkey PRIMARY KEY (id);


--
-- TOC entry 2710 (class 2606 OID 36985)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 2712 (class 2606 OID 36983)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


-- Completed on 2026-03-20 23:27:55

--
-- PostgreSQL database dump complete
--

