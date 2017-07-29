package kadoufall.monopoly.location;

import java.util.Optional;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class Bank extends Location {

	public Bank(String name, Point point) {
		super(name, point);
	}

	public void operation(Player player) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("欢迎光临" + name);
		alert.setHeaderText(null);
		alert.setContentText("请选择操作,退出请选择“取消”");

		ButtonType buttonTypeOne = new ButtonType("存款");
		ButtonType buttonTypeTwo = new ButtonType("取款");
		ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			saveMoney(player);
			operation(player);
		} else if (result.get() == buttonTypeTwo) {
			withdrawalMoney(player);
			operation(player);
		} else {
			Alert quit = new Alert(AlertType.INFORMATION);
			quit.setTitle("消息");
			quit.setHeaderText(null);
			quit.setContentText(name + "期待您的再次光临！");
			quit.showAndWait();
		}
	}

	private void saveMoney(Player player) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("欢迎光临" + name);
		dialog.setHeaderText(null);
		dialog.setContentText("您的现金为" + player.getCash() + "\n请输入您想要存入的金额数(0-" + player.getCash() + "),金额将保留两位小数");

		Optional<String> result = dialog.showAndWait();
		String save1 = "";
		if (result.isPresent()) {
			save1 = result.get();
			try {
				double save = Double.valueOf(save1);
				save1 = String.format("%.2f", save);
				save = Double.valueOf(save1);
				if (save <= player.getCash() && save >= 0) {
					player.addDeposit(save);
					player.addCash(-save);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("存款成功");
					alert.setHeaderText(null);
					alert.setContentText("您的存款从" + (player.getDeposit() - save) + "元增加至" + player.getDeposit() + "元\n"
							+ "您的现金从" + (player.getCash() + save) + "元减少至" + player.getCash() + "元");
					alert.showAndWait();
				} else {
					Alert warn = new Alert(AlertType.WARNING);
					warn.setTitle("错误");
					warn.setHeaderText(null);
					warn.setContentText("您的输入有误,请重新输入");
					warn.showAndWait();
					saveMoney(player);
				}
			} catch (Exception ex) {
				Alert warn = new Alert(AlertType.WARNING);
				warn.setTitle("错误");
				warn.setHeaderText(null);
				warn.setContentText("您的输入有误,请重新输入");
				warn.showAndWait();
				saveMoney(player);
			}
		}

	}

	private void withdrawalMoney(Player player) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("欢迎光临" + name);
		dialog.setHeaderText(null);
		dialog.setContentText(
				"您的存款为" + player.getDeposit() + "\n请输入您想要取出的金额数(0-" + player.getDeposit() + "),金额将保留两位小数");

		Optional<String> result = dialog.showAndWait();
		String withdrawal1 = "";
		if (result.isPresent()) {
			withdrawal1 = result.get();
			try {
				double withdrawal = Double.valueOf(withdrawal1);
				withdrawal1 = String.format("%.2f", withdrawal);
				withdrawal = Double.valueOf(withdrawal1);
				if (withdrawal <= player.getDeposit() && withdrawal >= 0) {
					player.addDeposit(-withdrawal);
					player.addCash(withdrawal);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("取款成功");
					alert.setHeaderText(null);
					alert.setContentText("您的存款从" + (player.getDeposit() + withdrawal) + "元减少至" + player.getDeposit()
							+ "元\n" + "您的现金从" + (player.getCash() - withdrawal) + "元增加至" + player.getCash() + "元");
					alert.showAndWait();
				} else {
					Alert warn = new Alert(AlertType.WARNING);
					warn.setTitle("错误");
					warn.setHeaderText(null);
					warn.setContentText("您的输入有误,请重新输入");
					warn.showAndWait();
					withdrawalMoney(player);
				}
			} catch (Exception ex) {
				Alert warn = new Alert(AlertType.WARNING);
				warn.setTitle("错误");
				warn.setHeaderText(null);
				warn.setContentText("您的输入有误,请重新输入");
				warn.showAndWait();
				withdrawalMoney(player);
			}
		}
	}

}
