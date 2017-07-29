package kadoufall.monopoly.application;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import kadoufall.monopoly.stock.*;
import kadoufall.monopoly.card.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kadoufall.monopoly.location.*;

// Map的控制类
public class Controller implements Initializable {
    // 当前玩家
    private Player currentPlayer = null;

    // 玩家数组，街道数组，投降玩家列表,判断受伤的数组
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<ArrayList<Location>> streets = new ArrayList<ArrayList<Location>>();
    private ArrayList<Player> giveUp = new ArrayList<Player>();
    private ArrayList<Integer> roundForHurt = new ArrayList<Integer>();

    // 当前时间
    private LocalDate date = LocalDate.now();

    // 股票数组
    private ArrayList<Stock> stocks = new ArrayList<Stock>();

    // 骰子
    @FXML Label dice;

    // 状态栏
    @FXML Label state;

    // 信息栏
    @FXML Label gameInfo;
    @FXML Label playerInfo_icon;
    @FXML Label playerInfo_name;
    @FXML Label playerInfo_cash;
    @FXML Label playerInfo_cash_num;
    @FXML Label playerInfo_deposit;
    @FXML Label playerInfo_deposit_num;
    @FXML Label playerInfo_coupon;
    @FXML Label playerInfo_coupon_num;
    @FXML Label playerInfo_stock;
    @FXML Label playerInfo_stock_num;
    @FXML Label playerInfo_property;
    @FXML Label playerInfo_property_num;

    // 按顺时针排列的点，共62个
    private ArrayList<Point> points = new ArrayList<Point>();// 所有点的集合
    @FXML Point Cell_110; @FXML Point Cell_108; @FXML Point Cell_106; @FXML Point Cell_104;
    @FXML Point Cell_63; @FXML Point Cell_78; @FXML Point Cell_77; @FXML Point Cell_75;
    @FXML Point Cell_74; @FXML Point Cell_70; @FXML Point Cell_71; @FXML Point Cell_69;
    @FXML Point Cell_68; @FXML Point Cell_32; @FXML Point Cell_31; @FXML Point Cell_30;
    @FXML Point Cell_29; @FXML Point Cell_28; @FXML Point Cell_27; @FXML Point Cell_25;
    @FXML Point Cell_24; @FXML Point Cell_22; @FXML Point Cell_52; @FXML Point Cell_57;
    @FXML Point Cell_56; @FXML Point Cell_55; @FXML Point Cell_54; @FXML Point Cell_53;
    @FXML Point Cell_33; @FXML Point Cell_34; @FXML Point Cell_35; @FXML Point Cell_36;
    @FXML Point Cell_37; @FXML Point Cell_38; @FXML Point Cell_39; @FXML Point Cell_40;
    @FXML Point Cell_41; @FXML Point Cell_42; @FXML Point Cell_43; @FXML Point Cell_44;
    @FXML Point Cell_45; @FXML Point Cell_46; @FXML Point Cell_47; @FXML Point Cell_48;
    @FXML Point Cell_49; @FXML Point Cell_50; @FXML Point Cell_51; @FXML Point Cell_81;
    @FXML Point Cell_83; @FXML Point Cell_85; @FXML Point Cell_84; @FXML Point Cell_88;
    @FXML Point Cell_90; @FXML Point Cell_89; @FXML Point Cell_87; @FXML Point Cell_94;
    @FXML Point Cell_95; @FXML Point Cell_97; @FXML Point Cell_96; @FXML Point Cell_98;
    @FXML Point Cell_100; @FXML Point Cell_102;
    // 每个点对应的标志图片
    @FXML Label Cell_109; @FXML Label Cell_107; @FXML Label Cell_103; @FXML Label Cell_73;
    @FXML Label Cell_149; @FXML Label Cell_159; @FXML Label Cell_76; @FXML Label Cell_86;
    @FXML Label Cell_82; @FXML Label Cell_80; @FXML Label Cell_79; @FXML Label Cell_169;
    @FXML Label Cell_21; @FXML Label Cell_179; @FXML Label Cell_189; @FXML Label Cell_18;
    @FXML Label Cell_17; @FXML Label Cell_199; @FXML Label Cell_66; @FXML Label Cell_58;
    @FXML Label Cell_16; @FXML Label Cell_26; @FXML Label Cell_120; @FXML Label Cell_60;
    @FXML Label Cell_9; @FXML Label Cell_121; @FXML Label Cell_23; @FXML Label Cell_62;
    @FXML Label Cell_15; @FXML Label Cell_14; @FXML Label Cell_13; @FXML Label Cell_12;
    @FXML Label Cell_122; @FXML Label Cell_10; @FXML Label Cell_123; @FXML Label Cell_8;
    @FXML Label Cell_7; @FXML Label Cell_67; @FXML Label Cell_124; @FXML Label Cell_65;
    @FXML Label Cell_64; @FXML Label Cell_59; @FXML Label Cell_118; @FXML Label Cell_61;
    @FXML Label Cell_125; @FXML Label Cell_126; @FXML Label Cell_127; @FXML Label Cell_1;
    @FXML Label Cell_128; @FXML Label Cell_3; @FXML Label Cell_4; @FXML Label Cell_139;
    @FXML Label Cell_6; @FXML Label Cell_91; @FXML Label Cell_130; @FXML Label Cell_129;
    @FXML Label Cell_92; @FXML Label Cell_166; @FXML Label Cell_93; @FXML Label Cell_119;
    @FXML Label Cell_99; @FXML Label Cell_101;
    @FXML
    private Font x3;

    // 初始化
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 将cell加入数组中，设置cell对应的mark标签，设置cell的街道号，将初始的Location加入Cell中
        points.add(Cell_110);Cell_110.setMark(Cell_109);Cell_110.setStreetID(0);Cell_110.addLocation(new Land("邯郸路1号",Cell_110));
        points.add(Cell_108);Cell_108.setMark(Cell_107);Cell_108.setStreetID(0);Cell_108.addLocation(new Land("邯郸路22号",Cell_108));
        points.add(Cell_106);Cell_106.setMark(Cell_103);Cell_106.setStreetID(0);Cell_106.addLocation(new Land("邯郸路220号",Cell_106));
        points.add(Cell_104);Cell_104.setMark(Cell_73);Cell_104.setStreetID(0);Cell_104.addLocation(new Land("邯郸路440号",Cell_104));
        points.add(Cell_63);Cell_63.setMark(Cell_149);Cell_63.setStreetID(0);Cell_63.addLocation(new Land("邯郸路550号",Cell_63));
        points.add(Cell_78);Cell_78.setMark(Cell_159);Cell_78.setStreetID(1);Cell_78.addLocation(new Bank("人民银行(国权路支行)",Cell_78));
        points.add(Cell_77);Cell_77.setMark(Cell_76);Cell_77.setStreetID(1);Cell_77.addLocation(new Land("国权路120号",Cell_77));
        points.add(Cell_75);Cell_75.setMark(Cell_86);Cell_75.setStreetID(1);Cell_75.addLocation(new Land("国权路550号",Cell_75));
        points.add(Cell_74);Cell_74.setMark(Cell_82);Cell_74.setStreetID(1);Cell_74.addLocation(new Land("国权路600号",Cell_74));
        points.add(Cell_70);Cell_70.setMark(Cell_80);Cell_70.setStreetID(1);Cell_70.addLocation(new Land("国权路1222号",Cell_70));
        points.add(Cell_71);Cell_71.setMark(Cell_79);Cell_71.setStreetID(2);Cell_71.addLocation(new Land("苴国路2号",Cell_71));
        points.add(Cell_69);Cell_69.setMark(Cell_169);Cell_69.setStreetID(2);Cell_69.addLocation(new Land("苴国路50号",Cell_69));
        points.add(Cell_68);Cell_68.setMark(Cell_21);Cell_68.setStreetID(2);Cell_68.addLocation(new Land("苴国路510号",Cell_68));
        points.add(Cell_32);Cell_32.setMark(Cell_179);Cell_32.setStreetID(2);Cell_32.addLocation(new Hospital("大富翁总医院",Cell_32));
        points.add(Cell_31);Cell_31.setMark(Cell_189);Cell_31.setStreetID(2);Cell_31.addLocation(new Store("百货商店（苴国路店）",Cell_31));
        points.add(Cell_30);Cell_30.setMark(Cell_18);Cell_30.setStreetID(2);Cell_30.addLocation(new Land("苴国路250号",Cell_30));
        points.add(Cell_29);Cell_29.setMark(Cell_17);Cell_29.setStreetID(2);Cell_29.addLocation(new Land("苴国路2250号",Cell_29));
        points.add(Cell_28);Cell_28.setMark(Cell_199);Cell_28.setStreetID(2);Cell_28.addLocation(new GiveCard("卡卡卡(苴国路店)",Cell_28));
        points.add(Cell_27);Cell_27.setMark(Cell_66);Cell_27.setStreetID(3);Cell_27.addLocation(new Land("绵谷路100号",Cell_37));
        points.add(Cell_25);Cell_25.setMark(Cell_58);Cell_25.setStreetID(3);Cell_25.addLocation(new Land("绵谷路111号",Cell_25));
        points.add(Cell_24);Cell_24.setMark(Cell_16);Cell_24.setStreetID(3);Cell_24.addLocation(new Land("绵谷路122号",Cell_24));
        points.add(Cell_22);Cell_22.setMark(Cell_26);Cell_22.setStreetID(3);Cell_22.addLocation(new Land("绵谷路133号",Cell_22));
        points.add(Cell_52);Cell_52.setMark(Cell_120);Cell_52.setStreetID(3);Cell_52.addLocation(new Bank("人民银行(绵谷路支行)",Cell_52));
        points.add(Cell_57);Cell_57.setMark(Cell_60);Cell_57.setStreetID(4);Cell_57.addLocation(new Land("人民路550号",Cell_57));
        points.add(Cell_56);Cell_56.setMark(Cell_9);Cell_56.setStreetID(4);Cell_56.addLocation(new Land("人民路120号",Cell_56));
        points.add(Cell_55);Cell_55.setMark(Cell_121);Cell_55.setStreetID(4);Cell_55.addLocation(new GiveCoupon("券券券(人民路分店)",Cell_55));
        points.add(Cell_54);Cell_54.setMark(Cell_23);Cell_54.setStreetID(4);Cell_54.addLocation(new Land("人民路250号",Cell_54));
        points.add(Cell_53);Cell_53.setMark(Cell_62);Cell_53.setStreetID(4);Cell_53.addLocation(new Land("人民路350号",Cell_53));
        points.add(Cell_33);Cell_33.setMark(Cell_15);Cell_33.setStreetID(5);Cell_33.addLocation(new Land("成都路0号",Cell_33));
        points.add(Cell_34);Cell_34.setMark(Cell_14);Cell_34.setStreetID(5);Cell_34.addLocation(new Land("成都路230号",Cell_34));
        points.add(Cell_35);Cell_35.setMark(Cell_13);Cell_35.setStreetID(5);Cell_35.addLocation(new Land("成都路530号",Cell_35));
        points.add(Cell_36);Cell_36.setMark(Cell_12);Cell_36.setStreetID(5);Cell_36.addLocation(new Land("成都路1110号",Cell_36));
        points.add(Cell_37);Cell_37.setMark(Cell_122);Cell_37.setStreetID(5);Cell_37.addLocation(new GiveCard("卡卡卡(成都路分店)",Cell_37));
        points.add(Cell_38);Cell_38.setMark(Cell_10);Cell_38.setStreetID(5);Cell_38.addLocation(new Land("成都路1220号",Cell_38));
        points.add(Cell_39);Cell_39.setMark(Cell_123);Cell_39.setStreetID(5);Cell_39.addLocation(new Bank("人民银行(成都路支行)",Cell_39));
        points.add(Cell_40);Cell_40.setMark(Cell_8);Cell_40.setStreetID(5);Cell_40.addLocation(new Land("成都路1550号",Cell_40));
        points.add(Cell_41);Cell_41.setMark(Cell_7);Cell_41.setStreetID(5);Cell_41.addLocation(new Land("成都路2770号",Cell_41));
        points.add(Cell_42);Cell_42.setMark(Cell_67);Cell_42.setStreetID(6);Cell_42.addLocation(new Land("北京路0号",Cell_42));
        points.add(Cell_43);Cell_43.setMark(Cell_124);Cell_43.setStreetID(6);Cell_43.addLocation(new Lottery("彩彩彩(北京路分店)",Cell_43));
        points.add(Cell_44);Cell_44.setMark(Cell_65);Cell_44.setStreetID(6);Cell_44.addLocation(new Land("北京路10号",Cell_44));
        points.add(Cell_45);Cell_45.setMark(Cell_64);Cell_45.setStreetID(6);Cell_45.addLocation(new Land("北京路20号",Cell_45));
        points.add(Cell_46);Cell_46.setMark(Cell_59);Cell_46.setStreetID(6);Cell_46.addLocation(new Land("北京路30号",Cell_46));
        points.add(Cell_47);Cell_47.setMark(Cell_118);Cell_47.setStreetID(6);Cell_47.addLocation(new Space(Cell_47));
        points.add(Cell_48);Cell_48.setMark(Cell_61);Cell_48.setStreetID(6);Cell_48.addLocation(new Land("北京路40号",Cell_48));
        points.add(Cell_49);Cell_49.setMark(Cell_125);Cell_49.setStreetID(6);Cell_49.addLocation(new Store("百货商店(北京路分店)",Cell_49));
        points.add(Cell_50);Cell_50.setMark(Cell_126);Cell_50.setStreetID(6);Cell_50.addLocation(new News("大新闻(北京路分社)",Cell_50));
        points.add(Cell_51);Cell_51.setMark(Cell_127);Cell_51.setStreetID(6);Cell_51.addLocation(new Space(Cell_51));
        points.add(Cell_81);Cell_81.setMark(Cell_1);Cell_81.setStreetID(6);Cell_81.addLocation(new Land("北京路50号",Cell_81));
        points.add(Cell_83);Cell_83.setMark(Cell_128);Cell_83.setStreetID(6);Cell_83.addLocation(new GiveCoupon("券券券(北京路分店)",Cell_83));
        points.add(Cell_85);Cell_85.setMark(Cell_3);Cell_85.setStreetID(6);Cell_85.addLocation(new Land("北京路60号",Cell_85));
        points.add(Cell_84);Cell_84.setMark(Cell_4);Cell_84.setStreetID(6);Cell_84.addLocation(new Land("北京路70号",Cell_84));
        points.add(Cell_88);Cell_88.setMark(Cell_139);Cell_88.setStreetID(6);Cell_88.addLocation(new Land("北京路80号",Cell_88));
        points.add(Cell_90);Cell_90.setMark(Cell_6);Cell_90.setStreetID(6);Cell_90.addLocation(new Land("北京路90号",Cell_90));
        points.add(Cell_89);Cell_89.setMark(Cell_91);Cell_89.setStreetID(6);Cell_89.addLocation(new Land("北京路100号",Cell_89));
        points.add(Cell_87);Cell_87.setMark(Cell_130);Cell_87.setStreetID(7);Cell_87.addLocation(new Land("杭州路1号",Cell_87));
        points.add(Cell_94);Cell_94.setMark(Cell_129);Cell_94.setStreetID(7);Cell_94.addLocation(new Land("杭州路11号",Cell_94));
        points.add(Cell_95);Cell_95.setMark(Cell_92);Cell_95.setStreetID(7);Cell_95.addLocation(new Land("杭州路22号",Cell_95));
        points.add(Cell_97);Cell_97.setMark(Cell_166);Cell_97.setStreetID(7);Cell_97.addLocation(new Bank("人民银行(杭州路支行)",Cell_97));
        points.add(Cell_96);Cell_96.setMark(Cell_93);Cell_96.setStreetID(7);Cell_96.addLocation(new Land("杭州路33号",Cell_96));
        points.add(Cell_98);Cell_98.setMark(Cell_119);Cell_98.setStreetID(7);Cell_98.addLocation(new Lottery("彩彩彩(杭州路分店)",Cell_98));
        points.add(Cell_100);Cell_100.setMark(Cell_99);Cell_100.setStreetID(7);Cell_100.addLocation(new Land("杭州路110号",Cell_100));
        points.add(Cell_102);Cell_102.setMark(Cell_101);Cell_102.setStreetID(7);Cell_102.addLocation(new News("大新闻(杭州路分社)",Cell_102));

        // 设置Point的编号
        for (int i = 0; i < points.size(); i++) {
            points.get(i).setPointID(i);
        }

        // 设置街道
        for (int i = 0; i < 8; i++) {
            streets.add(new ArrayList<Location>());
        }
        for (Point p : points) {
            streets.get(p.getStreetID()).add(p.getLocations().get(0));
        }

        // 初始化玩家、玩家列表
        int playerNum = Monopoly.playerNum;
        for (int i = 0; i < playerNum; i++) {
            switch (i) {
                case 0:
                    players.add(new Player("A", 1, Cell_110, "icons/p1.jpg", "icons/p11.jpg"));
                    break;
                case 1:
                    players.add(new Player("B", 2, Cell_110, "icons/p2.jpg", "icons/p22.jpg"));
                    break;
                case 2:
                    players.add(new Player("C", 3, Cell_110, "icons/p3.jpg", "icons/p33.jpg"));
                    break;
                case 3:
                    players.add(new Player("D", 4, Cell_110, "icons/p4.jpg", "icons/p44.jpg"));
                    break;
            }
            roundForHurt.add(0);
        }

        // 初始化当前玩家
        currentPlayer = players.get(0);

        // 初始化起点的图标
        currentPlayer.getPoint().setIcon();


        // 初始化右侧信息栏与底部状态栏
        changeInfo(currentPlayer);
        changeDate();
        warn();

        // 初始化股票
        stocks.add(new Stock("壳牌石油", 40, 0));
        stocks.add(new Stock("诺贝尔", 150, 1));
        stocks.add(new Stock("宝丽金", 25, 2));
        stocks.add(new Stock("联合利华", 20, 3));
        stocks.add(new Stock("亨氏", 100, 4));
        stocks.add(new Stock("哈雷戴维森", 55, 5));
        stocks.add(new Stock("IBM公司", 90, 6));
        stocks.add(new Stock("瞻博网路", 35, 7));
        stocks.add(new Stock("摩根大通", 30, 8));
        stocks.add(new Stock("美赞臣", 120, 9));
        for (Stock s : stocks) {
            Random ran = new Random();
            double rate = ran.nextDouble() * (-11 - 10) + 11;
            s.setRate(rate);
            s.changePrice();
            s.changeRatePrint();
            s.addTrend(date.toString(), s.getPrice());
        }

        // 初始化骰子图
        int i = (int) (Math.random() * 6);
        String url = "";
        switch (i) {
            case 0:
                url = "icons/dice1.png";
                break;
            case 1:
                url = "icons/dice2.png";
                break;
            case 2:
                url = "icons/dice3.png";
                break;
            case 3:
                url = "icons/dice4.png";
                break;
            case 4:
                url = "icons/dice5.png";
                break;
            case 5:
                url = "icons/dice6.png";
                break;
        }
        Image image = new Image(getClass().getResourceAsStream(url));
        dice.setGraphic(new ImageView(image));

    }

    // 菜单栏：玩家信息
    @FXML
    private void playerInfo(ActionEvent e) {
        PlayerInfo tem = new PlayerInfo(players);
        Stage stage = new Stage();
        tem.start(stage);
    }

    // 菜单栏：位置信息
    @FXML
    private void locInfo(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("查看位置信息");
        dialog.setHeaderText(null);
        dialog.setContentText("您想查看前后哪一步的具体信息(后方用负数表示)");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int step = Integer.parseInt(result.get());
                Point see;
                if (step > points.size() || step < -points.size()) {
                    Alert warn = new Alert(AlertType.WARNING);
                    warn.setTitle("错误");
                    warn.setHeaderText(null);
                    warn.setContentText("您的输入有误,请重新输入");
                    warn.showAndWait();
                    locInfo(e);
                } else {
                    String con = "";
                    if (step >= 0) {
                        see = currentPlayer.getPoint().getPointAt(points, currentPlayer.getPoint(),
                                currentPlayer.getDirection(), step);
                    } else {
                        see = currentPlayer.getPoint().getPointAt(points, currentPlayer.getPoint(),
                                Direction.negative(currentPlayer.getDirection()), -step);
                    }
                    boolean hasBarricade = false;
                    ArrayList<Location> cellLoc = see.getLocations();
                    for (int i = 0; i < cellLoc.size(); i++) {
                        if (cellLoc.get(i) instanceof Barricade) {
                            hasBarricade = true;
                        } else if (!(cellLoc.get(i) instanceof Player)) {
                            con += "   名称：" + cellLoc.get(i).getName() + "\n";
                            if (cellLoc.get(i) instanceof Land) {
                                if (((Land) cellLoc.get(i)).getOwner() == null) {
                                    con += "   拥有者：无主\n";
                                } else {
                                    con += "   拥有者：" + ((Land) cellLoc.get(i)).getOwner().getName() + "\n";
                                }
                                con += "   等级：" + ((Land) cellLoc.get(i)).getLevel();
                                con += "   价格：" + ((Land) cellLoc.get(i)).getPrice();
                            }
                        }
                    }
                    if (hasBarricade) {
                        con += "   路障：有";
                    }
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("信息");
                    alert.setHeaderText(null);
                    alert.setContentText(con);
                    alert.showAndWait();
                    locInfo(e);
                }

            } catch (Exception ex) {
                Alert warn = new Alert(AlertType.WARNING);
                warn.setTitle("错误");
                warn.setHeaderText(null);
                warn.setContentText("您的输入有误,请重新输入");
                warn.showAndWait();
                locInfo(e);
            }

        }

    }

    // 菜单栏：使用卡片
    @FXML
    private void useCard(ActionEvent e) {
        ArrayList<Card> cards = currentPlayer.getCardsList();
        List<String> choices = new ArrayList<>();
        for (Card c : cards) {
            choices.add(c.getName());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(cards.get(0).getName(), choices);
        dialog.setTitle("选择");
        dialog.setHeaderText(null);
        dialog.setContentText("请选择需要使用的道具");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String choice = result.get();
            for (Card c : cards) {
                if (choice.equals(c.getName())) {
                    boolean use = c.useCard(points, currentPlayer, players);
                    if (!use) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("消息");
                        alert.setHeaderText(null);
                        alert.setContentText("使用道具失败");
                        alert.showAndWait();
                    } else {
                        currentPlayer.removeCard(c);
                    }
                    break;
                }
            }
            hasCard(e);
        }
    }

    // 菜单栏：丢弃卡片
    @FXML
    private void discard(ActionEvent e) {
        ArrayList<Card> cards = currentPlayer.getCardsList();
        List<String> choices = new ArrayList<>();
        for (Card c : cards) {
            choices.add(c.getName());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(cards.get(0).getName(), choices);
        dialog.setTitle("选择");
        dialog.setHeaderText(null);
        dialog.setContentText("请选择需要丢弃的道具");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String choice = result.get();
            for (Card c : cards) {
                if (choice.equals(c.getName())) {
                    currentPlayer.removeCard(c);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("消息");
                    alert.setHeaderText(null);
                    alert.setContentText("丢弃成功");
                    alert.showAndWait();
                    break;
                }
            }
            hasCard(e);
        }
    }

    // 菜单栏：查看 拥有的卡片
    @FXML
    private void hasCard(ActionEvent e) {
        String info = "";
        ArrayList<Card> cards = currentPlayer.getCardsList();
        for (int i = 0; i < cards.size(); i++) {
            info += i + "." + cards.get(i).getName() + "\n";
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("信息");
        alert.setHeaderText("拥有的卡片");
        alert.setContentText(info);
        alert.showAndWait();
    }

    // 菜单栏：查看卡片信息
    @FXML
    private void seeCard(ActionEvent e) {
        String info = "";
        Card[] buyCard = new Card[7];
        buyCard[0] = new TurnCard();
        buyCard[1] = new AverageRichCard();
        buyCard[2] = new Roadblock();
        buyCard[3] = new TaxInspectionCard();
        buyCard[4] = new BuyCard();
        buyCard[5] = new PlunderCard();
        buyCard[6] = new Dice();

        for (Card c : buyCard) {
            info += c.getName() + ":" + c.getInformation() + "\n\n";
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("信息");
        alert.setHeaderText("卡片信息");
        alert.setContentText(info);
        alert.showAndWait();
    }

    // 菜单栏：股票
    @FXML
    private void stock(ActionEvent e) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("信息");
            alert.setHeaderText(null);
            alert.setContentText("周末股市休市！");
            alert.showAndWait();
        } else {
            StartStock tem = new StartStock(currentPlayer, players, date, stocks);
            Stage stage = new Stage();
            try {
                tem.start(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            stage.setOnCloseRequest((ee) -> {
                changeInfo(currentPlayer);
            });
        }
    }

    // 菜单栏：认输
    @FXML
    private void givein(ActionEvent e) {
        players.remove(currentPlayer);
        currentPlayer.setStepResult(StepResult.fail);
        giveUp.add(currentPlayer);
        for (int k = 0; k < currentPlayer.getLands().size(); k++) {
            ((Land) currentPlayer.getLands().get(k)).setOwner(null);
        }
        currentPlayer.getPoint().removeLocation(currentPlayer);
        currentPlayer.getLands().clear();
        if (players.size() == 1) {
            Alert alert = new Alert(AlertType.INFORMATION, players.get(0).getName() + "获胜!", ButtonType.YES);
            alert.setHeaderText("GAME OVER!");
            alert.showAndWait();
            System.exit(0);
        }

    }

    // 点击骰子的事件
    @FXML
    private void Dice(MouseEvent event) {
        Object o = event.getSource();
        Label tem = (Label) o;

        if (currentPlayer.isHurt()) {
            int temInt = roundForHurt.get(currentPlayer.getPlayerId() - 1);
            temInt++;
            roundForHurt.set(currentPlayer.getPlayerId() - 1, temInt);
            Alert alert = new Alert(AlertType.INFORMATION, "您受伤了，不能游戏", ButtonType.YES);
            alert.setHeaderText(null);
            alert.showAndWait();
            if (temInt == 2) {
                currentPlayer.setHurt(false);
                roundForHurt.set(currentPlayer.getPlayerId() - 1, 0);
            }
        } else {
            if (!currentPlayer.isUseDice()) {
                int random = (int) (Math.random() * 6) + 1;
                Alert alert = new Alert(AlertType.INFORMATION, "您投掷的点数为" + random, ButtonType.YES);
                alert.setHeaderText(null);
                alert.showAndWait();
                currentPlayer.setStep(random);
            } else {
                Alert alert = new Alert(AlertType.INFORMATION, "系统检测到您已经使用遥控骰子", ButtonType.YES);
                alert.setHeaderText(null);
                alert.showAndWait();
                currentPlayer.setUseDice(false);
            }

            int step = currentPlayer.getStep();
            String url = "";
            switch (step - 1) {
                case 0:
                    url = "icons/dice1.png";
                    break;
                case 1:
                    url = "icons/dice2.png";
                    break;
                case 2:
                    url = "icons/dice3.png";
                    break;
                case 3:
                    url = "icons/dice4.png";
                    break;
                case 4:
                    url = "icons/dice5.png";
                    break;
                case 5:
                    url = "icons/dice6.png";
                    break;
            }
            Image image = new Image(getClass().getResourceAsStream(url));
            tem.setGraphic(new ImageView(image));
            currentPlayer.step(points, streets, players);
        }
        currentPlayer = nextPlayer();
        changeInfo(currentPlayer);
        warn();
    }

    // 计算下一个玩家
    private Player nextPlayer() {
        Player re = null;
        for (int i = 0; i < players.size(); i++) {
            if (currentPlayer == players.get(i)) {
                if (i + 1 < players.size()) {
                    re = players.get(i + 1);
                } else {
                    re = players.get(0);
                    changeDate();
                    if (date.getDayOfMonth() == 1) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("消息");
                        alert.setHeaderText(null);
                        alert.setContentText("月底银行发利息啦！得到存款的10%");
                        alert.showAndWait();
                        for (Player p : players) {
                            p.addDeposit(players.get(i).getDeposit() * 0.1);
                        }
                    }
                }
                break;
            }
        }
        return re;
    }

    // 改变右侧信息栏
    private void changeInfo(Player player) {
        Image image = new Image(getClass().getResourceAsStream(player.getLargeIcon()));
        playerInfo_icon.setGraphic(new ImageView(image));
        playerInfo_name.setText("  " + player.getName());
        playerInfo_cash_num.setText(String.valueOf(player.getCash()));
        playerInfo_deposit_num.setText(String.valueOf(player.getDeposit()));
        playerInfo_coupon_num.setText(String.valueOf(player.getCoupon()));
        playerInfo_stock_num.setText(String.valueOf(player.getStockProperty()));
        playerInfo_property_num.setText(String.valueOf(player.getProperty()));
    }

    // 改变日期
    private void changeDate() {
        date = date.plusDays(1);
        gameInfo.setText("\n " + date.toString() + "\n\n " + date.getDayOfWeek().toString());
        for (Stock s : stocks) {
            Random ran = new Random();
            double rate = ran.nextDouble() * (-11 - 10) + 11;
            s.setRate(rate);
            s.changePrice();
            s.changeRatePrint();
            s.addTrend(date.toString(), s.getPrice());
            s.resetInventory();
        }
    }

    // 底部状态栏
    private void warn() {
        String con = "";

        boolean hasBarricade = false;
        for (int i = 1; i < 11; i++) {
            Point cell = currentPlayer.getPoint().getPointAt(points, currentPlayer.getPoint(),
                    currentPlayer.getDirection(), i);
            for (int j = 0; j < cell.getLocations().size(); j++) {
                if (cell.getLocations().get(j) instanceof Barricade) {
                    con += "前方" + i + "步有路障！";
                    hasBarricade = true;
                    break;
                } else if (cell.getLocations().get(j) instanceof Land) {
                    Land tem = (Land) cell.getLocations().get(j);
                    if (tem.getOwner() != null && tem.getOwner() != currentPlayer) {
                        con += "前方" + i + "步需要缴纳过路费！";
                        hasBarricade = true;
                        break;
                    }
                }
            }
            if (hasBarricade) {
                break;
            }
        }
        if (!hasBarricade) {
            con += "前方十步内一片坦途!";
        }
        state.setText(con);

    }

}
