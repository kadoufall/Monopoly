package kadoufall.monopoly.card;

import java.util.ArrayList;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kadoufall.monopoly.location.Direction;
import kadoufall.monopoly.location.Player;

public class TaxInspectionCard extends Card { // 查税卡

	public TaxInspectionCard() {
	}

	public String getInformation() {
		return "对距离自己五步以内的对手，强行将其30%的存款缴税(所缴税款并不给查税卡的发动者)";
	}

	public String getName() {
		return "查税卡";
	}

	public boolean useCard(ArrayList<Point> points, Player player, ArrayList<Player> players) {
		boolean re = true;
		ArrayList<Player> opponent = new ArrayList<Player>();
		Point cell = player.getPoint().getPointAt(points, player.getPoint(), player.getDirection(), 0);
		for (int i = 0; i < cell.getLocations().size(); i++) {
			if (cell.getLocations().get(i) instanceof Player && cell.getLocations().get(i) != player) {
				opponent.add((Player) cell.getLocations().get(i));
			}
		}
		for (int i = 1; i < 6; i++) {
			cell = player.getPoint().getPointAt(points, player.getPoint(), player.getDirection(), i);
			for (int j = 0; j < cell.getLocations().size(); j++) {
				if (cell.getLocations().get(j) instanceof Player) {
					opponent.add((Player) cell.getLocations().get(j));
				}
			}
			cell = player.getPoint().getPointAt(points, player.getPoint(), Direction.negative(player.getDirection()),
					i);
			for (int j = 0; j < cell.getLocations().size(); j++) {
				if (cell.getLocations().get(j) instanceof Player) {
					opponent.add((Player) cell.getLocations().get(j));
				}
			}
		}
		int oppNum = opponent.size();
		if (oppNum == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("消息");
			alert.setHeaderText(null);
			alert.setContentText("经检测,您五步内无对手,使用失败！");
			alert.showAndWait();
			re = false;
		} else {
			for (int i = 0; i < oppNum; i++) {
				double tax = opponent.get(i).getCash() * 0.3;
				opponent.get(i).addCash(-tax);
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("消息");
			alert.setHeaderText(null);
			alert.setContentText("您已经使对手强行将其30%的存款缴税");
			alert.showAndWait();
		}
		return re;
	}

}
