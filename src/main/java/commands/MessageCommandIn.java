package commands;

public class MessageCommandIn {
	private String message = "";
	private String command = "";
	private Integer userId = 0;
	private Long chatId = (long) 0;
	private Integer deleteMessageId = null;
	private String userName = "";

	public MessageCommandIn(String message, Integer userId, Long chatId, String userName, Integer messageId) {
		this.message = message;
		this.userId = userId;
		this.chatId = chatId;
		this.userName = userName;
		this.deleteMessageId = messageId;
		this.command = message.split(" ")[0];
	}

	public String getMessage() {
		return message;
	}
	
	public Integer getDeleteMessageId() {
		return deleteMessageId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommand() {
		return command;
	}

}
