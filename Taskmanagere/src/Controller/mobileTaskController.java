package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
public class mobileTaskController implements Initializable {

    @FXML
    private Button fin_btn_mob;

    @FXML
    private Button fin_btn_mob1;

    @FXML
    private Button fin_btn_mob2;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_endDate_mob;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_planID_mob;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_plan_mob;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_startDate_mob;

    @FXML
    private TableColumn<taskData, String> finishedPlans_col_status_mob;

    @FXML
    private TextField finishedPlans_planID_mob;

    @FXML
    private ComboBox<taskData> finishedPlans_status_mob;

    @FXML
    private TableView<taskData> finishedPlans_tableVIew_mob;

    @FXML
    private Button finishedPlans_updateBtn_mob;

    @FXML
    private Label home_FP_mob;

    @FXML
    private Label home_NAP_mob;

    @FXML
    private Button home_btn_mob;

    @FXML
    private Button home_btn_mob1;

    @FXML
    private Button home_btn_mob2;

    @FXML
    private Label home_dateRegistered_mob;

    @FXML
    private Label home_username_mob;

    @FXML
    private Button logout_btn_mob;

    @FXML
    private Button logout_btn_mob1;

    @FXML
    private Button logout_btn_mob2;

    @FXML
    private AnchorPane main_mob_form;

    @FXML
    private Button myPlans_addBtn_mob;

    @FXML
    private Button myPlans_clearBtn_mob;

    @FXML
    private TableColumn<taskData, String> myPlans_col_dateCreated_mob;

    @FXML
    private TableColumn<taskData, String> myPlans_col_endDate_mob;

    @FXML
    private TableColumn<taskData, String> myPlans_col_plan_mob;

    @FXML
    private TableColumn<taskData, String> myPlans_col_startDate_mob;

    @FXML
    private TableColumn<taskData, String> myPlans_col_status_mob;

    @FXML
    private Button myPlans_deleteBtn_mob;

    @FXML
    private DatePicker myPlans_endDate_mob;

    @FXML
    private TextArea myPlans_plan_mob;

    @FXML
    private DatePicker myPlans_startDate_mob;

    @FXML
    private TableView<taskData> myPlans_tableView_mob;

    @FXML
    private Button myPlans_updateBtn_mob;

    @FXML
    private Text page_label_mob;

    @FXML
    private Button plan_btn_mob;
    @FXML
    private Button plan_btn_mob1;

    @FXML
    private Button plan_btn_mob2;

    @FXML
    private Text page_label;

    @FXML
    private AnchorPane home_form_mob , myPlans_form_mob, finishedPlans_form_mob;

    @FXML
    private Text username_mob;

   



    


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private Alert alert;

    public void homeDisplayUsername_mob() {

        home_username_mob.setText(username_mob.getText());
    }

    public void homeDisplayDateRegisteredMob() {

        String selectDate = "SELECT date FROM user WHERE username ='"
                + data.username + "'";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(selectDate);
            result = prepare.executeQuery();

            if (result.next()) {
                home_dateRegistered_mob.setText(result.getString("date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayNAPMob() {

        String sql = "SELECT COUNT(id) FROM mytask WHERE planner ='"
                + username_mob.getText() + "'";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                home_NAP_mob.setText(result.getString("COUNT(id)"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayFP() {
        String sql = "SELECT COUNT(id) FROM mytask WHERE planner ='"
                + username_mob.getText() + "' AND status = 'Finished'";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                home_FP_mob.setText(result.getString("COUNT(id)"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void myTaskAddBtnMob(){
        connect = database.connectDB();

        try {
            if( myPlans_plan_mob.getText().isEmpty() || myPlans_startDate_mob.getValue() == null
         || myPlans_endDate_mob.getValue() == null ){
            alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all blank fields");
                alert.showAndWait();
         }else{
            if (myPlans_endDate_mob.getValue().isBefore(myPlans_startDate_mob.getValue())) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Date :<");
                alert.showAndWait();
            }else{
                String checkTask = "SELECT task FROM mytask WHERE task = '"
                            + myPlans_plan_mob.getText() + "'";

                    prepare = connect.prepareStatement(checkTask);
                    result = prepare.executeQuery();


                    if (result.next()) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Task: " + myPlans_plan_mob.getText() + "is already recorded");
                        alert.showAndWait();
                    }else {
                        String insertData = "INSERT INTO mytask (task, startDate, endDate, dateCreated, status, planner)"
                                + "VALUES(?,?,?,?,?,?)";

                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, myPlans_plan_mob.getText());
                        prepare.setString(2, String.valueOf(myPlans_startDate_mob.getValue()));
                        prepare.setString(3, String.valueOf(myPlans_endDate_mob.getValue()));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(4, String.valueOf(sqlDate));
                        prepare.setString(5, "Not Finish");
                        prepare.setString(6, username_mob.getText());
                        prepare.executeUpdate();

                        myTaskShowData();
                        myTaskClearBtn();
                    }


            }
         }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private int taskID;

    public void myTaskUpdateBtnMob() {

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
                        + myPlans_plan_mob.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String checkData = "SELECT * FROM mytask WHERE id = " + taskID;

                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);
                    String currentD;
                    if (result.next()) {
                        currentD = result.getString("dateCreated");

                        String updateData = "UPDATE mytask set task = '"
                                + myPlans_plan_mob.getText() + "',  startDate = '"
                                + myPlans_startDate_mob.getValue() + "', endDate = '"
                                + myPlans_endDate_mob.getValue() + "', dateCreated  = '"
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


    public void myTaskDeleteBtnMob() {

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
                        + myPlans_plan_mob.getText());
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
        myPlans_plan_mob.setText("");
        myPlans_startDate_mob.setValue(null);
        myPlans_endDate_mob.setValue(null);
        taskID = 0;
    }


    public ObservableList<taskData> myTaskDataList() {
        ObservableList<taskData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM mytask WHERE planner = '"
                + username_mob.getText() + "'";

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

        myPlans_col_plan_mob.setCellValueFactory(new PropertyValueFactory<>("task"));
        myPlans_col_startDate_mob.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        myPlans_col_endDate_mob.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        myPlans_col_dateCreated_mob.setCellValueFactory(new PropertyValueFactory<>("CreatedDate"));
        myPlans_col_status_mob.setCellValueFactory(new PropertyValueFactory<>("status"));

        myPlans_tableView_mob.setItems(myTaskListData);

    }

    public void myTaskSelectData() {

        taskData tData = myPlans_tableView_mob.getSelectionModel().getSelectedItem();
        int num = myPlans_tableView_mob.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        taskID = tData.getTaskID();

        myPlans_plan_mob.setText(tData.getTask());
        myPlans_startDate_mob.setValue(LocalDate.parse(String.valueOf(tData.getStartDate())));
        myPlans_endDate_mob.setValue(LocalDate.parse(String.valueOf(tData.getEndDate())));
    }


    public void finishedTaskUpdateBtn() {

        connect = database.connectDB();

        try {

            if (finishedPlans_planID_mob.getText().isEmpty()
                    || finishedPlans_status_mob.getSelectionModel().getSelectedItem() == null) {
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
                        + finishedPlans_planID_mob.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    String task = null;
                    String startDate = null;
                    String endDate = null;
                    String dateCreated = null;
                    String planner = null;

                    String selectData = "SELECT * FROM mytask WHERE id = "
                            + finishedPlans_planID_mob.getText();

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
                                + finishedPlans_status_mob.getSelectionModel().getSelectedItem()
                                + "', planner = '" + planner + "' WHERE id = " + finishedPlans_planID_mob.getText();

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
        finishedPlans_status_mob.setItems(listData);
    }

    public ObservableList<taskData> finishedTaskDataList() {
        ObservableList<taskData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM mytask WHERE planner = '"
                + username_mob.getText() + "'";
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

        finishedPlans_col_planID_mob.setCellValueFactory(new PropertyValueFactory<>("taskID"));
        finishedPlans_col_plan_mob.setCellValueFactory(new PropertyValueFactory<>("task"));
        finishedPlans_col_startDate_mob.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        finishedPlans_col_endDate_mob.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        finishedPlans_col_status_mob.setCellValueFactory(new PropertyValueFactory<>("status"));

        finishedPlans_tableVIew_mob.setItems(finishedTaskListData);
    }


    public void finishedPlanSelectData() {
        taskData tData = finishedPlans_tableVIew_mob.getSelectionModel().getSelectedItem();
        int num = finishedPlans_tableVIew_mob.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        finishedPlans_planID_mob.setText(String.valueOf(tData.getTaskID()));
    }

    public void displayUsername() {

        String user = data.username;
        username_mob.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }


    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn_mob || event.getSource() == home_btn_mob1 || event.getSource() == home_btn_mob2) {
            home_form_mob.setVisible(true);
            myPlans_form_mob.setVisible(false);
            finishedPlans_form_mob.setVisible(false);

            page_label.setText("HOME");

            homeDisplayUsername_mob();
            homeDisplayDateRegisteredMob();
            homeDisplayNAPMob();
            homeDisplayFP();

        } else if (event.getSource() == plan_btn_mob || event.getSource() == plan_btn_mob1 || event.getSource() == plan_btn_mob2) {
            home_form_mob.setVisible(false);
            myPlans_form_mob.setVisible(true);
            finishedPlans_form_mob.setVisible(false);
            page_label.setText("MY TASKS");

            myTaskShowData();
        } else if (event.getSource() == fin_btn_mob ||event.getSource() == fin_btn_mob1 ||event.getSource() == fin_btn_mob2) {
            home_form_mob.setVisible(false);
            myPlans_form_mob.setVisible(false);
            finishedPlans_form_mob.setVisible(true);
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
                logout_btn_mob.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLDocumentMobileSize.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }else if (option.get().equals(ButtonType.OK)) {
                logout_btn_mob1.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLDocumentMobileSize.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }else if (option.get().equals(ButtonType.OK)) {
                logout_btn_mob2.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLDocumentMobileSize.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }
            

               
            
        }


         catch (Exception e) {
            e.printStackTrace();
        }
    }



        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        displayUsername();

        homeDisplayUsername_mob();
        homeDisplayDateRegisteredMob();
        homeDisplayNAPMob();
        homeDisplayFP();

        myTaskShowData();

        finishedTaskListStatus();

        finishedPlansShowData();
       

    }
    
}
