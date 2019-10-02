package commands;

import dao.GameDAO;
import entities.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class command to get all the games for the meeting
 *
 * @author ashavrov
 */
public class CommandShowGames implements Command {
    @Override
    public List<MessageCommandOut> execute(MessageCommandIn message) {
        ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();

        Pattern patternCommand = Pattern.compile("^(/.*?)(\\s)(\".*\")$");
        Matcher matcherCommand = patternCommand.matcher(message.getMessage());

        if (matcherCommand.find()) {
            String meetingId = matcherCommand.group(3).replace("\"", "");
            GameDAO gameDAO = new GameDAO();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Игры:").append(System.getProperty("line.separator"));
            for (Game game : gameDAO.getByMeetingId(meetingId)) {
                stringBuilder.append(game.getName()).append(System.getProperty("line.separator"));
            }
            listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId()).setText(stringBuilder.toString()));
            listMessagesOut.add(new MessageCommandOut(message, null).setText("Запустить:")
                    .addButton("Запустить все игры", "/playGames \"" + meetingId + "\""));
        } else {
            listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
                    .setText("Неверный формат команды."));
        }
        return listMessagesOut;
    }
}
