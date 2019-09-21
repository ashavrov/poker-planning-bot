CREATE TABLE bot.meeting_user (
	userid varchar NOT NULL,
	meetingid varchar NOT NULL,
	primaryflag bpchar(1) NULL DEFAULT 'N'::bpchar
);
