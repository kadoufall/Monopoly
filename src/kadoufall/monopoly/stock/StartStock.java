package kadoufall.monopoly.stock;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kadoufall.monopoly.location.Player;

public class StartStock extends Application {
	static ArrayList<Player> players = null;
	static LocalDate date = null;
	static ArrayList<Stock> stocks = null;
	static Player currentPlayer = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("股票市场");
		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("Stock.fxml"));
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	public StartStock(Player currentPlayer, ArrayList<Player> players, LocalDate date, ArrayList<Stock> stocks) {
		this.players = players;
		this.date = date;
		this.stocks = stocks;
		this.currentPlayer = currentPlayer;
	}

}
