package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;

public class DataCenter implements Serializable {

	private UserBag users;
	private PostBag posts;
	private String current;
	private HashSet<String> dictionary = new HashSet<String>();
	
	public DataCenter() throws FileNotFoundException, IOException {
		File data = new File("src/data.dat");
		if(data.length()==0) {
			users = new UserBag();
			posts = new PostBag();
			dictionary = new HashSet<String>();
			try (BufferedReader reader = new BufferedReader(new FileReader("src/application/resources/dictionary.txt"))) {
			    String line;
			    while ((line = reader.readLine()) != null) {
			        dictionary.add(line.trim());
			    }
			}
			this.save();
		}
		else {
			try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(data))) {
				DataCenter load = (DataCenter) (ois.readObject());
					users = load.getUsers();
					posts = load.getPosts();
					dictionary = load.getDictionary();
			}
			catch (IOException | ClassNotFoundException ie) {
				ie.printStackTrace();
			}
		}
	}

	private HashSet<String> getDictionary() {
		return dictionary;
	}

	public void save() {
		File data = new File("src/data.dat");
		try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(data))) {
			oos.writeObject(this);
		}
		catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public UserBag getUsers() {
		return users;
	}
	
	public PostBag getPosts() {
		return posts;
	}
	
	public void addUser(String username, String password, File pfp) {
		users.addUser(new User(username, password, pfp));
	}
	
	public User findUser(String username) {
		return users.findUser(username);
	}
	
	public void addPost(Post post) {
		posts.addPost(post);
	}
	
	public Post getPost(int id) {
		return posts.getPost(id);
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}
	
	public int getBagSize() {
		return posts.getSize();
	}
	
	public boolean searchDictionary(String s) {
		return dictionary.contains(s);
	}
}
