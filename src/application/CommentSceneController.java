package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CommentSceneController {
	@FXML
	private ImageView usrPfp;
	@FXML
	private Label lblUser;
	@FXML
	private TextField tfReply;
	@FXML
	private VBox posts;
	@FXML
	private Button btnLike;
	
	private DataCenter dc;
	private Stage stage;
	private Scene scene;
	private Parent root;
	private int postID;

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
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		ups.setInstance(dc,stage, dc.getCurrent());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Event Listener on Button.onAction
	@FXML
	public void postReply(ActionEvent event) throws IOException {
		if(tfReply.getText().length()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Post Creation");
			alert.setContentText("Upload failed, no content entered.");
			alert.showAndWait();
		}
		else {
			Post post = new Post(dc.getCurrent(), tfReply.getText(), dc.getBagSize());
			dc.addPost(post);
			dc.getPost(postID).addReply(post);
			dc.save();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentScene.fxml"));
			root = loader.load();
			CommentSceneController csc = loader.getController();
			csc.setInstance(dc, stage, postID);
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void like(ActionEvent event) throws IOException {
		if(dc.findUser(dc.getCurrent()).getLikedPosts().contains(postID)) {
			dc.findUser(dc.getCurrent()).dislikePost(dc.getPost(postID));
			dc.getPost(postID).unLike();
			dc.save();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentScene.fxml"));
			root = loader.load();
			CommentSceneController csc = loader.getController();
            csc.setInstance(dc, stage, postID);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
		}
		else {
			dc.findUser(dc.getCurrent()).getLikedPosts().add(postID);
			dc.getPost(postID).like();
			dc.save();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentScene.fxml"));
			root = loader.load();
			CommentSceneController csc = loader.getController();
            csc.setInstance(dc, stage, postID);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
		}
	}
	
	public void setInstance(DataCenter dc, Stage stage, int postID) {
		this.dc = dc;
		this.stage = stage;
		this.postID = postID;
		lblUser.setText(dc.getCurrent());
		usrPfp.setImage(new Image(dc.findUser(dc.getCurrent()).getPfp().toURI().toString()));
		if(dc.findUser(dc.getCurrent()).getLikedPosts().contains(postID)) {
			btnLike.setText("Unlike");
		}
		tfReply.setPromptText("Reply as " + dc.getCurrent());
		HBox hboxop = new HBox();
		Label opLabel = new Label();
		opLabel.setWrapText(true);
		opLabel.setText(dc.getPost(postID).toString());
		ImageView opPfp = new ImageView();
		opPfp.setImage(new Image(dc.findUser(dc.getPost(postID).getUser()).getPfp().toURI().toString()));
		opPfp.setFitHeight(40);
		opPfp.setFitWidth(40);
		hboxop.setSpacing(5);
		hboxop.getChildren().addAll(opPfp, opLabel);
		posts.getChildren().add(hboxop);
		for(int i=0; i<=dc.getPost(postID).getReplies().size()-1; i++) {
			Post post = dc.getPost(dc.getPost(postID).getReplies().get(i));
            HBox hbox = new HBox();
            hbox.setSpacing(5);
            Label lblContent = new Label();
            lblContent.setWrapText(true);
            lblContent.setText(post.toString());
            ImageView imgPfp = new ImageView();
            imgPfp.setImage(new Image(dc.findUser(post.getUser()).getPfp().toURI().toString()));
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
                csc.setInstance(dc, stage, post.getId());
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            });
            posts.getChildren().add(hbox);
        }
	}
	
	
}
