-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION pg_database_owner;

COMMENT ON SCHEMA public IS 'standard public schema';
-- public.hobby definition

-- Drop table

-- DROP TABLE public.hobby;

CREATE TABLE public.hobby (
	id int4 NOT NULL,
	"name" varchar NULL,
	CONSTRAINT hobby_pkey PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE public.hobby OWNER TO myuser;
GRANT ALL ON TABLE public.hobby TO myuser;


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id int4 NOT NULL,
	username varchar NOT NULL,
	email varchar NOT NULL,
	"fireBaseId" uuid NOT NULL,
	created_at timestamp NULL,
	CONSTRAINT users_email_key UNIQUE (email),
	CONSTRAINT "users_fireBaseId_key" UNIQUE ("fireBaseId"),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_username_key UNIQUE (username)
);

-- Permissions

ALTER TABLE public.users OWNER TO myuser;
GRANT ALL ON TABLE public.users TO myuser;


-- public.matches definition

-- Drop table

-- DROP TABLE public.matches;

CREATE TABLE public.matches (
	id int4 NOT NULL,
	user1_id int4 NOT NULL,
	user2_id int4 NOT NULL,
	created_at timestamp NULL,
	CONSTRAINT matches_pkey PRIMARY KEY (id),
	CONSTRAINT matches_user1_id_fkey FOREIGN KEY (user1_id) REFERENCES public.users(id),
	CONSTRAINT matches_user2_id_fkey FOREIGN KEY (user2_id) REFERENCES public.users(id)
);

-- Permissions

ALTER TABLE public.matches OWNER TO myuser;
GRANT ALL ON TABLE public.matches TO myuser;


-- public.messages definition

-- Drop table

-- DROP TABLE public.messages;

CREATE TABLE public.messages (
	id int4 NOT NULL,
	match_id int4 NOT NULL,
	sender_id int4 NOT NULL,
	receiver_id int4 NOT NULL,
	message text NULL,
	created_at timestamp NULL,
	CONSTRAINT messages_pkey PRIMARY KEY (id),
	CONSTRAINT messages_match_id_fkey FOREIGN KEY (match_id) REFERENCES public.matches(id),
	CONSTRAINT messages_receiver_id_fkey FOREIGN KEY (receiver_id) REFERENCES public.users(id),
	CONSTRAINT messages_sender_id_fkey FOREIGN KEY (sender_id) REFERENCES public.users(id)
);

-- Permissions

ALTER TABLE public.messages OWNER TO myuser;
GRANT ALL ON TABLE public.messages TO myuser;


-- public.notifications definition

-- Drop table

-- DROP TABLE public.notifications;

CREATE TABLE public.notifications (
	id int4 NOT NULL,
	user_id int4 NOT NULL,
	"type" varchar NULL, -- match / message
	message text NULL,
	created_at timestamp NULL,
	CONSTRAINT notifications_pkey PRIMARY KEY (id),
	CONSTRAINT notifications_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

-- Column comments

COMMENT ON COLUMN public.notifications."type" IS 'match / message';

-- Permissions

ALTER TABLE public.notifications OWNER TO myuser;
GRANT ALL ON TABLE public.notifications TO myuser;


-- public.preferences definition

-- Drop table

-- DROP TABLE public.preferences;

CREATE TABLE public.preferences (
	id int4 NOT NULL,
	user_id int4 NOT NULL,
	interested_gender varchar NULL, -- male/ female/both
	max_distance int4 NULL,
	min_age int4 NULL,
	max_age int4 NULL,
	dating_purpose varchar NULL, -- serious/casual/friend/fwb
	CONSTRAINT preferences_pkey PRIMARY KEY (id),
	CONSTRAINT preferences_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

-- Column comments

COMMENT ON COLUMN public.preferences.interested_gender IS 'male/ female/both';
COMMENT ON COLUMN public.preferences.dating_purpose IS 'serious/casual/friend/fwb';

-- Permissions

ALTER TABLE public.preferences OWNER TO myuser;
GRANT ALL ON TABLE public.preferences TO myuser;


-- public.profiles definition

-- Drop table

-- DROP TABLE public.profiles;

CREATE TABLE public.profiles (
	user_id int4 NOT NULL,
	full_name varchar NULL,
	age int4 NULL,
	bio text NULL,
	gender varchar NULL,
	job varchar NULL,
	"location" varchar NULL,
	avatar_url varchar NULL,
	birth_date date NULL,
	created_at timestamp NULL,
	CONSTRAINT profiles_pkey PRIMARY KEY (user_id),
	CONSTRAINT profiles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

-- Permissions

ALTER TABLE public.profiles OWNER TO myuser;
GRANT ALL ON TABLE public.profiles TO myuser;


-- public.swipes definition

-- Drop table

-- DROP TABLE public.swipes;

CREATE TABLE public.swipes (
	id int4 NOT NULL,
	swiper_id int4 NOT NULL,
	swiped_id int4 NOT NULL,
	status varchar NULL, -- liked / disliked / superliked
	created_at timestamp NULL,
	CONSTRAINT swipes_pkey PRIMARY KEY (id),
	CONSTRAINT swipes_swiped_id_fkey FOREIGN KEY (swiped_id) REFERENCES public.users(id),
	CONSTRAINT swipes_swiper_id_fkey FOREIGN KEY (swiper_id) REFERENCES public.users(id)
);

-- Column comments

COMMENT ON COLUMN public.swipes.status IS 'liked / disliked / superliked';

-- Permissions

ALTER TABLE public.swipes OWNER TO myuser;
GRANT ALL ON TABLE public.swipes TO myuser;


-- public.user_hobby definition

-- Drop table

-- DROP TABLE public.user_hobby;

CREATE TABLE public.user_hobby (
	user_id int4 NOT NULL,
	hobby_id int4 NOT NULL,
	CONSTRAINT user_hobby_pkey PRIMARY KEY (user_id, hobby_id),
	CONSTRAINT user_hobby_hobby_id_fkey FOREIGN KEY (hobby_id) REFERENCES public.hobby(id),
	CONSTRAINT user_hobby_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

-- Permissions

ALTER TABLE public.user_hobby OWNER TO myuser;
GRANT ALL ON TABLE public.user_hobby TO myuser;




-- Permissions

GRANT ALL ON SCHEMA public TO pg_database_owner;
GRANT USAGE ON SCHEMA public TO public;