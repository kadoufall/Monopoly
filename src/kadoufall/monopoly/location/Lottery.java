package kadoufall.monopoly.location;

import java.util.Optional;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class Lottery extends Location {

	public Lottery(String name, Point point) {
		super(name, point);
	}

	@Override
	public void operation(Player player) {
		int money = 1000;
		String header = "";
		header = "一注彩票固定价格10元，中奖金额" + money + "元\n";
		header += "今天以下彩票开放购买,每注彩票号码如下:\n";
		String[] lotArray = new String[10];
		for (int i = 0; i < 10; i++) {
			String lotNum = String.valueOf((int) (Math.random() * 10000) + 1)
					+ String.valueOf((int) (Math.random() * 10)) + String.valueOf((int) (Math.random() * 10000));
			lotArray[i] = lotNum;
			header += "第" + i + "注：" + lotNum + "\n";
		}

		int win = (int) (Math.random() * 10);

		core(header, win, money, lotArray, player);
	}

	private void core(String header, int win, int money, String[] lotArray, Player player) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("欢迎光临" + name);
		dialog.setHeaderText(header);
		dialog.setContentText("请选择要购买第几注,输入(0-9),若不购买，请退出");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String choice = result.get();
			try {
				int chose = Integer.parseInt(choice);
				if (chose >= 0 && chose <= 9) {
					player.addCash(-10);
					if (chose == win) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("消息");
						alert.setHeaderText(null);
						alert.setContentText("恭喜你中奖了，获得" + money + "元现金!");
						alert.showAndWait();
						player.addCash(money);
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("消息");
						alert.setHeaderText(null);
						alert.setContentText("中奖号码为" + lotArray[win] + "\n很遗憾，您未中奖，谢谢参与!");
						alert.showAndWait();
					}
				} else {
					Alert warn = new Alert(AlertType.WARNING);
					warn.setTitle("错误");
					warn.setHeaderText(null);
					warn.setContentText("您的输入有误,请重新输入");
					warn.showAndWait();
					core(header, win, money, lotArray, player);
				}
			} catch (Exception ex) {
				Alert warn = new Alert(AlertType.WARNING);
				warn.setTitle("错误");
				warn.setHeaderText(null);
				warn.setContentText("您的输入有误,请重新输入");
				warn.showAndWait();
				core(header, win, money, lotArray, player);
			}
		}
	}

}
