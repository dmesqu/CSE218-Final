package application;

import java.io.Serializable;
import java.util.TreeMap;

public class UserBag implements Serializable {

	public TreeMap<String, User> users = new TreeMap<String, User>();
	
	public UserBag() {
		users = new TreeMap<String, User>();
	}
	
	public void addUser(User user) {
		users.put(user.getUsername(), user);
	}
	
	public void removeUser(User user) {
		users.remove(user.getUsername());
	}
	
	public User findUser(String username) {
		return users.get(username);
	}

	public int getList() {
		return users.size();
	}

	public TreeMap<String, User> getMap() {
		return users;
	}

	
}
