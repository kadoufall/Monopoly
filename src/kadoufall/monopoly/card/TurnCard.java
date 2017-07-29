package kadoufall.monopoly.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import kadoufall.monopoly.location.Direction;
import kadoufall.monopoly.location.Player;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

public class TurnCard extends Card {
	public TurnCard() {
	}

	public String getInformation() {
		return "使自己或距离自己五步以内的对手掉转方向";
	}

	public String getName() {
		return "转向卡";
	}

	public boolean useCard(ArrayList<Point> points, Player player, ArrayList<Player> players) {
		boolean re = true;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("选择");
		alert.setHeaderText(null);
		alert.setContentText("请选择对谁使用转向卡");

		ButtonType buttonTypeOne = new ButtonType("自己");
		ButtonType buttonTypeTwo = new ButtonType("对手");
		ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			player.setDirection(Direction.negative(player.getDirection()));
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("消息");
			alert1.setHeaderText(null);
			alert1.setContentText("您现在使自己调转方向为" + Direction.toDirection(player.getDirection()));
			alert1.showAndWait();
		} else if (result.get() == buttonTypeTwo) {
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
				cell = player.getPoint().getPointAt(points, player.getPoint(),
						Direction.negative(player.getDirection()), i);
				for (int j = 0; j < cell.getLocations().size(); j++) {
					if (cell.getLocations().get(j) instanceof Player) {
						opponent.add((Player) cell.getLocations().get(j));
					}
				}
			}
			int oppNum = opponent.size();
			if (oppNum == 0) {
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("消息");
				alert1.setHeaderText(null);
				alert1.setContentText("经检测,您五步内无对手,使用失败！");
				alert1.showAndWait();
				re = false;
			} else {
				List<String> choices = new ArrayList<>();
				for (Player p : opponent) {
					choices.add(p.getName());
				}
				ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
				dialog.setTitle("选择");
				dialog.setHeaderText(null);
				dialog.setContentText("经检测,您五步内有以下对手，请选择使用对象");

				Optional<String> result1 = dialog.showAndWait();
				if (result1.isPresent()) {
					String choice = result1.get();

					for (Player p : opponent) {
						if (choice.equals(p.getName())) {
							p.setDirection(Direction.negative(p.getDirection()));
							Alert alert1 = new Alert(AlertType.INFORMATION);
							alert1.setTitle("消息");
							alert1.setHeaderText(null);
							alert1.setContentText(
									"您已经成功使" + p.getName() + "调转方向为" + Direction.toDirection(p.getDirection()));
							alert1.showAndWait();
							break;
						}
					}
				}else{
                                    re = false;
                                }
			}

		} else {
			re = false;
		}

		return re;
	}

}
