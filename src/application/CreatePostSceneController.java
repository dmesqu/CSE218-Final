package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreatePostSceneController {
	@FXML
	private ImageView usrPfp;
	@FXML
	private Label lblUser;
	@FXML
	private TextArea taPost;
	@FXML
	private VBox vbox;
	
	private DataCenter dc;
	private Stage stage;
	private Scene scene;
	private Parent root;

	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void home(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
		root = loader.load();
		HomeSceneController hsc = loader.getController();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		hsc.setInstance(dc, stage);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void createPost(ActionEvent event) throws IOException {
		if(taPost.getText().length()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Post Creation");
			alert.setContentText("Upload failed, no content entered.");
			alert.showAndWait();
		}
		else {
			Post post = new Post(dc.getCurrent(), taPost.getText(), dc.getBagSize());
			dc.addPost(post);
			dc.findUser(dc.getCurrent()).addPost(post);
			ArrayList<String> list = new ArrayList<String>(dc.findUser(dc.getCurrent()).getFollowers());
			for(int i=0; i<=list.size()-1; i++) {
				dc.findUser(list.get(i)).addToTimeline(post);
			}
			dc.save();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
			root = loader.load();
			UserProfileSceneController ups = loader.getController();
			ups.setInstance(dc, stage, dc.getCurrent());
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void search(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchScene.fxml"));
		root = loader.load();
		SearchSceneController scc = loader.getController();
		scc.setInstance(dc, stage);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void userProfile(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
		root = loader.load();
		UserProfileSceneController ups = loader.getController();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		ups.setInstance(dc,stage, dc.getCurrent());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Event Listener on Button.onAction
	@FXML
	public void post(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CreatePostScene.fxml"));
		root = loader.load();
		CreatePostSceneController psc = loader.getController();
		psc.setInstance(dc, stage);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void setInstance(DataCenter dc, Stage stage) throws FileNotFoundException, IOException {
		this.dc = dc;
		this.stage = stage;
		lblUser.setText(dc.getCurrent());
		usrPfp.setImage(new Image(dc.findUser(dc.getCurrent()).getPfp().toURI().toString()));
		taPost.textProperty().addListener((observable, oldValue, newValue) -> {
			String[] words = newValue.split("\s+");
            vbox.getChildren().clear();
            for (String word : words) {
                if (!dc.searchDictionary(word)) {
                    Label lbl = new Label(word);
                    vbox.getChildren().add(lbl);
                }
            }
		});
		
	}
}