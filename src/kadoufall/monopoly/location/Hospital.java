package kadoufall.monopoly.location;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Hospital extends Location {

	public Hospital(String name, Point point) {
		super(name, point);
	}

	@Override
	public void operation(Player player) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("欢迎光临" + name);
		alert.setHeaderText(null);
		alert.setContentText("我没病");
		alert.showAndWait();
	}

}
