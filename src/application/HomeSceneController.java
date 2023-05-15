package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeSceneController {
	@FXML
	private ImageView usrPfp;
	@FXML
	private Label lblUser;
	@FXML
	private VBox posts;
	
	private DataCenter dc;
	private Stage stage;
	private Scene scene;
	private Parent root;

	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void logout(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
		root = loader.load();
		LoginSceneController lsc = loader.getController();
		lsc.setInstance(dc, stage);
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
	
	public void setInstance(DataCenter dc, Stage stage) {
		this.dc = dc;
		this.stage=stage;
		lblUser.setText(dc.getCurrent());
		usrPfp.setImage(new Image(dc.findUser(dc.getCurrent()).getPfp().toURI().toString()));
		for(int i=dc.findUser(dc.getCurrent()).getUserTimeline().size()-1; i>=0; i--) {
			User tmp = dc.findUser(dc.getPost(dc.findUser(dc.getCurrent()).getUserTimeline().get(i)).getUser());
			int val=i;
            HBox hbox = new HBox();
            hbox.setSpacing(5);
            Label lblContent = new Label();
            lblContent.setWrapText(true);
            lblContent.setText(dc.getPost(dc.findUser(dc.getCurrent()).getUserTimeline().get(i)).toString());
            ImageView imgPfp = new ImageView();
            imgPfp.setImage(new Image(tmp.getPfp().toURI().toString()));
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
                csc.setInstance(dc, stage,dc.getPost(dc.findUser(dc.getCurrent()).getUserTimeline().get(val)).getId());
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            });
            posts.getChildren().add(hbox);
        }
	}
}
