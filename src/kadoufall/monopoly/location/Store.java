package kadoufall.monopoly.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kadoufall.monopoly.application.Point;
import kadoufall.monopoly.card.AverageRichCard;
import kadoufall.monopoly.card.BuyCard;
import kadoufall.monopoly.card.Card;
import kadoufall.monopoly.card.Dice;
import kadoufall.monopoly.card.PlunderCard;
import kadoufall.monopoly.card.Roadblock;
import kadoufall.monopoly.card.TaxInspectionCard;
import kadoufall.monopoly.card.TurnCard;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;

public class Store extends Location {

	public Store(String name, Point point) {
		super(name, point);
	}

	@Override
	public void operation(Player player) {
		Card[] buyCard = new Card[7];
		buyCard[0] = new TurnCard();
		buyCard[1] = new AverageRichCard();
		buyCard[2] = new Roadblock();
		buyCard[3] = new TaxInspectionCard();
		buyCard[4] = new BuyCard();
		buyCard[5] = new PlunderCard();
		buyCard[6] = new Dice();

		if (player.getCoupon() < 5) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("欢迎光临" + name);
			alert.setHeaderText(null);
			alert.setContentText("您的点券余额为" + player.getCoupon() + "!\n" + "每个道具需要点券5，您的点券不足，债见，欢迎再次光临！");
			alert.showAndWait();
		} else {
			String info = "";
			List<String> choices = new ArrayList<>();
			for (Card c : buyCard) {
				choices.add(c.getName());
				info += c.getName() + ":" + c.getInformation() + "\n";
			}
			ChoiceDialog<String> dialog = new ChoiceDialog<>(buyCard[0].getName(), choices);
			dialog.setTitle("欢迎光临" + name);
			dialog.setHeaderText("您的点券余额为" + player.getCoupon() + "!\n" + "每个道具需要点券5");
			dialog.setContentText(info + "请选择需要购买的道具:");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				String choice = result.get();
				for (Card c : buyCard) {
					if (choice.equals(c.getName())) {
						player.addCard(c);
						player.addCoupon(-5);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("欢迎光临" + name);
						alert.setHeaderText(null);
						alert.setContentText("购买" + c.getName() + "成功！您的点券剩余" + player.getCoupon());
						alert.showAndWait();
						break;
					}
				}
				operation(player);
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("欢迎光临" + name);
				alert.setHeaderText(null);
				alert.setContentText(name + "期待您的再次光临");
				alert.showAndWait();
			}

		}

	}

}
