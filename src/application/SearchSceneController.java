package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchSceneController {
	@FXML
	private ImageView usrPfp;
	@FXML
	private Label lblUser;
	@FXML
	private ListView<String> listSearch;
	@FXML
	private TextField tfSearch;
	
	private DataCenter dc;
	private Stage stage;
	private Scene scene;
	private Parent root;
	private ArrayList<String> words;
	private String search;

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
		ups.setInstance(dc,stage, dc.getCurrent());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Event Listener on Button.onAction
	@FXML
	public void searchBtn(ActionEvent event) {
		listSearch.getItems().clear();
		listSearch.getItems().addAll(searchList(tfSearch.getText(),words));
	}
	
	private List<String> searchList(String searchWords, List<String> listOfStrings) {
		if(searchWords==null) {
			searchWords="";
		}
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
	
	public void setInstance(DataCenter dc, Stage stage) throws IOException {
		this.dc = dc;
		this.stage = stage;
		lblUser.setText(dc.getCurrent());
		usrPfp.setImage(new Image(dc.findUser(dc.getCurrent()).getPfp().toURI().toString()));
		words = new ArrayList<>(dc.getUsers().getMap().keySet());
		listSearch.getItems().addAll(searchList(search,words));
		listSearch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
				try {
					root = loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				UserProfileSceneController psc = loader.getController();
				psc.setInstance(dc, stage,  listSearch.getSelectionModel().getSelectedItem());
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				}
		});
	}
}
