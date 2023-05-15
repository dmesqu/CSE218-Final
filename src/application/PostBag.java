package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class PostBag implements Serializable {

	private LinkedList<Post> posts = new LinkedList<Post>();
	
	public PostBag() {
		posts = new LinkedList<Post>();
	}
	
	public int getSize() {
		return posts.size();
	}
	
	public void addPost(Post post) {
		posts.add(post);
	}
	
	public Post getPost(int id) {
		return posts.get(id);
	}
	
	public Post removePost(int id) {
		return posts.remove(id);
	}
}