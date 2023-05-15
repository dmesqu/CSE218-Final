package application;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Post implements Serializable {

	private String user;
	private String content;
	private String time;
	private int id;
	private int likes;
	private LinkedList<Integer> replies = new LinkedList<Integer>();
	
	public Post(String user, String content, int id) {
		this.user = user;
		this.content = content;
		this.id = id;
		String pattern =  "HH:mm MM-dd-yyyy";
		this.time = new SimpleDateFormat(pattern).format(new Date());
		this.likes = 0;
		replies = new LinkedList<Integer>();
	}
	
	
	
	public String getUser() {
		return user;
	}

	public String getContent() {
		return content;
	}
	
	public int getId(){
		return id;
	}
	
	public void addReply(Post post) {
		replies.add(post.getId());
	}
	
	public void removeReply(Post post) {
		replies.remove(post.getId());
	}

	public LinkedList<Integer> getReplies() {
		return replies;
	}

	public String getTime() {
		return time;
	}
	
	public int getLikes() {
		return likes;
	}

	public void like() {
		this.likes++;
	}
	
	public void unLike() {
		this.likes--;
	}
	
	public void setUser(String user) {
		this.user=user;
	}
	
	@Override
	public String toString() {
		return this.getUser() +  " " + this.getTime() + "\n" + this.getContent() + "\nLikes: " + this.getLikes();
	}
	
}