package kadoufall.monopoly.card;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kadoufall.monopoly.location.Location;
import kadoufall.monopoly.location.Player;

public class GiveCoupon extends Location {

	public GiveCoupon(String name, Point point) {
		super(name, point);
	}

	@Override
	public void operation(Player player) {
		player.addCoupon(25);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("欢迎光临" + name);
		alert.setHeaderText(null);
		alert.setContentText("赏您25点券！");
		alert.showAndWait();
	}

}
