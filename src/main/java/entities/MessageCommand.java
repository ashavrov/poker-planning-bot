package entities;

public class MessageCommand {
	private String message = "";
	private String command = "";
	private Integer userId = 0;
	private Long chatId = (long) 0;
	private String firstName = "";

	public MessageCommand(String message, Integer userId, Long chatId, String firstName) {
		this.message = message;
		this.userId = userId;
		this.chatId = chatId;
		this.firstName = firstName;
		this.command = message.split(" ")[0];
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCommand() {
		return command;
	}

}
