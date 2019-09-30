CREATE TABLE bot.games (
    gameId varchar NOT NULL,
	name varchar NOT NULL,
	meetingId varchar NOT NULL,
	endsFlag bpchar(1) NULL DEFAULT 'N'::bpchar
);
