package commands;

import dao.GameDAO;
import entities.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandCreateGame implements Command {
    @Override
    public List<MessageCommandOut> execute(MessageCommandIn message) {
        ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();

        Pattern patternCommand = Pattern.compile("^(/.*?)(\\s)(\".*\")(\\s)(\".*\")$");
        Matcher matcherCommand = patternCommand.matcher(message.getMessage());

        if (matcherCommand.find()) {
            String meetingId = matcherCommand.group(3).replace("\"", "");
            String gameName = matcherCommand.group(5).replace("\"", "");
            Game game = new Game(gameName, meetingId);
            GameDAO gameDAO = new GameDAO();
            gameDAO.insert(game);
            listMessagesOut.add(new MessageCommandOut(message, null)
                    .setText("Игра создана."));
        } else {
            listMessagesOut.add(new MessageCommandOut(message, null)
                    .setText("Ошибка при создании игры. Неверный формат команды."));
        }
        return listMessagesOut;
    }
}
