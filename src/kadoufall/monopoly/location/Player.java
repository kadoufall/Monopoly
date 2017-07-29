package kadoufall.monopoly.location;

import java.math.BigDecimal;
import java.util.ArrayList;

import kadoufall.monopoly.application.Point;
import kadoufall.monopoly.card.AverageRichCard;
import kadoufall.monopoly.card.BuyCard;
import kadoufall.monopoly.card.Card;
import kadoufall.monopoly.card.Dice;
import kadoufall.monopoly.card.GiveCard;
import kadoufall.monopoly.card.GiveCoupon;
import kadoufall.monopoly.card.PlunderCard;
import kadoufall.monopoly.card.Roadblock;
import kadoufall.monopoly.card.TaxInspectionCard;
import kadoufall.monopoly.card.TurnCard;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Player extends Location {
	private int playerId = 0;
	private Direction direction = Direction.cw;
	private double cash = 0;
	private double deposit = 0;
	private int coupon = 0;
	private int houseNum = 0;
	private double property = 0;
	private int step = 0;
	private StepResult stepResult = StepResult.normal;
	private ArrayList<Card> cardsList = new ArrayList<Card>();
	private ArrayList<Location> lands = new ArrayList<Location>();
	private boolean useDice = false;
	private ArrayList<Integer> stockNum = new ArrayList<Integer>();
	private double stockProperty = 0;
	private String icon = "";
	private String largeIcon = "";
	private boolean hurt = false;

	// 新建玩家的时候将玩家加到某一个point内
	public Player(String name, int playerID, Point point, String icon, String largeIcon) {
		super(name, point);
		addCash(10000);
		addDeposit(10000);
		addCoupon(50);
		addHouseNum(0);
		setProperty(getProperty());
		setStep(0);
		setPlayerId(playerID);
		point.addLocation(this);
		addCard(new TurnCard());
		addCard(new AverageRichCard());
		addCard(new Roadblock());
		addCard(new TaxInspectionCard());
		addCard(new BuyCard());
		addCard(new PlunderCard());
		addCard(new Dice());
		for (int i = 0; i < 10; i++) {
			stockNum.add(0);
		}
		setIcon(icon);
		setLargeIcon(largeIcon);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
		if (playerId == 1 || playerId == 3) {
			direction = Direction.cw;
		} else {
			direction = Direction.ccw;
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCash() {
		BigDecimal bg = new BigDecimal(cash);
		double re = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return re;
	}

	public void addCash(double cash) {
		BigDecimal bg = new BigDecimal(cash);
		double re = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.cash += re;
	}

	public double getDeposit() {
		BigDecimal bg = new BigDecimal(deposit);
		double re = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return re;
	}

	public void addDeposit(double deposit) {
		BigDecimal bg = new BigDecimal(deposit);
		double re = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.deposit += re;
	}

	public int getCoupon() {
		return coupon;
	}

	public void addCoupon(int coupon) {
		this.coupon += coupon;
	}

	public int getHouseNum() {
		return houseNum;
	}

	public void addHouseNum(int houseNum) {
		this.houseNum += houseNum;
	}

	public void setProperty(double property) {
		this.property = property;
	}

	public double getProperty() {
		return cash + deposit + stockProperty;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public StepResult getStepResult() {
		return stepResult;
	}

	public void setStepResult(StepResult stepResult) {
		this.stepResult = stepResult;
	}

	public ArrayList<Card> getCardsList() {
		return cardsList;
	}

	public void setCardsList(ArrayList<Card> cardsList) {
		this.cardsList = cardsList;
	}

	public void addCard(Card card) {
		getCardsList().add(card);
	}

	public void removeCard(Card card) {
		getCardsList().remove(card);
	}

	public ArrayList<Location> getLands() {
		return lands;
	}

	public void addLands(Location land) {
		lands.add(land);
	}

	public boolean isUseDice() {
		return useDice;
	}

	public void setUseDice(boolean useDice) {
		this.useDice = useDice;
	}

	public ArrayList<Integer> getStockNum() {
		return stockNum;
	}

	public void addStockNum(int id, int num) {
		int num1 = this.stockNum.get(id);
		this.stockNum.set(id, num1 + num);
	}

	public double getStockProperty() {
		return stockProperty;
	}

	public void setStockProperty(double stockProperty) {
		this.stockProperty = stockProperty;
	}

	public void addStockProperty(double i) {
		BigDecimal bg = new BigDecimal(i);
		double re = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.stockProperty = this.stockProperty + re;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLargeIcon() {
		return largeIcon;
	}

	public void setLargeIcon(String largeIcon) {
		this.largeIcon = largeIcon;
	}

	public boolean isHurt() {
		return hurt;
	}

	public void setHurt(boolean hurt) {
		this.hurt = hurt;
	}

	@Override
	public void operation(Player player) {

	}

	public void step(ArrayList<Point> points, ArrayList<ArrayList<Location>> streets, ArrayList<Player> players) {
		int i = 1;
		Location barricade = null;
		for (; i < step; i++) {
			Point nextpoint = point.getPointAt(points, point, direction, i);
			boolean toNext = true;
			if (nextpoint.hasBarricade() != null) {
				toNext = false;
				barricade = nextpoint.hasBarricade();
			}
			if (!toNext) {
				break;
			}
			ArrayList<Location> temLoc = nextpoint.getLocations();
			for (Location loc : temLoc) {
				if (loc instanceof Bank) {
					((Bank) loc).operation(this);
					break;
				}
			}
		}
		Point topoint = point.getPointAt(points, point, direction, i);
		if (barricade != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("消息");
			alert.setHeaderText(null);
			alert.setContentText("糟糕！遇到路障了=_=");
			alert.showAndWait();
			topoint.removeLocation(barricade);
			topoint.setIcon();
		}
		moveTo(points, topoint, streets, players);
	}

	private void moveTo(ArrayList<Point> points, Point movingpoint, ArrayList<ArrayList<Location>> streets,
			ArrayList<Player> players) {
		point.removeLocation(this);
		point.setIcon();
		movingpoint.addLocation(this);
		movingpoint.setIcon();
		point = movingpoint;
		ArrayList<Location> temLoc = point.getLocations();
		for (Location loc : temLoc) {
			if (loc instanceof Bank) {
				((Bank) loc).operation(this);
				break;
			} else if (loc instanceof GiveCard) {
				((GiveCard) loc).operation(this);
				break;
			} else if (loc instanceof GiveCoupon) {
				((GiveCoupon) loc).operation(this);
				break;
			} else if (loc instanceof Land) {
				((Land) loc).operation(this, streets);
				break;
			} else if (loc instanceof Lottery) {
				((Lottery) loc).operation(this);
				break;
			} else if (loc instanceof News) {
				((News) loc).operation(this, players);
				if (hurt) {
					hurt(points);
				}

				break;
			} else if (loc instanceof Space) {
				((Space) loc).operation(this);
				break;
			} else if (loc instanceof Store) {
				((Store) loc).operation(this);
				break;
			} else if (loc instanceof Hospital) {
				((Hospital) loc).operation(this);
				break;
			}
		}
	}

	public void hurt(ArrayList<Point> points) {
		point.removeLocation(this);
		point.setIcon();
		Point hospital = null;
		boolean find = false;
		for (Point p : points) {
			ArrayList<Location> temLoc = p.getLocations();
			for (Location loc : temLoc) {
				if (loc instanceof Hospital) {
					hospital = p;
					find = true;
					break;
				}
			}
			if (find) {
				break;
			}
		}
		direction = Direction.ccw;
		hospital.addLocation(this);
		hospital.setIcon();
		point = hospital;
	}

}
