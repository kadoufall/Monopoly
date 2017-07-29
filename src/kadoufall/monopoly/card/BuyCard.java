package kadoufall.monopoly.card;

import java.util.ArrayList;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kadoufall.monopoly.location.Land;
import kadoufall.monopoly.location.Location;
import kadoufall.monopoly.location.Player;

public class BuyCard extends Card { // 购地卡

	public BuyCard() {

	}

	public String getName() {
		return "购地卡";
	}

	public String getInformation() {
		return "强行用现价购买自己当前所在位置的土地，当然发动者不能购买自己的房屋)";
	}

	public boolean useCard(ArrayList<Point> points, Player player, ArrayList<Player> players) {
		boolean re = true;
		boolean hasLand = false;
		int num = -1;

		ArrayList<Location> loc = player.getPoint().getLocations();

		for (int i = 0; i < loc.size(); i++) {
			if (loc.get(i) instanceof Land) {
				hasLand = true;
				num = i;
			}
		}
		if (hasLand) {
			Land land = (Land) loc.get(num);
			if (land.getOwner() == player) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("消息");
				alert.setHeaderText(null);
				alert.setContentText("使用失败！当前房屋是自己的！");
				alert.showAndWait();
				re = false;
			} else {
				if (land.getPrice() > player.getCash()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("消息");
					alert.setHeaderText(null);
					alert.setContentText("使用失败！现金不足以购买房屋！");
					alert.showAndWait();
					re = false;
				} else {
					if (land.getOwner() == null) {
						land.setLevel(1);
						land.setOwner(player);
						player.addHouseNum(1);
						player.addLands(land);
						player.addCash(-land.getPrice());
					} else {
						land.getOwner().getLands().remove(land);
						land.getOwner().addHouseNum(-1);
						land.setOwner(player);
						player.addHouseNum(1);
						player.addCash(-land.getPrice());
						player.addLands(land);
					}
					land.changeIcon();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("消息");
					alert.setHeaderText(null);
					alert.setContentText("使用成功，您获得了" + land.getName());
					alert.showAndWait();
				}
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("消息");
			alert.setHeaderText(null);
			alert.setContentText("当前所在地非房屋，不能购买！");
			alert.showAndWait();
			re = false;
		}

		return re;
	}

}
