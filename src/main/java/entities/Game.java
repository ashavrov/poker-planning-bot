package entities;

import java.util.Objects;
import java.util.UUID;

public class Game {
    private final String gameId;
    private String name;
    private String meetingId;
    private boolean isEnds;

    public Game(String name, String meetingId) {
        this.gameId= UUID.randomUUID().toString();
        this.name = name;
        this.meetingId = meetingId;
    }

    public Game(String gameId, String name, String meetingId, boolean isEnds) {
        this.gameId = gameId;
        this.name = name;
        this.meetingId = meetingId;
        this.isEnds = isEnds;
    }

    public String getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public void setEnds(boolean ends) {
        isEnds = ends;
    }


    public boolean isEnds() {
        return isEnds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return isEnds == game.isEnds &&
                Objects.equals(name, game.name) &&
                Objects.equals(meetingId, game.meetingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, meetingId, isEnds);
    }
}
