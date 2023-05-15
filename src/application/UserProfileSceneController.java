package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserProfileSceneController {
	@FXML
	private ImageView usrPfp;
	@FXML
	private Label lblUser;
	@FXML
	private VBox posts;
	@FXML
	private ImageView usrPage;
	@FXML
	private Label lblProfile;
	@FXML
	private Label lblFollowers;
	@FXML
	private Label lblFollowing;
	@FXML
	private Label lblPosts;
	@FXML
	private Button btnfollow;
	
	private DataCenter dc;
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String user;

	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void home(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
		root = loader.load();
		HomeSceneController hsc = loader.getController();
		hsc.setInstance(dc, stage);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void createPost(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CreatePostScene.fxml"));
		root = loader.load();
		CreatePostSceneController psc = loader.getController();
		psc.setInstance(dc, stage);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
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
		ups.setInstance(dc,stage, dc.getCurrent());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Event Listener on Button.onAction
	@FXML
	public void follow(ActionEvent event) throws IOException {
		if(!dc.findUser(user).getFollowers().contains(dc.getCurrent())) {
            dc.findUser(user).addFollower(dc.findUser(dc.getCurrent()));
            dc.findUser(dc.getCurrent()).addFollow(dc.findUser(user));
            dc.save();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
            root = loader.load();
            UserProfileSceneController psc = loader.getController();
            psc.setInstance(dc, stage, user);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            dc.findUser(user).removeFollower(dc.findUser(dc.getCurrent()));
            dc.findUser(dc.getCurrent()).removeFollow(dc.findUser(user));
            dc.save();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
            root = loader.load();
            UserProfileSceneController psc = loader.getController();
            psc.setInstance(dc, stage, user);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
	}
	
	public void setInstance(DataCenter dc, Stage stage, String user) {
		this.dc = dc;
		this.stage=stage;
		this.user=user;
		if(user.compareTo(dc.getCurrent())==0) {
			btnfollow.setDisable(true);
		}
		if(dc.findUser(user).getFollowers().contains(dc.getCurrent())) {
			btnfollow.setText("Unfollow");
		}
		lblPosts.setText(dc.findUser(user).getUserPosts().size() + "");
		lblFollowers.setText(dc.findUser(user).getFollowers().size() + "");
		lblFollowing.setText(dc.findUser(user).getFollowing().size() + "");
		lblUser.setText(dc.getCurrent());
		lblProfile.setText(user);
		usrPfp.setImage(new Image(dc.findUser(dc.getCurrent()).getPfp().toURI().toString()));
		usrPage.setImage(new Image(dc.findUser(user).getPfp().toURI().toString()));
		for(int i=dc.findUser(user).getUserPosts().size()-1; i>=0; i--) {
			Post tmp = dc.getPost(dc.findUser(user).getUserPosts().get(i));
			int val=i;
            HBox hbox = new HBox();
            hbox.setSpacing(5);
            Label lblContent = new Label();
            lblContent.setWrapText(true);
            lblContent.setText(tmp.toString());
            ImageView imgPfp = new ImageView();
            imgPfp.setImage(new Image(dc.findUser(user).getPfp().toURI().toString()));
            imgPfp.setFitHeight(40);
            imgPfp.setFitWidth(40);
            hbox.getChildren().addAll(imgPfp, lblContent);
            hbox.setOnMouseClicked(e->{
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentScene.fxml"));
				try {
					root = loader.load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CommentSceneController csc = loader.getController();
                csc.setInstance(dc, stage, dc.getPost(dc.findUser(user).getUserPosts().get(val)).getId());
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            });
            posts.getChildren().add(hbox);
        }
		
	}
}
