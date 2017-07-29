package kadoufall.monopoly.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert.AlertType;
import kadoufall.monopoly.location.Player;

public class Dice extends Card { // 遥控骰子
	public Dice() {
	}

	public String getName() {
		return "遥控骰子";
	}

	public String getInformation() {
		return "自己使用时可以任意控制骰子的结果，结果只能是(1-6)";
	}

	public boolean useCard(ArrayList<Point> points, Player player, ArrayList<Player> players) {
		List<String> choices = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			choices.add(String.valueOf(i));
		}
		ChoiceDialog<String> dialog = new ChoiceDialog<>(String.valueOf(1), choices);
		dialog.setTitle("选择");
		dialog.setHeaderText(null);
		dialog.setContentText("您想摇到哪一步");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String choice1 = result.get();
			int choice = Integer.parseInt(choice1);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("消息");
			alert.setHeaderText(null);
			alert.setContentText("上帝会保佑您下一步摇到的一定是" + choice);
			alert.showAndWait();
			player.setStep(choice);
			player.setUseDice(true);
		}

		return true;
	}

}
