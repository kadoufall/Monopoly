package kadoufall.monopoly.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import kadoufall.monopoly.location.*;

//自建的位置
public class Point extends VBox {
	// 位置的标签，位置的标志标签,位置ID，位置所在的街道ID，位置内的Location数组
	@FXML private Label label;
	private Label mark;
	private int pointID;
	private int streetID;
	private String address;
	private ArrayList<Location> locations = new ArrayList<Location>();

	public Point() {
		super();
		// 位置的UI设计在Point.fxml中
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Point.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		setPointID(0);
		setStreetID(0);
		setAddress("");
		setMark(null);
	}

	public Label getLabel() {
		return label;
	}

	public int getPointID() {
		return pointID;
	}

	public void setPointID(int pointID) {
		this.pointID = pointID;
	}

	public int getStreetID() {
		return streetID;
	}

	public void setStreetID(int streetID) {
		this.streetID = streetID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Label getMark() {
		return mark;
	}

	public void setMark(Label mark) {
		this.mark = mark;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void addLocation(Location location) {
		this.locations.add(location);
	}

	// 获得指定方向、步数的位置
	public Point getPointAt(ArrayList<Point> points, Point point, Direction direction, int step) {
		Point re = null;
		int toId = 0;
		int pointNum = points.size();
		switch (direction) {
		case cw:
			toId = pointID + step;
			if (toId >= pointNum) {
				toId -= pointNum;
			}
			break;
		case ccw:
			toId = pointID - step;
			if (toId < 0) {
				toId += pointNum;
			}
			break;
		}
		for (Point p : points) {
			if (p.getPointID() == toId) {
				re = p;
				break;
			}
		}
		return re;
	}

	// 判断当前位置是否有路障，若有返回
	public Location hasBarricade() {
		Location re = null;
		for (Location loc : locations) {
			if (loc instanceof Barricade) {
				re = loc;
				break;
			}
		}
		return re;
	}

	// 删除某个Location
	public void removeLocation(Location loc) {
		locations.remove(loc);
	}

	// 设置位置自身的标签
	public void setIcon() {
		Predicate<Location> filter = (p) -> (p instanceof Player || p instanceof Barricade);
		Location tem = locations.stream().filter(filter).findFirst().orElse(null);
		if(tem == null){
			label.setGraphic(null);
		}else{
			if(tem instanceof Player){
				Player tem1 = (Player)tem;
				Image image = new Image(getClass().getResourceAsStream(tem1.getIcon()));
				label.setGraphic(new ImageView(image));
			}else{
				Image image = new Image(getClass().getResourceAsStream("icons/barrage.png"));
				label.setGraphic(new ImageView(image));
			}
		}
	}


}
