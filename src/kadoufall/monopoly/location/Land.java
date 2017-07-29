package kadoufall.monopoly.location;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

import kadoufall.monopoly.application.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Land extends Location {
	private Player owner = null;
	private int originPrice = 500;
	private int level = 0;
	private int upPrice = 100 * level;
	private int price = 500;

	public Land(String name, Point point) {
		super(name, point);
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(int originPrice) {
		this.originPrice = originPrice;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getUpPrice() {
		return upPrice;
	}

	public void setUpPrice(int upPrice) {
		this.upPrice = upPrice;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public void operation(Player player) {
		// do nothing
	}

	public void operation(Player player, ArrayList<ArrayList<Location>> streets) {
		if (this.getOwner() == null) {
			buyLand(player);
		} else if (this.getOwner() == player) {
			upgradeLand(player);
		} else {
			toll(player, streets);
		}
	}

	private void buyLand(Player player) {
		if (price > player.getCash()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("欢迎光临" + name);
			alert.setHeaderText("当前土地无主,土地等级" + level + "级,土地价格" + price + "元");
			alert.setContentText("系统检测到您现金不足,期待您的再次光临！");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("欢迎光临" + name);
			alert.setHeaderText("当前土地无主,土地等级" + level + "级,土地价格" + price + "元");
			alert.setContentText("您是否愿意购买");

			ButtonType buttonTypeOne = new ButtonType("购买");
			ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne) {
				player.addCash(-price);
				owner = player;
				player.addHouseNum(1);
				player.addLands(this);
				Alert quit = new Alert(AlertType.INFORMATION);
				quit.setTitle("消息");
				quit.setHeaderText(null);
				quit.setContentText("购买成功!" + name + "期待您的再次光临！");
				quit.showAndWait();
				Image image = new Image(getClass().getResourceAsStream(getIconLoc(player.getPlayerId(), 0)));
				player.getPoint().getMark().setGraphic(new ImageView(image));
			} else {
				Alert quit = new Alert(AlertType.INFORMATION);
				quit.setTitle("消息");
				quit.setHeaderText(null);
				quit.setContentText(name + "期待您的再次光临！");
				quit.showAndWait();
			}
		}

	}

	private void upgradeLand(Player player) {
		if (level == 5) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("欢迎回到" + name);
			alert.setHeaderText("当前土地等级" + level + "级,土地价格" + price + "元");
			alert.setContentText(name + "欢迎您回来，当前房屋满级");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("欢迎回到" + name);
			alert.setHeaderText("当前土地等级" + level + "级,土地价格" + price + "元,升级费用" + upPrice + "元");
			alert.setContentText("您是否希望升级房屋");

			ButtonType buttonTypeOne = new ButtonType("升级");
			ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne) {
				if (upPrice > player.getCash()) {
					Alert quit = new Alert(AlertType.INFORMATION);
					quit.setTitle("消息");
					quit.setHeaderText(null);
					quit.setContentText("您的现金不足以升级土地," + name + "期待您的再次回来！");
					quit.showAndWait();
				} else {
					level++;
					player.addCash(-upPrice);
					price += upPrice;
					Alert quit = new Alert(AlertType.INFORMATION);
					quit.setTitle("消息");
					quit.setHeaderText(null);
					quit.setContentText("升级成功！当前等级" + level + "级," + name + "期待您的再次回来！");
					quit.showAndWait();
					Image image = new Image(getClass().getResourceAsStream(getIconLoc(player.getPlayerId(), level)));
					player.getPoint().getMark().setGraphic(new ImageView(image));
				}
			} else {
				Alert quit = new Alert(AlertType.INFORMATION);
				quit.setTitle("消息");
				quit.setHeaderText(null);
				quit.setContentText(name + "期待您的再次光临！");
				quit.showAndWait();
			}
		}

	}

	// 过路费
	private void toll(Player player, ArrayList<ArrayList<Location>> streets) {
		String con = "";

		double toll = price * 0.3;
		double addition = 0;
		int streetNum = point.getStreetID();
		ArrayList<Location> street = streets.get(streetNum);
		for (Location loc : street) {
			if (loc instanceof Land) {
				Land next = (Land) loc;
				if (next.getOwner() == owner && next != this) {
					addition += next.getPrice() * 0.1;
				}
			}
		}
		toll += addition;

		if (player.getCash() >= toll) {
			player.addCash(-toll);
			owner.addCash(toll);
			con += "您已支付了" + toll + "元过路费(其中街道加成" + addition + "元)" + "\n您的现金余额为" + player.getCash() + "\n谢谢,期待您的再次光临！";
		} else if (player.getCash() + player.getDeposit() >= toll) {
			player.addDeposit(-toll + player.getCash());
			player.addCash(-player.getCash());
			owner.addCash(toll);
			con += "您已支付了" + toll + "元过路费(其中街道加成" + addition + "元)" + "\n您的现金余额为0,您的存款余额为" + player.getDeposit()
					+ "\n谢谢,期待您的再次光临！";
		} else {
			double toll1 = toll - player.getCash() - player.getDeposit();
			owner.addCash(toll - toll1);
			player.addCash(-player.getCash());
			player.addDeposit(-player.getDeposit());

			ArrayList<Location> lands = player.getLands();
			ArrayList<Location> tem = player.getLands();
			boolean give = false;
			for (int i = 0; i < lands.size(); i++) {
				player.addCash(((Land) lands.get(i)).getPrice());
				((Land) lands.get(i)).setOwner(null);
				tem.add(((Land) lands.get(i)));
				player.addHouseNum(-1);
				con += "您已经变卖了" + name + ",获得了现金" + ((Land) lands.get(i)).getPrice() + "\n";

				int temLevel = ((Land) lands.get(i)).getLevel();
				Image image = new Image(getClass().getResourceAsStream(getIconLoc(0, temLevel)));
				lands.get(i).getPoint().getMark().setGraphic(new ImageView(image));

				if (player.getCash() >= toll1) {
					give = true;
					player.addCash(-toll1);
					owner.addCash(toll);
					con += "您已支付了" + toll + "元过路费(其中街道加成" + addition + "元)" + "\n您的存款余额为0,您的现金余额为" + player.getCash()
							+ "\n谢谢,期待您的再次光临！";
					break;
				}
			}
			Consumer<Location> del = e -> lands.remove(e);
			tem.forEach(del);
			if (!give) {
				owner.addCash(player.getCash());
				player.addCash(-player.getCash());
				player.setStepResult(StepResult.fail);
				con += "您付不起过路费，破产了。。";
			}
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("欢迎光临" + name);
		alert.setHeaderText("欢迎来到 " + name + ",该房屋属于" + owner.getName() + "您需要支付过路费" + toll + "元");
		alert.setContentText(con);
		alert.showAndWait();

	}

	private String getIconLoc(int playerID, int level) {
		String re = "";
		if (playerID == 1) {
			switch (level) {
			case 0:
				re = "icons/A0.png";
				break;
			case 1:
				re = "icons/A1.png";
				break;
			case 2:
				re = "icons/A2.png";
				break;
			case 3:
				re = "icons/A3.png";
				break;
			case 4:
				re = "icons/A4.png";
				break;
			case 5:
				re = "icons/A5.png";
				break;
			}
		} else if (playerID == 2) {
			switch (level) {
			case 0:
				re = "icons/B0.png";
				break;
			case 1:
				re = "icons/B1.png";
				break;
			case 2:
				re = "icons/B2.png";
				break;
			case 3:
				re = "icons/B3.png";
				break;
			case 4:
				re = "icons/B4.png";
				break;
			case 5:
				re = "icons/B5.png";
				break;
			}
		} else if (playerID == 3) {
			switch (level) {
			case 0:
				re = "icons/C0.png";
				break;
			case 1:
				re = "icons/C1.png";
				break;
			case 2:
				re = "icons/C2.png";
				break;
			case 3:
				re = "icons/C3.png";
				break;
			case 4:
				re = "icons/C4.png";
				break;
			case 5:
				re = "icons/C5.png";
				break;
			}
		} else if (playerID == 4) {
			switch (level) {
			case 0:
				re = "icons/D0.png";
				break;
			case 1:
				re = "icons/D1.png";
				break;
			case 2:
				re = "icons/D2.png";
				break;
			case 3:
				re = "icons/D3.png";
				break;
			case 4:
				re = "icons/D4.png";
				break;
			case 5:
				re = "icons/D5.png";
				break;
			}
		} else if (playerID == 0) {
			switch (level) {
			case 0:
				re = "icons/N0.png";
				break;
			case 1:
				re = "icons/N1.png";
				break;
			case 2:
				re = "icons/N2.png";
				break;
			case 3:
				re = "icons/N3.png";
				break;
			case 4:
				re = "icons/N4.png";
				break;
			case 5:
				re = "icons/N5.png";
				break;
			}
		}
		return re;
	}
        
        public void changeIcon(){
            Image image = new Image(getClass().getResourceAsStream(getIconLoc(owner.getPlayerId(), 0)));
            owner.getPoint().getMark().setGraphic(new ImageView(image));
        }
        
}
