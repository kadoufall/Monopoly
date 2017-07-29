package kadoufall.monopoly.stock;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Stock {
	private String name = "";
	private int id = 0;
	private double price = 0;
	private double rate = 0;
	private String ratePrint = "";
	private int inventory = 1000;
	private int A = 0;
	private int B = 0;
	private int C = 0;
	private int D = 0;
	private Map<String, Double> trend = new LinkedHashMap<String, Double>();
	private Map<Integer, Double> cost = new LinkedHashMap<Integer, Double>();
	private String aveCost = "";

	public Stock() {
		this("", 0, -1);
	}

	public Stock(String name, double price, int id) {
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
		this.setRate(0);
		this.resetInventory();
		this.setA(0);
		this.setB(0);
		this.setC(0);
		this.setD(0);
		this.setRatePrint("");
		this.setAveCost("---");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double i) {
		this.price = i;
		/*
		 * double re = price * (1 + getRate() * 0.01); String tem =
		 * String.format("%.2f", re); re = Double.valueOf(tem); this.price = re;
		 */
	}

	public void changePrice() {
		double re = price * (1 + getRate() * 0.01);
		String tem = String.format("%.2f", re);
		re = Double.valueOf(tem);
		this.price = re;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		if (rate >= 10) {
			rate = 10;
		}
		if (rate <= -10) {
			rate = -10;
		}
		String tem = String.format("%.2f", rate);
		rate = Double.valueOf(tem);
		this.rate = rate;
	}

	public String getRatePrint() {
		return ratePrint;
	}

	public void setRatePrint(String ratePrint) {
		this.ratePrint = ratePrint;
	}

	public void changeRatePrint() {
		double re = getRate();
		String tem = String.valueOf(re) + "%";
		if (this.rate >= 0) {
			this.ratePrint = "+" + tem;
		} else {
			this.ratePrint = tem;
		}
	}

	public int getInventory() {
		return inventory;
	}

	public void addInventory(int i) {
		this.inventory = this.inventory + i;
	}

	public void resetInventory() {
		this.inventory = 1000;
	}

	public int getA() {
		return A;
	}

	public void setA(int a) {
		A = a;
	}

	public int getB() {
		return B;
	}

	public void setB(int b) {
		B = b;
	}

	public int getC() {
		return C;
	}

	public void setC(int c) {
		C = c;
	}

	public int getD() {
		return D;
	}

	public void setD(int d) {
		D = d;
	}

	public Map<String, Double> getTrend() {
		return trend;
	}

	public void addTrend(String key, double value) {
		this.trend.put(key, value);
	}

	public Map<Integer, Double> getCost() {
		return cost;
	}

	public void addCost(Integer key, double value) {
		this.cost.put(key, value);
	}

	public String getAveCost() {
		return aveCost;
	}

	public void setAveCost(String aveCost) {
		this.aveCost = aveCost;
	}

	public void changeAveCost() {
		int num = 0;
		double mon = 0;
		for (Map.Entry<Integer, Double> entry : cost.entrySet()) {
			mon += entry.getValue();
			num += entry.getKey();
		}
		double cc = mon / num;
		String tem = String.format("%.2f", cc);
		this.aveCost = tem;
	}

}
