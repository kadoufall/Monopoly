package kadoufall.monopoly.location;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Space extends Location {

	public Space(Point point) {
		super("空", point);
	}

	@Override
	public void operation(Player player) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("欢迎光临" + name);
		alert.setHeaderText(null);
		alert.setContentText("来到了空地=_=好无聊！");
		alert.showAndWait();
	}

}
