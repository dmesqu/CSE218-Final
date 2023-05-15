package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
			Parent root = loader.load();
			DataCenter dc = new DataCenter();
			LoginSceneController lsc = loader.getController();
			lsc.setInstance(dc, primaryStage);
	        Scene scene = new Scene(root);
	        primaryStage.setTitle("Firelink");
	        primaryStage.getIcons().add(new Image("https://cdn.discordapp.com/attachments/650756906469687326/1051632188199809054/Planet.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

