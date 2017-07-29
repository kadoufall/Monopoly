package kadoufall.monopoly.location;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

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

public class News extends Location {

	public News(String name, Point point) {
		super(name, point);
	}

	@Override
	public void operation(Player player) {
		// do nothing

	}

	public void operation(Player player, ArrayList<Player> players) {
		String con = "";

		int chance = (int) (Math.random() * 6);
		switch (chance) {
		case 0:
			double money = Double.valueOf(Math.floor(new Random().nextDouble() * 1000));
			Player max = players.stream().max((p, p1) -> (p.getHouseNum() - p1.getHouseNum())).get();
			max.addCash(money);
			con += max.getName() + "的房产数最多，大土豪，奖励" + money + "元现金！";
			break;
		case 1:
			double money1 = Double.valueOf(Math.floor(new Random().nextDouble() * 1000));
			Player min = players.stream().min((p, p1) -> (p.getHouseNum() - p1.getHouseNum())).get();
			con += min.getName() + "的房产数最少，小可怜，资助" + money1 + "元现金！";
			min.addCash(money1);
			break;
		case 2:
			con += "银行加发储金红利啦，每个人得到存款的10%";
			Consumer<Player> giveMoney = e -> e.addDeposit(e.getDeposit() * 0.1);
			players.forEach(giveMoney);
			break;
		case 3:
			con += "积极缴税，每个人缴纳财产税10%";
			Consumer<Player> giveMoney1 = e -> e.addDeposit(-e.getDeposit() * 0.1);
			players.forEach(giveMoney1);
			break;
		case 4:
			Card card = new TurnCard();
			String str = "卡卡发卡了，每个人得到一张转向片";
			int random = (int) (Math.random() * 7);
			switch (random) {
			case 0:
				break;
			case 1:
				card = new AverageRichCard();
				str = "卡卡发卡了，每个人得到一张均富卡片";
				break;
			case 2:
				card = new TaxInspectionCard();
				str = "卡卡发卡了，每个人得到一张查税片";
				break;
			case 3:
				card = new PlunderCard();
				str = "卡卡发卡了，每个人得到一张掠夺片";
				break;
			case 4:
				card = new BuyCard();
				str = "卡卡发卡了，每个人得到一张购地片";
				break;
			case 5:
				card = new Dice();
				str = "卡卡发卡了，每个人得到一个遥控骰子";
				break;
			case 6:
				card = new Roadblock();
				str = "卡卡发卡了，每个人得到一个路障";
				break;
			}
			con += str;
			for (int i = 0; i < players.size(); i++) {
				players.get(i).addCard(card);
			}
			break;
		case 5:
			con += "糟糕，受伤了。。\n被强制关进医院两回合，两回合后沿逆时针继续游戏";
			player.setHurt(true);
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("欢迎光临" + name);
		alert.setHeaderText("大新闻！！！");
		alert.setContentText(con);
		alert.showAndWait();
	}
}
