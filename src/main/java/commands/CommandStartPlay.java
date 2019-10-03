package commands;

import dao.GameDAO;
import entities.Game;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandStartPlay implements Command {
    private final ArrayList<Game> games = new ArrayList<>();

    @Override
    public List<MessageCommandOut> execute(MessageCommandIn message) {
        ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();

        Pattern patternCommand = Pattern.compile("^(/.*?)(\\s)(\".*\")$");
        Matcher matcherCommand = patternCommand.matcher(message.getMessage());

        if (matcherCommand.find()) {
            String gameId = matcherCommand.group(3).replace("\"", "");
            Game game = getRunningGames(gameId);
            if (game == null) {
                GameDAO gameDAO = new GameDAO();
                game = gameDAO.getById(gameId);
            }
            game.start();
            for (User user : game.getUsers()) {
                listMessagesOut.add(new MessageCommandOut(user,
                        user.getUserId().equals(message.getUserId().toString()) ? message.getDeleteMessageId() : null)
                        .setText("Запущена игра \"" + game.getName() + "\"")
                        .addButton("Играть", "/playGame \"" + game.getGameId() + "\""));
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
