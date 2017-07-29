package kadoufall.monopoly.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.Pane;

// 主程序
public class Monopoly extends Application {
	// 玩家数
	public static int playerNum = 0;

	@Override
	public void start(Stage primaryStage) {
		// 选择玩家数并进入游戏
		List<Integer> choices = new ArrayList<>();
		choices.add(2);
		choices.add(3);
		choices.add(4);

		ChoiceDialog<Integer> dialog = new ChoiceDialog<>(2, choices);
		dialog.setTitle("选择");
		dialog.setHeaderText(null);
		dialog.setContentText("请选择玩家数");

		Optional<Integer> result = dialog.showAndWait();
		if (result.isPresent()) {
			playerNum = result.get();
			try {
				primaryStage.setTitle("Monopoly");
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Map.fxml"));
				Pane myPane = (Pane) fxmlLoader.load();
				Scene myScene = new Scene(myPane);
				primaryStage.setScene(myScene);
				primaryStage.setResizable(false);
				primaryStage.sizeToScene();
				primaryStage.show();
			} catch (IOException event) {
				event.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
