package kadoufall.monopoly.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import kadoufall.monopoly.location.Player;

// 玩家信息
public class PlayerInfo extends Application {
	ArrayList<Player> players = new ArrayList<Player>();

	public static final String Column1MapKey = "A";
	public static final String Column2MapKey = "B";
	public static final String Column3MapKey = "C";
	public static final String Column4MapKey = "D";
	public static final String Column5MapKey = "E";
	public static final String Column6MapKey = "F";
	public static final String Column7MapKey = "G";

	public PlayerInfo(ArrayList<Player> players) {
		this.players = players;
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("玩家信息");
		stage.setWidth(600);
		stage.setHeight(300);

		final Label label = new Label("玩家信息");
		label.setFont(new Font("Arial", 20));

		TableColumn<Map, String> name = new TableColumn<>("姓名");
		TableColumn<Map, String> cash = new TableColumn<>("现金");
		TableColumn<Map, String> deposit = new TableColumn<>("存款");
		TableColumn<Map, String> coupon = new TableColumn<>("点券");
		TableColumn<Map, String> houseNum = new TableColumn<>("房产");
		TableColumn<Map, String> stock = new TableColumn<>("股票");
		TableColumn<Map, String> propert = new TableColumn<>("资产");

		name.setCellValueFactory(new MapValueFactory(Column1MapKey));
		name.setMinWidth(40);
		cash.setCellValueFactory(new MapValueFactory(Column2MapKey));
		cash.setMinWidth(60);
		deposit.setCellValueFactory(new MapValueFactory(Column3MapKey));
		deposit.setMinWidth(60);
		coupon.setCellValueFactory(new MapValueFactory(Column4MapKey));
		coupon.setMinWidth(40);
		houseNum.setCellValueFactory(new MapValueFactory(Column5MapKey));
		houseNum.setMinWidth(40);
		stock.setCellValueFactory(new MapValueFactory(Column6MapKey));
		stock.setMinWidth(60);
		propert.setCellValueFactory(new MapValueFactory(Column7MapKey));
		propert.setMinWidth(60);

		TableView tableView = new TableView<>(generateDataInMap());

		tableView.setEditable(false);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		tableView.getColumns().setAll(name, cash, deposit, coupon, houseNum, stock, propert);
		Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap = (
				TableColumn<Map, String> p) -> new TextFieldTableCell(new StringConverter() {
					@Override
					public String toString(Object t) {
						return t.toString();
					}

					@Override
					public Object fromString(String string) {
						return string;
					}
				});
		name.setCellFactory(cellFactoryForMap);
		cash.setCellFactory(cellFactoryForMap);
		deposit.setCellFactory(cellFactoryForMap);
		coupon.setCellFactory(cellFactoryForMap);
		houseNum.setCellFactory(cellFactoryForMap);
		stock.setCellFactory(cellFactoryForMap);
		propert.setCellFactory(cellFactoryForMap);

		final VBox vbox = new VBox();

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, tableView);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	private ObservableList<Map> generateDataInMap() {
		ObservableList<Map> allData = FXCollections.observableArrayList();
		for (Player p : players) {
			Map<String, String> dataRow = new HashMap<>();
			dataRow.put(Column1MapKey, p.getName());
			dataRow.put(Column2MapKey, String.valueOf(p.getCash()));
			dataRow.put(Column3MapKey, String.valueOf(p.getDeposit()));
			dataRow.put(Column4MapKey, String.valueOf(p.getCoupon()));
			dataRow.put(Column5MapKey, String.valueOf(p.getHouseNum()));
			dataRow.put(Column6MapKey, String.valueOf(p.getStockProperty()));
			dataRow.put(Column7MapKey, String.valueOf(p.getProperty()));
			allData.add(dataRow);
		}

		return allData;
	}
}