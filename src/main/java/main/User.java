package main;

public class User {
	private String name = null;
	private String chatId = null;
	private String userId = null;
	public User(String userId, String chatId, String name) {
		this.userId = userId;
		this.chatId = chatId;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String getChatId() {
		return chatId;
	}
	public String getUserId() {
		return userId;
	}
}
