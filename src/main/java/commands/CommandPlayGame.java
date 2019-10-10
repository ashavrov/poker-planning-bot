package commands;

import dao.GameDAO;
import entities.Game;
import entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandPlayGame implements Command {
    private final ArrayList<Game> games = new ArrayList<>();

    @Override
    public List<MessageCommandOut> execute(MessageCommandIn message) {
        ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();

        Pattern patternCommand = Pattern.compile("^(/.*?)(\\s)(\".*\")(\\s)(\".*\")$");
        Matcher matcherCommand = patternCommand.matcher(message.getMessage());

        if (matcherCommand.find()) {
            String gameId = matcherCommand.group(3).replace("\"", "");
            String bet = matcherCommand.group(5).replace("\"", "");
            Game game = getRunningGames(gameId);
            if (game == null) {
                GameDAO gameDAO = new GameDAO();
                game = gameDAO.getById(gameId);
                games.add(game);
            }

            for (User user : game.getUsers()) {
                if (user.getUserId().equals(message.getUserId())) {
                    if(game.play(user, bet)){
                        for (HashMap.Entry<User, String> bets : game.getUsersBets().entrySet()) {
                            listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
                                    .setText(bets.getKey().getName()+" "+bets.getValue()));
                        }
                        break;
                    }
                }
            }
        } else {
            listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
                    .setText("Неверный формат команды."));
        }
        return listMessagesOut;
    }

    private Game getRunningGames(String gameId) {
        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                return game;
            }
        }
        return null;
    }
}
