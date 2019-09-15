CREATE TABLE bot.users (
	userid varchar NOT NULL, -- Идентификатор пользователя
	chatid varchar NOT NULL, -- Идентификатор чата
	"name" varchar NOT NULL -- Имя пользователя
);

CREATE UNIQUE INDEX users_userid_idx ON bot.users USING btree (userid);

COMMENT ON COLUMN bot.users.userid IS 'Идентификатор пользователя';
COMMENT ON COLUMN bot.users.chatid IS 'Идентификатор чата';
COMMENT ON COLUMN bot.users."name" IS 'Имя пользователя';

