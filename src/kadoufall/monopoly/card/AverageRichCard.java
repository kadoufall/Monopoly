package kadoufall.monopoly.card;

import java.util.ArrayList;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kadoufall.monopoly.location.Player;

public class AverageRichCard extends Card { // 均富卡

	public AverageRichCard() {
	}

	public String getInformation() {
		return "将所有人的现金平均分配";
	}

	public String getName() {
		return "均富卡";
	}

	@Override
	public boolean useCard(ArrayList<Point> points, Player player, ArrayList<Player> players) {
		double averageCash = 0;
		for (int i = 0; i < players.size(); i++) {
			averageCash += players.get(i).getCash();
		}
		averageCash /= players.size();
		for (int i = 0; i < players.size(); i++) {
			players.get(i).addCash(-players.get(i).getCash() + averageCash);
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("消息");
		alert.setHeaderText(null);
		alert.setContentText("所有人的现金已经平均分配！");
		alert.showAndWait();
		return true;
	}

}
