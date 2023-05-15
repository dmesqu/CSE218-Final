package application;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.TreeSet;

public class User implements Serializable {

	private String username;
	private String password;
	private File pfp;
	private TreeSet<String> followers = new TreeSet<String>();
	private TreeSet<String> following = new TreeSet<String>();
	private LinkedList<Integer> userPosts = new LinkedList<Integer>();
	private LinkedList<Integer> userTimeline = new LinkedList<Integer>();
	private LinkedList<Integer> likedPosts = new LinkedList<Integer>();
	
	public User(String username, String password, File pfp) {
		this.username=username;
		this.password=password;
		this.pfp=pfp;
		followers = new TreeSet<String>();
		following = new TreeSet<String>();
		userPosts = new LinkedList<Integer>();
		likedPosts= new LinkedList<Integer>();
		userTimeline = new LinkedList<Integer>();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void addFollower(User user) {
		followers.add(user.getUsername());
	}
	
	public void removeFollower(User user) {
		followers.remove(user.getUsername());
	}
	
	public void addFollow(User user) {
		following.add(user.getUsername());
	}
	
	public void removeFollow(User user) {
		following.remove(user.getUsername());
	}
	
	public void addPost(Post post) {
		userPosts.add(post.getId());
	}
	
	public void removePost(Post post) {
		userPosts.remove(post.getId());
	}
	
	public void addToTimeline(Post post) {
		userTimeline.add(post.getId());
	}
	
	public void removeFromTimeline(Post post) {
		userTimeline.remove(post.getId());
	}	
	

	public TreeSet<String> getFollowers() {
		return followers;
	}

	public TreeSet<String> getFollowing() {
		return following;
	}

	public LinkedList<Integer> getUserPosts() {
		return userPosts;
	}

	public LinkedList<Integer> getUserTimeline() {
		return userTimeline;
	}

	public LinkedList<Integer> getLikedPosts() {
		return likedPosts;
	}

	public void likePosts(Post post) {
		likedPosts.add(post.getId());
	}
	
	public void dislikePost(Post post) {
		int idx = likedPosts.indexOf(post.getId());
		likedPosts.remove(idx);
	}

	public File getPfp() {
		return pfp;
	}

	public void setPfp(File pfp) {
		this.pfp = pfp;
	}
	
}
