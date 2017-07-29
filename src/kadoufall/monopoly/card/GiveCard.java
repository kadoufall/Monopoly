package kadoufall.monopoly.card;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kadoufall.monopoly.location.Location;
import kadoufall.monopoly.location.Player;

public class GiveCard extends Location {

	public GiveCard(String name, Point point) {
		super(name, point);
	}

	@Override
	public void operation(Player player) {
		String str = "";

		int random = (int) (Math.random() * 7);
		switch (random) {
		case 0:
			player.addCard(new TurnCard());
			str = "卡卡送您一张转向卡哦！";
			break;
		case 1:
			player.addCard(new AverageRichCard());
			str = "卡卡送您一张均富卡哦！";
			break;
		case 2:
			player.addCard(new TaxInspectionCard());
			str = "卡卡送您一张查税卡哦！";
			break;
		case 3:
			player.addCard(new PlunderCard());
			str = "卡卡送您一张掠夺卡哦！";
			break;
		case 4:
			player.addCard(new BuyCard());
			str = "卡卡送您一张购地卡哦！";
			break;
		case 5:
			player.addCard(new Dice());
			str = "卡卡送您一个遥控骰子哦！";
			break;
		case 6:
			player.addCard(new Roadblock());
			str = "卡卡送您一个路障哦！";
			break;
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("欢迎光临" + name);
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.showAndWait();
	}

}
