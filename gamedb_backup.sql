--
-- PostgreSQL database dump
--

-- Dumped from database version 17.1
-- Dumped by pg_dump version 17.1

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

--
-- Name: update_last_session(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_last_session() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE Videojuegos
    SET last_session = (
        SELECT MAX(session_date)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_last_session() OWNER TO postgres;

--
-- Name: update_player_count(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_player_count() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE Videojuegos
    SET player_count = (
        SELECT COUNT(DISTINCT player_id)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_player_count() OWNER TO postgres;

--
-- Name: update_total_sessions(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_total_sessions() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE Videojuegos
    SET total_sessions = (
        SELECT COALESCE(SUM(session_count), 0)
        FROM Partidas
        WHERE game_id = NEW.game_id
    )
    WHERE game_id = NEW.game_id;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_total_sessions() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: jugadores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jugadores (
    player_id integer NOT NULL,
    nick_name character varying(50) NOT NULL
);


ALTER TABLE public.jugadores OWNER TO postgres;

--
-- Name: jugadores_player_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.jugadores_player_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.jugadores_player_id_seq OWNER TO postgres;

--
-- Name: jugadores_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.jugadores_player_id_seq OWNED BY public.jugadores.player_id;


--
-- Name: partidas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.partidas (
    session_id integer NOT NULL,
    game_id integer NOT NULL,
    player_id integer NOT NULL,
    experience integer DEFAULT 0,
    life_level integer DEFAULT 0,
    coins integer DEFAULT 0,
    session_count integer DEFAULT 1,
    session_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.partidas OWNER TO postgres;

--
-- Name: partidas_session_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.partidas_session_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.partidas_session_id_seq OWNER TO postgres;

--
-- Name: partidas_session_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.partidas_session_id_seq OWNED BY public.partidas.session_id;


--
-- Name: videojuegos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.videojuegos (
    game_id integer NOT NULL,
    isbn character varying(20) NOT NULL,
    title character varying(100) NOT NULL,
    player_count integer DEFAULT 0,
    total_sessions integer DEFAULT 0,
    last_session timestamp without time zone
);


ALTER TABLE public.videojuegos OWNER TO postgres;

--
-- Name: videojuegos_game_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.videojuegos_game_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.videojuegos_game_id_seq OWNER TO postgres;

--
-- Name: videojuegos_game_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.videojuegos_game_id_seq OWNED BY public.videojuegos.game_id;


--
-- Name: jugadores player_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jugadores ALTER COLUMN player_id SET DEFAULT nextval('public.jugadores_player_id_seq'::regclass);


--
-- Name: partidas session_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partidas ALTER COLUMN session_id SET DEFAULT nextval('public.partidas_session_id_seq'::regclass);


--
-- Name: videojuegos game_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.videojuegos ALTER COLUMN game_id SET DEFAULT nextval('public.videojuegos_game_id_seq'::regclass);


--
-- Data for Name: jugadores; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.jugadores (player_id, nick_name) FROM stdin;
\.


--
-- Data for Name: partidas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.partidas (session_id, game_id, player_id, experience, life_level, coins, session_count, session_date) FROM stdin;
\.


--
-- Data for Name: videojuegos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.videojuegos (game_id, isbn, title, player_count, total_sessions, last_session) FROM stdin;
\.


--
-- Name: jugadores_player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.jugadores_player_id_seq', 1, false);


--
-- Name: partidas_session_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.partidas_session_id_seq', 1, false);


--
-- Name: videojuegos_game_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.videojuegos_game_id_seq', 1, false);


--
-- Name: jugadores jugadores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jugadores
    ADD CONSTRAINT jugadores_pkey PRIMARY KEY (player_id);


--
-- Name: partidas partidas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partidas
    ADD CONSTRAINT partidas_pkey PRIMARY KEY (session_id);


--
-- Name: videojuegos videojuegos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.videojuegos
    ADD CONSTRAINT videojuegos_pkey PRIMARY KEY (game_id);


--
-- Name: partidas updatelastsession; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER updatelastsession AFTER INSERT ON public.partidas FOR EACH ROW EXECUTE FUNCTION public.update_last_session();


--
-- Name: partidas updateplayercount; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER updateplayercount AFTER INSERT ON public.partidas FOR EACH ROW EXECUTE FUNCTION public.update_player_count();


--
-- Name: partidas updatetotalsessions; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER updatetotalsessions AFTER INSERT ON public.partidas FOR EACH ROW EXECUTE FUNCTION public.update_total_sessions();


--
-- Name: partidas partidas_game_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partidas
    ADD CONSTRAINT partidas_game_id_fkey FOREIGN KEY (game_id) REFERENCES public.videojuegos(game_id) ON DELETE CASCADE;


--
-- Name: partidas partidas_player_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partidas
    ADD CONSTRAINT partidas_player_id_fkey FOREIGN KEY (player_id) REFERENCES public.jugadores(player_id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

