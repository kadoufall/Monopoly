package kadoufall.monopoly.stock;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import kadoufall.monopoly.location.*;

public class StockController implements Initializable {
	// 玩家数组，日期，股票数组
	private ArrayList<Player> players = null;
	private LocalDate date = null;
	private ArrayList<Stock> stocks = null;
	private Player currentPlayer = null;

	// 表格，折线图
	@FXML
	private TableView<Stock> tableView;
	@FXML
	private LineChart lineChart;

	//
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;

	// 初始化
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 初始化玩家数组，日期，股票数组,表格
		players = StartStock.players;
		date = StartStock.date;
		stocks = StartStock.stocks;
		currentPlayer = StartStock.currentPlayer;
		tableView.setItems(FXCollections.observableArrayList(stocks));

		ObservableList<Stock> stocks = tableView.getItems();

		Stock clickedRow1 = stocks.get(0);
		XYChart.Series series1 = new XYChart.Series();
		series1.setName(clickedRow1.getName());
		Map<String, Double> trend1 = clickedRow1.getTrend();
		for (Map.Entry<String, Double> entry : trend1.entrySet()) {
			series1.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
		}
		lineChart.getData().addAll(series1);

		// 单击更新走势图，双击进行交易
		tableView.setRowFactory(tv -> {
			TableRow<Stock> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (row.getItem() != null) {
					if (event.getClickCount() == 1) {
						lineChart.getData().clear();
						Stock clickedRow = row.getItem();
						XYChart.Series series = new XYChart.Series();
						series.setName(clickedRow.getName());
						Map<String, Double> trend = clickedRow.getTrend();
						for (Map.Entry<String, Double> entry : trend.entrySet()) {
                                                    System.out.println(entry.getKey());
							series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
						}
						lineChart.getData().addAll(series);
					}

					else if (event.getClickCount() == 2) {
						Stock stock = row.getItem();
						if (stock.getRate() == 10) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle(stock.getName());
							alert.setHeaderText(stock.getName());
							alert.setContentText(stock.getName() + "处于涨停状态，不能进行买卖！");
							alert.showAndWait();
						} else if (stock.getRate() == -10) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle(stock.getName());
							alert.setHeaderText(stock.getName());
							alert.setContentText(stock.getName() + "处于跌停状态，不能进行买卖！");
							alert.showAndWait();
						} else {
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle(stock.getName());
							alert.setHeaderText(stock.getName());
							alert.setContentText("请选择操作,退出请选择“取消”");

							ButtonType buttonTypeOne = new ButtonType("买");
							ButtonType buttonTypeTwo = new ButtonType("卖");
							ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

							alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == buttonTypeOne) {
								double maxBuy1 = currentPlayer.getCash() + currentPlayer.getDeposit();
								maxBuy1 /= stock.getPrice();
								int maxBuy = (int) maxBuy1;
								maxBuy = Math.min(maxBuy, stock.getInventory());
								TextInputDialog dialog = new TextInputDialog();
								dialog.setTitle(stock.getName());
								dialog.setHeaderText(null);
								dialog.setContentText("您的现金为" + currentPlayer.getCash() + "元\n您的存款为"
										+ currentPlayer.getDeposit() + "元\n今日剩余库存为" + stock.getInventory()
										+ "股\n系统计算出当前您可以购买的股票数目范围为(0-" + maxBuy + "),请输入需要购买的股数");
								Optional<String> result1 = dialog.showAndWait();
								if (result1.isPresent()) {
									String buyNum = result1.get();
									try {
										int num = Integer.parseInt(buyNum);
										double numPrice = num * stock.getPrice();
										if (num > maxBuy) {
											Alert warn = new Alert(AlertType.WARNING);
											warn.setTitle("错误");
											warn.setHeaderText(null);
											warn.setContentText("您的输入有误!");
											warn.showAndWait();
										} else {
											// 减少现金存款，计算平均成本,减少库存
											stock.addInventory(-num);
											currentPlayer.addStockNum(stock.getId(), num);
											if (numPrice <= currentPlayer.getCash()) {
												currentPlayer.addCash(-numPrice);

											} else {
												currentPlayer.addDeposit(-numPrice + currentPlayer.getCash());
												currentPlayer.addCash(-currentPlayer.getCash());
											}
											stock.addCost(num, stock.getPrice() * num);
											switch (currentPlayer.getName()) {
											case "A":
												stock.setA(stock.getA() + num);
												break;
											case "B":
												stock.setB(stock.getB() + num);
												break;
											case "C":
												stock.setC(stock.getC() + num);
												break;
											case "D":
												stock.setD(stock.getD() + num);
												break;
											}
											stock.changeAveCost();
											currentPlayer.addStockProperty(stock.getPrice() * num);
											Alert success = new Alert(AlertType.INFORMATION);
											success.setTitle(stock.getName());
											success.setHeaderText(stock.getName());
											success.setContentText("购买成功!");
											success.showAndWait();

											for (int i = 0; i < tableView.getColumns().size(); i++) {
												tableView.getColumns().get(i).setVisible(false);
												tableView.getColumns().get(i).setVisible(true);
											}
										}
									} catch (Exception e) {
										Alert warn = new Alert(AlertType.WARNING);
										warn.setTitle("错误");
										warn.setHeaderText(null);
										warn.setContentText("您的输入有误!");
										warn.showAndWait();
									}
								}
							} else if (result.get() == buttonTypeTwo) {
								int has = currentPlayer.getStockNum().get(stock.getId());
								TextInputDialog dialog = new TextInputDialog();
								dialog.setTitle(stock.getName());
								dialog.setHeaderText(null);
								dialog.setContentText("您当前持有的股数为" + has + "\n请输入您要卖出的股数");
								Optional<String> result1 = dialog.showAndWait();
								if (result1.isPresent()) {
									String sellNum = result1.get();
									try {
										int num = Integer.parseInt(sellNum);
										if (num > has) {
											Alert warn = new Alert(AlertType.WARNING);
											warn.setTitle("错误");
											warn.setHeaderText(null);
											warn.setContentText("您的输入有误!");
											warn.showAndWait();
										} else {
											currentPlayer.addStockNum(stock.getId(), -num);
											currentPlayer.addCash(num * stock.getPrice());
											stock.addInventory(num);
											switch (currentPlayer.getName()) {
											case "A":
												stock.setA(stock.getA() - num);
												break;
											case "B":
												stock.setB(stock.getB() - num);
												break;
											case "C":
												stock.setC(stock.getC() - num);
												break;
											case "D":
												stock.setD(stock.getD() - num);
												break;
											}
											currentPlayer.addStockProperty(-stock.getPrice() * num);

											Alert success = new Alert(AlertType.INFORMATION);
											success.setTitle(stock.getName());
											success.setHeaderText(stock.getName());
											success.setContentText("售出成功!");
											success.showAndWait();

											for (int i = 0; i < tableView.getColumns().size(); i++) {
												tableView.getColumns().get(i).setVisible(false);
												tableView.getColumns().get(i).setVisible(true);
											}

										}
									} catch (Exception e) {
										Alert warn = new Alert(AlertType.WARNING);
										warn.setTitle("错误");
										warn.setHeaderText(null);
										warn.setContentText("您的输入有误!");
										warn.showAndWait();
									}
								}
							} else {

							}
						}

					}
				}

			});
			return row;
		});
	}

}
