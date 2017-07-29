package kadoufall.monopoly.location;

import kadoufall.monopoly.application.Point;

public abstract class Location {
	protected String name;
	protected Point point;

	public Location(String name, Point point) {
		this.setName(name);
		this.setPoint(point);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public abstract void operation(Player player);
}
