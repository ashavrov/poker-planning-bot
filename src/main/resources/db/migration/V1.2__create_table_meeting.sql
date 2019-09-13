CREATE TABLE bot.meetings (
	name varchar NOT NULL, -- Название встречи
	"date" date NOT NULL, -- Дата встречи
	meetingid varchar NOT NULL -- Идентификатор
);
CREATE UNIQUE INDEX meetings_meetingid_idx ON bot.meetings USING btree (meetingid);

COMMENT ON COLUMN bot.meetings."name" IS 'Название встречи';
COMMENT ON COLUMN bot.meetings."date" IS 'Дата встречи';
COMMENT ON COLUMN bot.meetings.meetingid IS 'Идентификатор';
