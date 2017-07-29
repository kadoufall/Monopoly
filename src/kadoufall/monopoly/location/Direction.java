package kadoufall.monopoly.location;

public enum Direction {
	cw, ccw; // clockwise,anticlockwise

	public static String toDirection(Direction direction) {
		String re = "";
		switch (direction) {
		case cw:
			re = "顺时针";
			break;
		case ccw:
			re = "逆时针";
			break;
		}
		return re;
	}

	public static Direction negative(Direction direction) {
		switch (direction) {
		case cw:
			return ccw;
		case ccw:
			return cw;
		default:
			return cw;// useless
		}
	}

}
