/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package TaskManagerSystem;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class TaskController implements Initializable {

    @FXML
    private Button fin_btn;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_endDate;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_plan;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_planID;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_startDate;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_status;

    @FXML
    private AnchorPane finishedPlans_form;

    @FXML
    private TextField finishedPlans_planID;

    @FXML
    private ComboBox<?> finishedPlans_status;

    @FXML
    private TableView<taskData> finishedPlans_tableVIew;

    @FXML
    private Button finishedPlans_updateBtn;

    @FXML
    private Label home_FP;

    @FXML
    private Label home_NAP;

    @FXML
    private Button home_btn;

    @FXML
    private Label home_dateRegistered;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_username;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button myPlans_addBtn;

    @FXML
    private Button myPlans_clearBtn;

    @FXML
    private TableColumn<taskData, String> myPlans_col_dateCreated;

    @FXML
    private TableColumn<taskData, String> myPlans_col_endDate;

    @FXML
    private TableColumn<taskData, String> myPlans_col_plan;

    @FXML
    private TableColumn<taskData, String> myPlans_col_startDate;

    @FXML
    private TableColumn<taskData, String> myPlans_col_status;

    @FXML
    private Button myPlans_deleteBtn;

    @FXML
    private DatePicker myPlans_endDate;

    @FXML
    private AnchorPane myPlans_form;

    @FXML
    private TextArea myPlans_plan;

    @FXML
    private DatePicker myPlans_startDate;

    @FXML
    private TableView<taskData> myPlans_tableView;

    @FXML
    private Button myPlans_updateBtn;

    @FXML
    private Label page_label;

    @FXML
    private Button plan_btn;

    @FXML
    private Label username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private Alert alert;

    public void homeDisplayUsername() {

        home_username.setText(username.getText());
    }

    public void homeDisplayDateRegistered() {

        String selectDate = "SELECT date FROM user WHERE username ='"
                + data.username + "'";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(selectDate);
            result = prepare.executeQuery();

            if (result.next()) {
                home_dateRegistered.setText(result.getString("date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayNAP() {

        String sql = "SELECT COUNT(id) FROM mytask WHERE planner ='"
                + username.getText() + "'";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                home_NAP.setText(result.getString("COUNT(id)"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayFP() {
        String sql = "SELECT COUNT(id) FROM mytask WHERE planner ='"
                + username.getText() + "' AND status = 'Finished'";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                home_FP.setText(result.getString("COUNT(id)"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void myTasksAddBtn() {

        connect = database.connectDB();

        try {

            if (myPlans_plan.getText().isEmpty() || myPlans_startDate.getValue() == null
                    || myPlans_endDate.getValue() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all blank fields");
                alert.showAndWait();
            } else {
                if (myPlans_endDate.getValue().isBefore(myPlans_startDate.getValue())) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Date :<");
                    alert.showAndWait();
                } else {

                    String checkTask = "SELECT task FROM mytask WHERE task = '"
                            + myPlans_plan.getText() + "'";

                    prepare = connect.prepareStatement(checkTask);
                    result = prepare.executeQuery();

                    if (result.next()) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Task: " + myPlans_plan.getText() + "is already recorded");
                        alert.showAndWait();
                    } else {
                        String insertData = "INSERT INTO mytask (task, startDate, endDate, dateCreated, status, planner)"
                                + "VALUES(?,?,?,?,?,?)";

                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, myPlans_plan.getText());
                        prepare.setString(2, String.valueOf(myPlans_startDate.getValue()));
                        prepare.setString(3, String.valueOf(myPlans_endDate.getValue()));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(4, String.valueOf(sqlDate));
                        prepare.setString(5, "Not Finish");
                        prepare.setString(6, username.getText());
                        prepare.executeUpdate();

                        myTaskShowData();
                        myTaskClearBtn();
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void myTaskUpdateBtn() {

        connect = database.connectDB();

        try {

            if (taskID == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Task: "
                        + myPlans_plan.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String checkData = "SELECT * FROM mytask WHERE id = " + taskID;

                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);
                    String currentD;
                    if (result.next()) {
                        currentD = result.getString("dateCreated");

                        String updateData = "UPDATE mytask set task = '"
                                + myPlans_plan.getText() + "',  startDate = '"
                                + myPlans_startDate.getValue() + "', endDate = '"
                                + myPlans_endDate.getValue() + "', dateCreated  = '"
                                + currentD + "' WHERE id = " + taskID;

                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated!");
                        alert.showAndWait();

                        myTaskShowData();
                        myTaskClearBtn();
                    }

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void myTaskDeleteBtn() {

        connect = database.connectDB();

        try {

            if (taskID == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Task: "
                        + myPlans_plan.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String checkData = "SELECT * FROM mytask WHERE id = " + taskID;

                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);
                    String currentD;
                    if (result.next()) {
                        currentD = result.getString("dateCreated");

                        String deleteData = "DELETE FROM mytask WHERE id = " + taskID;

                        prepare = connect.prepareStatement(deleteData);
                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Deleted!");
                        alert.showAndWait();

                        myTaskShowData();
                        myTaskClearBtn();
                    }

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void myTaskClearBtn() {
        myPlans_plan.setText("");
        myPlans_startDate.setValue(null);
        myPlans_endDate.setValue(null);
        taskID = 0;
    }

    public ObservableList<taskData> myTaskDataList() {
        ObservableList<taskData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM mytask WHERE planner = '"
                + username.getText() + "'";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            taskData tData;
            while (result.next()) {
                tData = new taskData(result.getInt("id"),
                        result.getString("task"), result.getDate("startDate"), result.getDate("endDate"),
                        result.getDate("dateCreated"), result.getString("status"), result.getString("planner"));

                listData.add(tData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<taskData> myTaskListData;

    public void myTaskShowData() {
        myTaskListData = myTaskDataList();

        myPlans_col_plan.setCellValueFactory(new PropertyValueFactory<>("task"));
        myPlans_col_startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        myPlans_col_endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        myPlans_col_dateCreated.setCellValueFactory(new PropertyValueFactory<>("CreatedDate"));
        myPlans_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        myPlans_tableView.setItems(myTaskListData);

    }
    private int taskID;

    public void myTaskSelectData() {

        taskData tData = myPlans_tableView.getSelectionModel().getSelectedItem();
        int num = myPlans_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        taskID = tData.getTaskID();

        myPlans_plan.setText(tData.getTask());
        myPlans_startDate.setValue(LocalDate.parse(String.valueOf(tData.getStartDate())));
        myPlans_endDate.setValue(LocalDate.parse(String.valueOf(tData.getEndDate())));
    }

    public void finishedTaskUpdateBtn() {

        connect = database.connectDB();

        try {

            if (finishedPlans_planID.getText().isEmpty()
                    || finishedPlans_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select item first ");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Task ID: "
                        + finishedPlans_planID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    String task = null;
                    String startDate = null;
                    String endDate = null;
                    String dateCreated = null;
                    String planner = null;

                    String selectData = "SELECT * FROM mytask WHERE id = "
                            + finishedPlans_planID.getText();

                    statement = connect.createStatement();
                    result = statement.executeQuery(selectData);

                    if (result.next()) {
                        task = result.getString("task");
                        startDate = result.getString("startDate");
                        endDate = result.getString("endDate");
                        dateCreated = result.getString("dateCreated");
                        planner = result.getString("planner");

                        String updateData = "UPDATE mytask SET task = '"
                                + task + "', startDate = '"
                                + startDate + "', endDate = '"
                                + dateCreated + "', status = '"
                                + finishedPlans_status.getSelectionModel().getSelectedItem()
                                + "', planner = '" + planner + "' WHERE id = " + finishedPlans_planID.getText();

                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated!");
                        alert.showAndWait();

                        finishedPlansShowData();
                    }

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled ");
                    alert.showAndWait();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String[] listStatus = {"Finished", "Not Finish"};

    public void finishedTaskListStatus() {

        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        finishedPlans_status.setItems(listData);
    }

    public ObservableList<taskData> finishedTaskDataList() {
        ObservableList<taskData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM mytask WHERE planner = '"
                + username.getText() + "'";
        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            taskData tData;
            while (result.next()) {

                tData = new taskData(result.getInt("id"), result.getString("task"),
                        result.getDate("startDate"), result.getDate("endDate"),
                        result.getDate("dateCreated"), result.getString("status"),
                        result.getString("planner"));
                listData.add(tData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<taskData> finishedTaskListData;

    public void finishedPlansShowData() {

        finishedTaskListData = finishedTaskDataList();

        finishedPlans_col_planID.setCellValueFactory(new PropertyValueFactory<>("taskID"));
        finishedPlans_col_plan.setCellValueFactory(new PropertyValueFactory<>("task"));
        finishedPlans_col_startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        finishedPlans_col_endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        finishedPlans_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        finishedPlans_tableVIew.setItems(finishedTaskListData);
    }

    public void finishedPlanSelectData() {
        taskData tData = finishedPlans_tableVIew.getSelectionModel().getSelectedItem();
        int num = finishedPlans_tableVIew.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        finishedPlans_planID.setText(String.valueOf(tData.getTaskID()));
    }

    public void displayUsername() {

        String user = data.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            myPlans_form.setVisible(false);
            finishedPlans_form.setVisible(false);

            page_label.setText("HOME");

            homeDisplayUsername();
            homeDisplayDateRegistered();
            homeDisplayNAP();
            homeDisplayFP();

        } else if (event.getSource() == plan_btn) {
            home_form.setVisible(false);
            myPlans_form.setVisible(true);
            finishedPlans_form.setVisible(false);
            page_label.setText("MY TASKS");

            myTaskShowData();
        } else if (event.getSource() == fin_btn) {
            home_form.setVisible(false);
            myPlans_form.setVisible(false);
            finishedPlans_form.setVisible(true);
            page_label.setText("FINISHED PLANS");

            finishedPlansShowData();
        }
    }

    public void logout() {

        try {

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logout_btn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        displayUsername();

        homeDisplayUsername();
        homeDisplayDateRegistered();
        homeDisplayNAP();
        homeDisplayFP();

        myTaskShowData();

        finishedTaskListStatus();

        finishedPlansShowData();

    }

}
