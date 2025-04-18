-- Không cần tạo lại schema public vì nó đã tồn tại mặc định
-- CREATE SCHEMA public AUTHORIZATION pg_database_owner;

-- Chỉ tạo bảng và dữ liệu

-- Bật extension pgcrypto để dùng gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS pgcrypto;
-- Bảng hobby
CREATE TABLE IF NOT EXISTS public.hobby (
  id SERIAL PRIMARY KEY,
  "name" varchar UNIQUE NOT NULL
);

-- Bảng users
CREATE TABLE IF NOT EXISTS public.users (
  id SERIAL PRIMARY KEY,
  username varchar NOT NULL,
  email varchar NOT NULL,
  "fireBaseId" uuid ,
  created_at timestamp NULL,
  CONSTRAINT users_email_key UNIQUE (email),
  CONSTRAINT "users_fireBaseId_key" UNIQUE ("fireBaseId"),
  CONSTRAINT users_username_key UNIQUE (username)
);


-- Bảng matches
CREATE TABLE IF NOT EXISTS public.matches (
  id int4 NOT NULL,
  user1_id int4 NOT NULL,
  user2_id int4 NOT NULL,
  created_at timestamp NULL,
  CONSTRAINT matches_pkey PRIMARY KEY (id),
  CONSTRAINT matches_user1_id_fkey FOREIGN KEY (user1_id) REFERENCES public.users(id),
  CONSTRAINT matches_user2_id_fkey FOREIGN KEY (user2_id) REFERENCES public.users(id)
);

-- Bảng messages
CREATE TABLE IF NOT EXISTS public.messages (
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

-- Bảng notifications
CREATE TABLE IF NOT EXISTS public.notifications (
  id int4 NOT NULL,
  user_id int4 NOT NULL,
  "type" varchar NULL, -- match / message
  message text NULL,
  created_at timestamp NULL,
  CONSTRAINT notifications_pkey PRIMARY KEY (id),
  CONSTRAINT notifications_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

-- Bảng preferences
CREATE TABLE IF NOT EXISTS public.preferences (
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

-- Bảng profiles
CREATE TABLE IF NOT EXISTS public.profiles (
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

-- Bảng swipes
CREATE TABLE IF NOT EXISTS public.swipes (
  id int4 NOT NULL,
  swiper_id int4 NOT NULL,
  swiped_id int4 NOT NULL,
  status varchar NULL, -- liked / disliked / superliked
  created_at timestamp NULL,
  CONSTRAINT swipes_pkey PRIMARY KEY (id),
  CONSTRAINT swipes_swiped_id_fkey FOREIGN KEY (swiped_id) REFERENCES public.users(id),
  CONSTRAINT swipes_swiper_id_fkey FOREIGN KEY (swiper_id) REFERENCES public.users(id)
);

-- Bảng user_hobby
CREATE TABLE IF NOT EXISTS public.user_hobby (
  user_id int4 NOT NULL,
  hobby_id int4 NOT NULL,
  CONSTRAINT user_hobby_pkey PRIMARY KEY (user_id, hobby_id),
  CONSTRAINT user_hobby_hobby_id_fkey FOREIGN KEY (hobby_id) REFERENCES public.hobby(id),
  CONSTRAINT user_hobby_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

-- Phân quyền cho các bảng
ALTER TABLE public.hobby OWNER TO myuser;
ALTER TABLE public.users OWNER TO myuser;
ALTER TABLE public.matches OWNER TO myuser;
ALTER TABLE public.messages OWNER TO myuser;
ALTER TABLE public.notifications OWNER TO myuser;
ALTER TABLE public.preferences OWNER TO myuser;
ALTER TABLE public.profiles OWNER TO myuser;
ALTER TABLE public.swipes OWNER TO myuser;
ALTER TABLE public.user_hobby OWNER TO myuser;

GRANT ALL ON TABLE public.hobby TO myuser;
GRANT ALL ON TABLE public.users TO myuser;
GRANT ALL ON TABLE public.matches TO myuser;
GRANT ALL ON TABLE public.messages TO myuser;
GRANT ALL ON TABLE public.notifications TO myuser;
GRANT ALL ON TABLE public.preferences TO myuser;
GRANT ALL ON TABLE public.profiles TO myuser;
GRANT ALL ON TABLE public.swipes TO myuser;
GRANT ALL ON TABLE public.user_hobby TO myuser;

-- Phân quyền cho schema public
GRANT ALL ON SCHEMA public TO pg_database_owner;
GRANT USAGE ON SCHEMA public TO public;
