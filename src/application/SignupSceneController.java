package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class SignupSceneController {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private ImageView usrPfp;
	
	private DataCenter dc;
	private Stage stage;
	private Scene scene;
	private Parent root;
	private File sf;

	// Event Listener on Button.onAction
	@FXML
	public void createAccount(ActionEvent event) throws IOException {
		if(tfUsername.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("New User");
			alert.setContentText("No Username Entered");
			alert.showAndWait();
		}
		else if(pfPassword.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("New User");
			alert.setContentText("No Password Entered");
			alert.showAndWait();
		}
		else if(dc.findUser(tfUsername.getText())!=null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("New User");
			alert.setContentText("Username is Taken");
			alert.showAndWait();
		}
		else {
			if(sf==null) {
				sf =  new File("src/application/resources/default.png");
			}
			dc.addUser(tfUsername.getText(), pfPassword.getText(), sf);
			dc.save();
			tfUsername.setText("");
			pfPassword.setText("");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("New User");
			alert.setContentText("Account Created! Returning to Login...");
			alert.showAndWait();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
			root = loader.load();
			DataCenter dc = new DataCenter();
			LoginSceneController lsc = loader.getController();
			lsc.setInstance(dc, stage);
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	// Event Listener on Label.onMouseClicked
	@FXML
	public void login(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
		Parent root = loader.load();
		DataCenter dc = new DataCenter();
		LoginSceneController lsc = loader.getController();
		lsc.setInstance(dc, stage);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Event Listener on ImageView[#usrPfp].onMouseClicked
	@FXML
	public void uploadPfp(MouseEvent event) {
		FileChooser fc =  new FileChooser();
		ExtensionFilter ex1 = new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
		fc.getExtensionFilters().addAll(ex1);
		fc.setTitle("Upload profile picture");
		fc.setInitialDirectory(new File("C:/"));
		sf = fc.showOpenDialog(stage);
		if(sf != null) {
			Image image = new Image(sf.toURI().toString());
			usrPfp.setImage(image);
		}
	}
	
	public void setInstance(DataCenter dc, Stage stage) {
		this.dc = dc;
		this.stage = stage;
		Image def = new Image(getClass().getResourceAsStream("resources/default.png"), 150, 150, false, false);
		usrPfp.setImage(def);
	}
}
