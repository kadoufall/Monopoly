package kadoufall.monopoly.card;

import java.util.ArrayList;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kadoufall.monopoly.location.Direction;
import kadoufall.monopoly.location.Player;

public class PlunderCard extends Card { // 掠夺卡

	public PlunderCard() {
	}

	public String getInformation() {
		return "对距离自己五步以内的对手，随机将对手的一张卡牌据为己有";
	}

	public String getName() {
		return "掠夺卡";
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
			String con = "";
			for (int i = 0; i < oppNum; i++) {
				int cardNum = opponent.get(i).getCardsList().size();
				if (cardNum > 0) {
					int choice = (int) (Math.random() * cardNum);
					Card card = opponent.get(i).getCardsList().get(choice);
					player.addCard(card);
					opponent.get(i).getCardsList().remove(card);
					con += "掠夺到" + card.getName() + "\n";
				}
			}
			con += "您已经随机将对手的一张卡牌据为己有";
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("消息");
			alert.setHeaderText(null);
			alert.setContentText(con);
			alert.showAndWait();
		}
		return re;
	}

}
