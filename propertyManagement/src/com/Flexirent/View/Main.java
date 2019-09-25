package com.Flexirent.View;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Flexirent.Controller.RoomBeanController;
import com.Flexirent.Model.RentBean;
import com.Flexirent.Model.RoomBean;
import com.utils.DateUtil;
import com.utils.FileWriteAndRead;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	static RoomBeanController roomBeanController = new RoomBeanController();

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			ScrollPane scrollPane = new ScrollPane();

			GridPane pane = new GridPane();
			GridPane pane1 = new GridPane();
			Label lbeFlexirent  = new Label("Flexirent Room            "
					+ "                                                                             ");
			lbeFlexirent.setFont(Font.font("Timer New Roman",
					FontWeight.BOLD, FontPosture.ITALIC, 25));
			Button btnAdd = new Button("Add Property");
			btnAdd.setOnAction(e -> addHotel());
			pane1.add(lbeFlexirent, 1, 1);
			pane1.add(btnAdd, 2, 1);
			pane.add(pane1, 1, 1);
			
			GridPane pane2 = new GridPane();
			
			
			Label lbe =  new Label("                                                   ");
			Label lbe2 = new Label("                                                   ");
			Label lbeRoomNumber = new Label("BEDROOM");
			Label lbeType = new Label("TYPE");
			Label lbeStatus = new Label("STATUS");
			Label lbeSuburb = new Label("Suburb");
			TextField textField = new TextField();
			TextField textSuburb = new TextField();
			@SuppressWarnings("rawtypes")
			ComboBox cobType = new ComboBox();
			cobType.getItems().addAll("Suite", "Apartment");
			@SuppressWarnings("rawtypes")
			ComboBox cobStatus = new ComboBox();
			cobStatus.getItems().addAll("Available", "Rent", "Maintain");
			Button btnSearch = new Button("Search");
			pane2.add(lbe, 1, 1);
			pane2.add(lbeRoomNumber, 2, 1);
			pane2.add(lbeType, 3, 1);
			pane2.add(lbeStatus, 4, 1);
			pane2.add(lbeSuburb, 5, 1);
			
			pane2.add(lbe2, 1, 2);
			pane2.add(textField, 2, 2);
			pane2.add(cobType, 3, 2);
			pane2.add(cobStatus, 4, 2);
			pane2.add(textSuburb, 5, 2);
			pane2.add(btnSearch, 6, 2);
			
			pane.add(pane2, 1, 2);
			
			GridPane pane3 = new GridPane();
			
			btnSearch.setOnAction(e -> getDetails(primaryStage, pane3, (String) textField.getText(),
					(String) cobType.getValue(), (String) cobStatus.getValue(), (String) textSuburb.getText()));
			

			MenuBar menuBar = new MenuBar();
			menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

			Menu fileMenu = new Menu("File");
			MenuItem ImportMenuItem = new MenuItem("Import Data");
			MenuItem ExportMenuItem = new MenuItem("Export Data");
			MenuItem DeleteMenuItem = new MenuItem("Delete All property");
			MenuItem ExitMenuItem = new MenuItem("Exit");
			ExitMenuItem.setOnAction(actionEvent -> Platform.exit());
			ImportMenuItem.setOnAction(actionEvent -> {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					System.out.println(file.getPath());
					;
					String imputDateStr = FileWriteAndRead.readFileByPath(file.getPath());
					System.out.println(imputDateStr);
					String[] rows = imputDateStr.split("\n");
					for (int i = 0; i < rows.length; i++) {
						String[] rowData = rows[i].split(":");
						String roomidid = "";
						if (rowData.length == 8) {
							roomidid = roomBeanController.imputInsertRoom(rowData);
						}
						if (rowData.length == 6) {
							roomBeanController.insertRent(rowData[0], rowData[1], rowData[2], rowData[3], rowData[4],
									rowData[5], roomidid);
						}
					}
					f_alert_informationDialog("message", "Input completed!", primaryStage);
				}
			});
			ExportMenuItem.setOnAction(actionEvent -> {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File file = directoryChooser.showDialog(primaryStage);
				String path = file.getPath();
				System.out.println(path);
				String strData = "";
				List<RoomBean> roomlist = roomBeanController.getDetails("", "", "", "");
				for (int i = 0; i < roomlist.size(); i++) {
					strData += roomlist.get(i).toString() + "\n";
					List<RentBean> rentlist = roomBeanController.getRentBeanByRoomid(roomlist.get(i).getId());
					for (RentBean rent : rentlist) {
						strData += rent.toString() + "\n";
					}

				}

				FileWriteAndRead.writerFileByLine(strData, path + "\\export_data.txt");

				f_alert_informationDialog("message", "Export completed!", primaryStage);
			});

			fileMenu.getItems().addAll(ImportMenuItem, ExportMenuItem, new SeparatorMenuItem(), DeleteMenuItem,
					ExitMenuItem);

			menuBar.getMenus().addAll(fileMenu);

//			pane.add(lbeRoomNumber, 1, 1);
//			pane.add(lbeType, 1, 2);
//			pane.add(lbeStatus, 1, 3);
//			pane.add(textField, 2, 1);
//			pane.add(cobType, 2, 2);
//			pane.add(cobStatus, 2, 3);

			root.setTop(menuBar);
			scrollPane.setContent(pane);
			
//			pane.add(lbeSuburb, 1, 4);
//			pane.add(textSuburb, 2, 4);
//			pane.add(btnAdd, 1, 5);
//			pane.add(btnSearch, 2, 5);
			getDetails(primaryStage, pane3, "", "", "", "");
			GridPane pane4 = new GridPane();
			Label lbeBlank = new Label("   ");
			pane4.add(lbeBlank, 1, 1);
			pane.add(pane4, 1, 3);
			pane.add(pane3, 1, 4);
			primaryStage.setTitle("Start Your Journey from Flexi");

			root.setCenter(scrollPane);

			Scene scene = new Scene(root, 1100, 700);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getDetails(Stage primaryStage, GridPane pane, String roomNumber, String type, String status,
			String suburb) {
		List<RoomBean> roomdata = roomBeanController.getDetails(roomNumber, type, status, suburb);

		pane.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 5);
		for (int i = 0; i < roomdata.size(); i++) {
			String imagePath = roomdata.get(i).getImage();
			String description = roomdata.get(i).getDescription();
			String roomid = roomdata.get(i).getId();
			String strstatus = roomdata.get(i).getStatus();
			String strtype = roomdata.get(i).getType();
			String strRoomNumber = roomdata.get(i).getRoomNumber();
			String strMaintaindate = roomdata.get(i).getMaintaindate();
			String strStretName = roomdata.get(i).getStretName();
			String strStretNunber = roomdata.get(i).getStretNunber();
			String strSuburb = roomdata.get(i).getSuburb();
			
			
			
			
			File file = new File(roomdata.get(i).getImage());
			Image image = new Image(file.toURI().toString());

			ImageView imv = new ImageView(image);
			Label labe1 = new Label(roomdata.get(i).getStatus()+"                                                        ");
			
			String strDescription=roomdata.get(i).getDescription();
			if(strDescription.length()>52&&strDescription.length()<104){
				Label Descriptionlabe1 = new Label(strDescription.substring(0,52));
				Label Descriptionlabe2 = new Label(strDescription.substring(52,strDescription.length()));
				GridPane pane2=new GridPane();
				pane2.add(Descriptionlabe1, 1, 1);
				pane2.add(Descriptionlabe2, 1, 2);
				pane.add(pane2, i % 3 + 1, 8 + (i / 3) * 3);
				
			}else if(strDescription.length()>103){
				Label Descriptionlabe1 = new Label(strDescription.substring(0,52));
				Label Descriptionlabe2 = new Label(strDescription.substring(52,103));
				Label Descriptionlabe3 = new Label(strDescription.substring(103,strDescription.length()));
				GridPane pane2=new GridPane();
				pane2.add(Descriptionlabe1, 1, 1);
				pane2.add(Descriptionlabe2, 1, 2);
				pane2.add(Descriptionlabe3, 1, 3);
				pane.add(pane2, i % 3 + 1, 8 + (i / 3) * 3);
				
			}else{
				Label labe2 = new Label(strDescription);
				pane.add(labe2, i % 3 + 1, 8 + (i / 3) * 3);
			}
			
			
			Button btnLookInfo = new Button("VIEW");
			GridPane pane1=new GridPane();
			pane1.add(labe1, 1, 1);
			pane1.add(btnLookInfo, 2, 1);
			
			
			
			btnLookInfo.setOnAction(
					e -> LookInfo(imagePath, description, roomid, strstatus, strtype, strRoomNumber, strMaintaindate,strStretName,strStretNunber,strSuburb));
			pane.add(imv, i % 3 + 1, 6 + (i / 3) * 3);
			pane.add(pane1, i % 3 + 1, 7 + (i / 3) * 3);
		}
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	public static void LookInfo(String imagePath, String description, String roomid, String strstatus, String type,
			String roomNumber, String maintaindate, String stretName, String stretNunber,String suburb) {
		// create a new stage
		Stage secondStage = new Stage();

		GridPane pane = new GridPane();
		GridPane pane1 = new GridPane();
		
		
		File file = new File(imagePath);
		Image image = new Image(file.toURI().toString());

		ImageView imv = new ImageView(image);
		
		pane1.add(imv, 1, 1);
		if(description.length()>52){
			Label Descriptionlabe1 = new Label(description.substring(0,52));
			Label Descriptionlabe2 = new Label(description.substring(52,description.length()));
			GridPane pane2=new GridPane();
			pane2.add(Descriptionlabe1, 1, 1);
			pane2.add(Descriptionlabe2, 1, 2);
			pane1.add(pane2, 2, 1);
			
		}else{
			Label label = new Label(description);
			pane1.add(label, 2, 1);
		}
		GridPane pane3 = new GridPane();
		Label labelpane3_1 = new Label(type +" "+strstatus);
		
		Label labelpane3_2 = new Label(stretNunber+"  "+stretName
				+" Street, " +suburb);
		Label labelpane3_3 = new Label("$"+priceBytype(type,roomNumber));
		Label labelpane3_4 = new Label(roomNumber+" Bedrooms");
		pane3.add(labelpane3_1, 1, 1);
		pane3.add(labelpane3_2, 1, 2);
		pane3.add(labelpane3_3, 1, 3);
		pane3.add(labelpane3_4, 1, 4);
		
		
		
		
		
		Button btnChangeRent = new Button("Book Now");
		Button btnReturn = new Button("Check Out");

		

		Button btnChangeMaintain = new Button("Maintain Property");
		btnChangeMaintain.setOnAction(e -> {
				addMaintaindate(roomid);
			
		});
		Button btnChangeAvailable = new Button("Complete Maintenance");
		btnChangeAvailable.setOnAction(e -> {
			roomBeanController.updateStatus("available", roomid);
			f_alert_informationDialog("message", "Maintenance completed!", secondStage);
		});
		RentBean rentBean = roomBeanController.getRentBeanNoReturnByRoomid(roomid);
		btnReturn.setOnAction(e -> addReturn(rentBean));

		if (rentBean != null) {
			btnChangeRent.setDisable(true);
		} else {
			btnReturn.setDisable(true);
		}
		if(strstatus.equals("Maintain")){
			btnChangeRent.setDisable(true);
			btnReturn.setDisable(true);
		}
		btnChangeRent.setOnAction(e ->{
			addRent(roomid, secondStage, type, roomNumber, maintaindate);
			
		
		});
		pane3.add(btnChangeRent, 1, 5);
		pane3.add(btnReturn, 1, 6);
		pane3.add(btnChangeMaintain, 1, 7);
		pane3.add(btnChangeAvailable, 1, 8);
		
		TableView<RentBean> dataTable = new TableView<RentBean>();
		VBox vbox = getVBoxTable(dataTable, roomid);

		pane3.add(vbox, 1, 9);
		pane.add(pane1, 1, 1);
		pane.add(pane3, 1, 2);
		
		secondStage.setTitle("LookInfo");
		Scene secondScene = new Scene(pane, 1250, 600);
		secondStage.setScene(secondScene);
		secondStage.show();

	}

	private static VBox getVBoxTable(TableView<RentBean> dataTable, String roomid) {

		ObservableList data = getTableList(roomid);
		final Label label = new Label("RENTAL_RECORD list");
		label.setFont(new Font("Arial", 20));

		TableColumn col1 = new TableColumn("id");
		col1.setCellValueFactory(new PropertyValueFactory<RentBean, String>("id"));
		col1.setMinWidth(120);

		TableColumn col2 = new TableColumn("rentDate");
		col2.setCellValueFactory(new PropertyValueFactory<RentBean, String>("rentDate"));
		col2.setMinWidth(120);
		TableColumn col3 = new TableColumn("estimatedReturnDate");
		col3.setCellValueFactory(new PropertyValueFactory<RentBean, String>("estimatedReturnDate"));
		col3.setMinWidth(120);


		TableColumn col6 = new TableColumn("actualRetuenDate");
		col6.setCellValueFactory(new PropertyValueFactory<RentBean, String>("actualRetuenDate"));
		col6.setMinWidth(120);

		TableColumn col7 = new TableColumn("rentalFee");
		col7.setCellValueFactory(new PropertyValueFactory<RentBean, String>("rentalFee"));
		col7.setMinWidth(120);

		TableColumn col8 = new TableColumn("lateFee");
		col8.setCellValueFactory(new PropertyValueFactory<RentBean, String>("lateFee"));
		col8.setMinWidth(120);

		TableColumn col9 = new TableColumn("customerName");
		col9.setCellValueFactory(new PropertyValueFactory<RentBean, String>("customerName"));
		col9.setMinWidth(120);

		TableColumn col10 = new TableColumn("stretNunber");
		col10.setCellValueFactory(new PropertyValueFactory<RentBean, String>("stretNunber"));
		col10.setMinWidth(120);

		dataTable.setItems(data);

		dataTable.getColumns().addAll(col1, col2, col3, col6, col7, col8, col9, col10);
		dataTable.setMinSize(800, 600);
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.getChildren().addAll(label, dataTable);
		vbox.setPadding(new Insets(10, 0, 0, 10));

		return vbox;
	}

	private static ObservableList getTableList(String Roomid) {
		ObservableList data = null;
		List<RentBean> RentBeanList = roomBeanController.getRentBeanByRoomid(Roomid);
		data = FXCollections.observableList(RentBeanList);

		return data;

	}

	@SuppressWarnings("unchecked")
	public static void addRent(String roomid, Stage stage, String type, String roomNumber, String maintaindate) {
		if (roomBeanController.getRoomBeanByid(roomid).getStatus().equals("available")) {
			// create a new stage
			Stage secondStage = new Stage();

			GridPane pane = new GridPane();
			Label lbe0 = new Label("maintaindate:");
			Label lbe1 = new Label("rentDate");
			Label lbe2 = new Label("estimatedReturnDate");
			Label lbe6 = new Label("customerName");
			pane.add(lbe0, 1, 1);
			pane.add(lbe1, 1, 2);
			pane.add(lbe2, 1, 3);
			pane.add(lbe6, 1, 4);

			DatePicker date1 = new DatePicker();
			DatePicker date2 = new DatePicker();
			TextField textField6 = new TextField();
			Label txt2 = new Label(maintaindate);
			pane.add(txt2, 2, 1);
			pane.add(date1, 2, 2);
			pane.add(date2, 2, 3);
			pane.add(textField6, 2, 4);

			Button btnSave = new Button("Save");
			Button btnCancel = new Button("Cancel");

			pane.add(btnSave, 1, 9);
			pane.add(btnCancel, 2, 9);
			btnSave.setOnAction(e -> {
				LocalDate localDate1 = date1.getValue();
				ZoneId zoneId1 = ZoneId.systemDefault();
				ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId1);
				Date dateR1 = Date.from(zdt1.toInstant());

				LocalDate localDate2 = date2.getValue();
				ZoneId zoneId2 = ZoneId.systemDefault();
				ZonedDateTime zdt2 = localDate2.atStartOfDay(zoneId2);
				Date dateR2 = Date.from(zdt2.toInstant());
				int sumFee = feeCountSum(type, DateUtil.differentDays(dateR1, dateR2), roomNumber);
				if (maintaindate != null && !maintaindate.equals("")) {
					try {
						Date maintaindateD = new SimpleDateFormat("yyyy-MM-dd").parse(maintaindate);
						if (dateR2.after(maintaindateD) && dateR1.before(maintaindateD)) {
							f_alert_informationDialog("message",
									"Sorry, due to maintenance, it's not available during this period of time.",
									secondStage);
						} else {
							if (roomBeanController.insertRent(date1.getValue() + "", date2.getValue() + "", "",
									sumFee + "", "", (String) textField6.getText(), roomid)) {
								if (f_alert_informationDialog("message", "Property successfully added!", secondStage)) {
									roomBeanController.updateStatus("rent", roomid);
									secondStage.close();
								}

							} else {
								f_alert_informationDialog("message", "Oops! Something is wrong.", secondStage);
							}
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}

				} else {
					if (roomBeanController.insertRent(date1.getValue() + "", date2.getValue() + "", "", sumFee + "", "",
							(String) textField6.getText(), roomid)) {
						if (f_alert_informationDialog("message", "Property successfully added!", secondStage)) {
							secondStage.close();
						}
						roomBeanController.updateStatus("rent", roomid);

					} else {
						f_alert_informationDialog("message", "Oops! Something is wrong.", secondStage);
					}
				}

			});

			btnCancel.setOnAction(e -> addCancel(secondStage));
			secondStage.setTitle("Book Now");
			Scene secondScene = new Scene(pane, 600, 400);
			secondStage.setScene(secondScene);
			secondStage.show();
		} else {
			f_alert_informationDialog("message", "Sorry, it is not available.", stage);
		}

	}

	@SuppressWarnings("unchecked")
	public static void addReturn(RentBean rentBean) {
		Stage secondStage = new Stage();

		GridPane pane = new GridPane();
		Label lbe1 = new Label("actualRetuenDate");
		Label lbe5 = new Label("lateFee");
		pane.add(lbe1, 1, 1);
		pane.add(lbe5, 1, 2);

		DatePicker date1 = new DatePicker();
		TextField textField5 = new TextField();
		textField5.setDisable(true);

		pane.add(date1, 2, 1);
		pane.add(textField5, 2, 3);

		Button btnSave = new Button("Save");
		Button btnCancel = new Button("Cancel");

		pane.add(btnSave, 1, 9);
		pane.add(btnCancel, 2, 9);
		btnSave.setOnAction(e -> {
			LocalDate localDate1 = date1.getValue();
			ZoneId zoneId1 = ZoneId.systemDefault();
			ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId1);
			Date dateR1 = Date.from(zdt1.toInstant());
			Date estimatedReturnDate = null;
			Date rentDate = null;
			try {
				estimatedReturnDate = new SimpleDateFormat("yyyy-MM-dd").parse(rentBean.getEstimatedReturnDate());

				rentDate=new SimpleDateFormat("yyyy-MM-dd").parse(rentBean.getRentDate());
				
				if(DateUtil.differentDays(rentDate, dateR1)<getRentday(rentBean.getRentDate())||DateUtil.differentDays(rentDate, dateR1)>28){
					f_alert_informationDialog("message", "Minimum stay: 2 days (from Sun to Thu)\n 3 days (from Fri to Sat) \n Maimum stay:28 days", secondStage);
				}else{
					if (roomBeanController.updateRent(
							date1.getValue() + "", leteFeeCountSum(rentBean.getType(),
									DateUtil.differentDays(estimatedReturnDate, dateR1), rentBean.getRoomNumber()) + "",
							rentBean.getId())) {
						if (f_alert_informationDialog("message", "Property successfully returned!", secondStage)) {
							secondStage.close();
						}
						roomBeanController.updateStatus("available", rentBean.getRoomid());

					} else {
						f_alert_informationDialog("message", "Oops! Something is wrong.", secondStage);
					}
				}
				
				
				
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnCancel.setOnAction(e -> addCancel(secondStage));
		secondStage.setTitle("Check Out");
		Scene secondScene = new Scene(pane, 600, 400);
		secondStage.setScene(secondScene);
		secondStage.show();

	}

	@SuppressWarnings("unchecked")
	public static void addMaintaindate(String roomid) {
		Stage secondStage = new Stage();

		GridPane pane = new GridPane();
		Label lbe1 = new Label("maintaindate");
		pane.add(lbe1, 1, 1);

		DatePicker date1 = new DatePicker();
		pane.add(date1, 2, 1);

		Button btnSave = new Button("Save");
		Button btnCancel = new Button("Cancel");

		pane.add(btnSave, 1, 9);
		pane.add(btnCancel, 2, 9);
		btnSave.setOnAction(e -> {

			RoomBean roomBean=roomBeanController.getRoomBeanByid(roomid);
			String Maintaindate=roomBean.getMaintaindate();
			if(Maintaindate!=null){
				Date nowMaintaindate = null;
				try {
					nowMaintaindate = new SimpleDateFormat("yyyy-MM-dd").parse(Maintaindate);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				LocalDate localDate1 = date1.getValue();
				ZoneId zoneId1 = ZoneId.systemDefault();
				ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId1);
				Date dateR1 = Date.from(zdt1.toInstant());
				if(roomBean.getType().equals("Suite")){
					if(DateUtil.differentDays(nowMaintaindate, dateR1)<10){
						if (roomBeanController.updateMaintaindate(date1.getValue() + "", roomid)) {
							if (f_alert_informationDialog("message", "The property is now under maintenance!", secondStage)) {
								secondStage.close();
							}
							roomBeanController.updateStatus("available", roomid);

						} else {
							f_alert_informationDialog("message", "Oops! Something is wrong.", secondStage);
						}
					}else {
						f_alert_informationDialog("message", "Maximum 10 days interval of maintenance ", secondStage);
					}
				}else{
					if (roomBeanController.updateMaintaindate(date1.getValue() + "", roomid)) {
						if (f_alert_informationDialog("message", "The property is now under maintenance!", secondStage)) {
							secondStage.close();
						}
						roomBeanController.updateStatus("available", roomid);

					} else {
						f_alert_informationDialog("message", "Oops! Something is wrong.", secondStage);
					}
				}
				
				
			}else{
				if (roomBeanController.updateMaintaindate(date1.getValue() + "", roomid)) {
					if (f_alert_informationDialog("message", "The property is now under maintenance!", secondStage)) {
						secondStage.close();
					}
					roomBeanController.updateStatus("available", roomid);

				} else {
					f_alert_informationDialog("message", "Oops! Something is wrong.", secondStage);
				}
			}
			
		

		});

		btnCancel.setOnAction(e -> addCancel(secondStage));
		secondStage.setTitle("Maintenance Startdate:");
		Scene secondScene = new Scene(pane, 600, 400);
		secondStage.setScene(secondScene);
		secondStage.show();

	}

	@SuppressWarnings("unchecked")
	public static void addHotel() {
		// create a new stage
		Stage secondStage = new Stage();

		GridPane pane = new GridPane();
		Label lbeRoomNumber = new Label("Number of bedroom");
		Label lbeType = new Label("Type");
		Label lbeStretNunber = new Label("Street Number");
		Label lbeStretName = new Label("Street Name");
		Label lbeSuburb = new Label("Suburb");
		Label lbeDescription = new Label("Description");
		pane.add(lbeRoomNumber, 1, 1);
		pane.add(lbeType, 1, 2);
		pane.add(lbeStretNunber, 1, 3);
		pane.add(lbeStretName, 1, 4);
		pane.add(lbeSuburb, 1, 5);
		pane.add(lbeDescription, 1, 6);

		TextField textRoomNumber = new TextField();
		TextField textStretNunber = new TextField();
		TextField textStretName = new TextField();
		@SuppressWarnings("rawtypes")
		ComboBox cobType = new ComboBox();
		cobType.getItems().addAll("Suite", "Apartment");

		TextField textStretSuburb = new TextField();
		TextField textDescription = new TextField();
		pane.add(textRoomNumber, 2, 1);
		pane.add(cobType, 2, 2);
		pane.add(textStretNunber, 2, 3);
		pane.add(textStretName, 2, 4);
		pane.add(textStretSuburb, 2, 5);
		pane.add(textDescription, 2, 6);

		Button btnSave = new Button("Save and Upload image");
		Button btnCancel = new Button("Cancel");

		pane.add(btnSave, 1, 9);
		pane.add(btnCancel, 2, 9);
		btnSave.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(secondStage);
			if (file != null) {
				System.out.println(file.getPath());
				;
				FileWriteAndRead.copyFile(file.getPath(), "image\\" + file.getName());
				if (roomBeanController.insertRoom((String) cobType.getValue(), (String) textStretNunber.getText(),
						(String) textStretName.getText(), (String) textRoomNumber.getText(),
						(String) textStretSuburb.getText(), "available", "image\\" + file.getName(),
						(String) textDescription.getText())) {
					if (f_alert_informationDialog("message", "The property is successfully added!", secondStage)) {
						secondStage.close();
					}

				} else {
					f_alert_informationDialog("message", "Oops! Something is wrong.", secondStage);
				}
			} else {
				if (roomBeanController.insertRoom((String) cobType.getValue(), (String) textStretNunber.getText(),
						(String) textStretName.getText(), (String) textRoomNumber.getText(),
						(String) textStretSuburb.getText(), "available", "image\\noimage.png",
						(String) textDescription.getText())) {
					if (f_alert_informationDialog("message", "The property is successfully added!", secondStage)) {
						secondStage.close();
					}

				} else {
					f_alert_informationDialog("message", "Oops! Something is wrong.", secondStage);
				}
			}
		});

		btnCancel.setOnAction(e -> addCancel(secondStage));
		secondStage.setTitle("add hotel");
		Scene secondScene = new Scene(pane, 600, 400);
		secondStage.setScene(secondScene);
		secondStage.show();

	}

	public static void saveHotel(String type, String stretNunber, String stretName, String roomNumber, String suburb,
			String status, String image, String description, Stage d_stage) {
		if (roomBeanController.insertRoom(type, stretNunber, stretName, roomNumber, suburb, status, image,
				description)) {
			if (f_alert_informationDialog("message", "The property is successfully added!", d_stage)) {
				d_stage.close();
			}

		} else {
			f_alert_informationDialog("message", "Oops! Something is wrong.", d_stage);
		}
	}

	public static void saveRent(String rentDate, String estimatedReturnDate, String actualRetuenDate, String rentalFee,
			String lateFee, String customerName, String roomid, Stage d_stage) {
		if (roomBeanController.insertRent(rentDate, estimatedReturnDate, actualRetuenDate, rentalFee, lateFee,
				customerName, roomid)) {
			f_alert_informationDialog("message", "successfully added", d_stage);
			roomBeanController.updateStatus("rent", roomid);

		} else {
			f_alert_informationDialog("message", "Oops! Something is wrong.", d_stage);
		}
	}

	public static void addCancel(Stage stage) {
		stage.close();
	}

	public static boolean f_alert_informationDialog(String p_header, String p_message, Stage d_stage) {
		Alert _alert = new Alert(Alert.AlertType.CONFIRMATION, p_message,
				new ButtonType("Cancel", ButtonBar.ButtonData.NO), new ButtonType("Confirm", ButtonBar.ButtonData.YES));
		_alert.setTitle("message");
		_alert.setHeaderText(p_header);
		_alert.setContentText(p_message);
		_alert.initOwner(d_stage);
		Optional<ButtonType> _buttonType = _alert.showAndWait();
		if (_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
			return true;
		} else {
			return false;
		}
	}

	// Fee count
	public static int feeCountSum(String type, int days, String roomNumber) {
		int sum = 0;
		if (type.equals("Suite")) {
			sum = days * 554;
		} else if (type.equals("Apartment")) {
			if (roomNumber.equals("1")) {
				sum = days * 143;
			}
			if (roomNumber.equals("2")) {
				sum = days * 210;
			}
			if (roomNumber.equals("3")) {
				sum = days * 319;
			}
		}
		return sum;
	}
	public static int priceBytype(String type,String roomNumber) {
		int sum = 0;
		if (type.equals("Suite")) {
			sum =  554;
		} else if (type.equals("Apartment")) {
			if (roomNumber.equals("1")) {
				sum = 143;
			}
			if (roomNumber.equals("2")) {
				sum = 210;
			}
			if (roomNumber.equals("3")) {
				sum =  319;
			}
		}
		return sum;
	}

	// late fee
	public static double leteFeeCountSum(String type, int days, String roomNumber) {
		double sum = 0;
		if (type.equals("Suite")) {
			sum = days * 662;
		} else if (type.equals("Apartment")) {
			if (roomNumber.equals("1")) {
				sum = days * 143 * 1.15;
			}
			if (roomNumber.equals("2")) {
				sum = days * 210 * 1.15;
			}
			if (roomNumber.equals("3")) {
				sum = days * 319 * 1.15;
			}
		}
		return sum;
	}
	
	public static int getRentday(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
       // String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); 
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int days=0;
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; 
        if (w < 0)
            w = 0;
        
        if(w>=0&&w<=4){
        	days=2;
        }else{
        	days=3;
        }
        
        return days;
    }
	

}