CREATE TABLE bot.users (
	userid varchar NOT NULL, -- Идентификатор пользователя
	chatid varchar NOT NULL, -- Идентификатор чата
	"name" varchar NOT NULL -- Имя пользователя
);

COMMENT ON COLUMN bot.users.userid IS 'Идентификатор пользователя';
COMMENT ON COLUMN bot.users.chatid IS 'Идентификатор чата';
COMMENT ON COLUMN bot.users."name" IS 'Имя пользователя';

ALTER TABLE bot.users OWNER TO tgdunstfdmbhbu;
GRANT ALL ON TABLE bot.users TO tgdunstfdmbhbu;
