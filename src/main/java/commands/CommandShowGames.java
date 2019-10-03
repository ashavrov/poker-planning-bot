package commands;

import dao.GameDAO;
import entities.Game;

import java.util.ArrayList;
import java.util.HashMap;
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
            HashMap<String, String> buttonHashMap = new HashMap<>();
            MessageCommandOut messageOut = new MessageCommandOut(message, message.getDeleteMessageId()).setText("Запустить игру:");
            for (Game game : gameDAO.getByMeetingId(meetingId)) {
                buttonHashMap.put(game.getName(), "/startPlay \"" + game.getGameId() + "\"");
            }
            messageOut.addButtons(buttonHashMap);
            listMessagesOut.add(messageOut);
        } else {
            listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
                    .setText("Неверный формат команды."));
        }
        return listMessagesOut;
    }
}
