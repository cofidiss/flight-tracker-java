--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

-- Started on 2026-07-25 17:39:02

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 42206)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id bigint NOT NULL,
    user_name character varying(255),
    password character varying(255),
    role_id integer NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 42205)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."user" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3306 (class 0 OID 42206)
-- Dependencies: 210
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (id, user_name, password, role_id) FROM stdin;
6	a	H0D8ktokFpR1CXnubPWC8tXX0o4YM13gWrxU0FYOD1MChgxlK/CNVgJSql50IQVG82n7u86MEs/HlXsmUv6adQ==	1
7	string	J1fLPK/DmvRRq7Jpe+ebSrYdY9dNhbBBhinejCaBG1KfPzeA0BUAY/9Vor7udMTsECoqJzGh8ffxDUc60Ypqhw==	0
8	asd2	5U7n4oX7sCdSeRQ6vExVTlMU57QX7KyDpZhKlk+suq1ohmooQcPoPd8SWimFVmJhxAFPn5YOxgJTrrzalROptA==	1
9	1234	PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==	1
10	333	XjFVd0052XxfnhfBCMKz4EhaQ64069GW9hpvi/cy73GknlcQWUz8c5HbEU7fmfXaPtlu8dbKXlmOhfkb1B5+6w==	1
11	asd	5U7n4oX7sCdSeRQ6vExVTlMU57QX7KyDpZhKlk+suq1ohmooQcPoPd8SWimFVmJhxAFPn5YOxgJTrrzalROptA==	1
12	ee	LJ+7PyicKLzsdS8CHTbDoUuGFAvYKUKwbAg4kxzlmZwf0LKXub2ySqFF/AJEt/OCTIONJv98IirXZj3fssBmtA==	2
13	ad	0xnksVEg90L9MhTzulPgE0wO+/8xEl9Tn30Lg3PWgwNuuU7Byadcgqj+fsgQiANhhmPDXQCzA6Jse4HTNNNkug==	1
14	p	kphyg4y5z+ZXjhHwoyNDiu5a5/YdQUEtYttysl2sUgGd4tajVestAzM2+3DnPw7Ar+yj7zbdipDYP5mP7iO3jQ==	3
15	p3	kphyg4y5z+ZXjhHwoyNDiu5a5/YdQUEtYttysl2sUgGd4tajVestAzM2+3DnPw7Ar+yj7zbdipDYP5mP7iO3jQ==	3
16	p	kphyg4y5z+ZXjhHwoyNDiu5a5/YdQUEtYttysl2sUgGd4tajVestAzM2+3DnPw7Ar+yj7zbdipDYP5mP7iO3jQ==	3
17	pp	kphyg4y5z+ZXjhHwoyNDiu5a5/YdQUEtYttysl2sUgGd4tajVestAzM2+3DnPw7Ar+yj7zbdipDYP5mP7iO3jQ==	3
18	p0	/r98cZ+GgQ9sIboxHyt1CwP4pfyTqhXCaDH++rimGz6BZVji3Tm6DNZdzVyNHFhx5yKlkzUiGo7tFpkihrBGhA==	3
19	p7	OSqwZ+dkYrG7tPVrS4Tp8KGmW+mHTxqtm27kcOO6he4DGi6/P0BqdMjHrFf0nyAk9EmGymql4Bd8sPSmn1BLyw==	3
20	pğ	bOt4ufeBDE3bg5QoaMf0OKFghjvDBY523smzU6QmpmmqSGkqaGqfDhffUMoDYagGHLpm6yOCr+y40CA41p2DuQ==	3
21	1.	Tf9Oo0DwqCPxXT9PAati6uDl2lecy4Ufjbnf6ExYsrN7iZA6dA4e4XLaeTpuedVg5ff5vQWKEqKAQz7W+kZRCg==	1
22	rr	LnptpCzzFxREpkgkcAIhv1DGkmvTNOQDtU2O8FZF6+wQ7rUcIrzyyVDgCj0leX185poiN9TVxNYMlZN1gq9mbQ==	3
23	ee	LJ+7PyicKLzsdS8CHTbDoUuGFAvYKUKwbAg4kxzlmZwf0LKXub2ySqFF/AJEt/OCTIONJv98IirXZj3fssBmtA==	3
24	eer	2KXF0NS5cMvRxIrTcrDaz85s8VWyYF5IRok0yMoMVT7/Gq4pegMWxr3u2/IFC4s8n744ENw8KNMpBBNvaHZwbQ==	3
25	34	5Tl/FMRPjfdUYXGUBR2rGtOPWfCFgEBsLv1ZqkwPcWFnE8Kr52vFA+CPL17aSGNjT2/pmtOdRslHwJYjuR5Tyg==	3
26	33	MWOo1qRUDs8XlOzgJF8pEVTTDhCANZ0umU73nBpGmqDNgIdp2cfuMMo0LGgD0uvOw+txqSjW2xh9+x/Cz2QDlQ==	2
27	22	atJ10mwgDoFTTZmWGDyHSN36vHsKARqQ9GMBYm1wmSNHRwPKyrD/i2fNhGtstVsjo5sD+9+1IY7sM3PPcBChZg==	2
28	guest	sODsf6ColXfJNBwWz/hweJIhsxCgLMRl9GR4lAf4Pzd6h6l9Y1ysJmYUeo+1/SfVbeo9TOuh/H0C9CLdpnlOPA==	1
29	premium	YnRx2RS/7XoG4EDaskXTQprszgAncpw0DxFxA9I9ss7SMnHI0PZaQzsJz38vYyVTPfaA2iY5Ewl01jMPxwat7A==	3
\.


--
-- TOC entry 3312 (class 0 OID 0)
-- Dependencies: 209
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 29, true);


--
-- TOC entry 3165 (class 2606 OID 42212)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


-- Completed on 2026-07-25 17:39:02

--
-- PostgreSQL database dump complete
--

