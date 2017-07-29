package kadoufall.monopoly.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert.AlertType;
import kadoufall.monopoly.location.Barricade;
import kadoufall.monopoly.location.Direction;
import kadoufall.monopoly.location.Player;

public class Roadblock extends Card { // 路障

	public Roadblock() {
	}

	public String getInformation() {
		return "可以在前后8步之内安放一个路障，任意玩家经过路障时会停在路障所在位置不能前行";
	}

	public String getName() {
		return "路障";
	}

	public boolean useCard(ArrayList<Point> points, Player player, ArrayList<Player> players) {
		List<String> choices = new ArrayList<>();
		for (int i = -8; i < 9; i++) {
			choices.add(String.valueOf(i));
		}
		ChoiceDialog<String> dialog = new ChoiceDialog<>(String.valueOf(1), choices);
		dialog.setTitle("选择");
		dialog.setHeaderText(null);
		dialog.setContentText("您想在哪一步放置路障");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			int choice = Integer.parseInt(result.get());
			Point cell = player.getPoint().getPointAt(points, player.getPoint(), player.getDirection(), choice);
			if (choice < 0) {
				cell = player.getPoint().getPointAt(points, player.getPoint(),
						Direction.negative(player.getDirection()), -choice);
			}
			Barricade ba = new Barricade("", cell);
			cell.addLocation(ba);
			cell.setIcon();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("消息");
			alert.setHeaderText(null);
			alert.setContentText("添加路障成功！");

		}
		return true;
	}

}
