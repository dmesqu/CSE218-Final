package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginSceneController {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField pfPassword;
	
	private DataCenter dc;
	private Stage stage;
	private Scene scene;
	private Parent root;

	// Event Listener on Button[#btnLogin].onAction
	@FXML
	public void login(ActionEvent event) throws IOException {
		if(tfUsername.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Login");
			alert.setContentText("No Username Entered");
			alert.showAndWait();
		}
		else if(pfPassword.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Login");
			alert.setContentText("No Password Entered");
			alert.showAndWait();
		}
		else {
			User user = dc.findUser(tfUsername.getText());
			if(user == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("User Login");
				alert.setContentText("Login Failed");
				alert.showAndWait();
			}
			else if(user.getUsername().equals(tfUsername.getText())&&user.getPassword().equals(pfPassword.getText())){
				dc.setCurrent(tfUsername.getText());
				FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
				root = loader.load();
				HomeSceneController hsc = loader.getController();
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				hsc.setInstance(dc, stage);
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("User Login");
				alert.setContentText("Login Failed");
				alert.showAndWait();
			}
		}
	}
	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void signup(MouseEvent event) throws IOException {
		DataCenter dc = new DataCenter();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignupScene.fxml"));
		root = loader.load();
		SignupSceneController scc = loader.getController();
		scc.setInstance(dc, stage);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void setInstance(DataCenter dc, Stage stage) {
		this.dc=dc;
		this.stage=stage;
	}
}
