package entities;

public class User {
	private String name;
	private String chatId;
	private final String userId;

	public User(String userId, String chatId, String name) {
		this.userId = userId == null ? "" : userId;
		this.chatId = chatId == null ? "" : chatId;
		this.name = name == null ? "" : name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chatId.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + userId.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return chatId.equals(other.chatId) && name.equals(other.name) && userId.equals(other.userId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getChatId() {
		return chatId;
	}

	public String getUserId() {
		return userId;
	}
}
