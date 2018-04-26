/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clask.ClaskApp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Course;

/**
 * FXML Controller class
 *
 * @author jr110
 */
public class CreateCourseController {
    private static ClaskApp mainInstance;
    private static SerializedCollection serialized;
    
    private String courseName;
    private String roomNum;
    private String password;
    private String confirmPass;
    
    @FXML
    private TextField createCourseName;
    @FXML
    private PasswordField createCoursePswd;
    @FXML
    private PasswordField createConfirmPass;
    @FXML
    private ComboBox createCourseRoom;
    @FXML
    private Label errorCourseName;
    @FXML
    private Label errorRoomSelected;
    @FXML
    private Label errorPassword;
    @FXML
    private Label errorConfirmPass;
    
    public CreateCourseController(){
        mainInstance = ClaskApp.getInstance();
        serialized = SerializedCollection.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        getCreateCourseRoom().getItems().removeAll(getCreateCourseRoom().getItems());
        getCreateCourseRoom().getItems().addAll(serialized.getAvail_rooms());
        getCreateCourseRoom().getSelectionModel().select(serialized.getAvail_rooms().get(0));    // TODO
    }
        
    public boolean validateCourse() {
        setRoomNum((String) getCreateCourseRoom().getSelectionModel().getSelectedItem());
        boolean flag = false;
        getErrorCourseName().setText("");
        getErrorPassword().setText("");
        getErrorConfirmPass().setText("");
        
        setCourseName(getCreateCourseName().getText());
        if ((getCourseName().length() == 0) || (getCourseName().equals(""))) {
            getErrorCourseName().setText("ERROR: Course name cannot be empty.");
            flag = true;
        } 
        else if ((getCourseName().length() < 6) || (getCourseName().length() > 20)) {
            getErrorCourseName().setText("ERROR: Course name must be between 6-20 characters.");
            flag = true;
        } else if (serialized.checkIfCourseNameExist(getCourseName())) {
            getErrorCourseName().setText("ERROR: Course name already exists.");
            flag = true;
        } else {
            //getErrorCourseName().setText("");
        }
        
        setPassword(getCreateCoursePswd().getText());
        if (getPassword().length() == 0 || getPassword().equals("")) {
            getErrorPassword().setText("ERROR: Password cannot be empty.");
            flag = true;
        } else if (getPassword().length() < 3 || getPassword().length() > 20) {
            getErrorPassword().setText("ERROR: Password must be between 3-20 characters.");
            flag = true;
        
        } else {
            //getErrorPassword().setText("");
        }

        setConfirmPass(getCreateConfirmPass().getText());
        if (!password.equals(confirmPass)) {
            getErrorConfirmPass().setText("ERROR: Passwords must match.");
            flag = true;
        
        } else {
            //getErrorConfirmPass().setText("");
        }
        if (flag) {
            return false;
        }

        return true;
    }
    
    public void createCourse() {
        if (validateCourse()){
            serialized.addToListOfCourses(new Course(getCourseName(), getRoomNum(), getPassword()));
            try {
                getMainInstance().setAccountMessage(true);
                getMainInstance().switchScenes("InstructorLogin");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getSerialized().readCourseListFile();
        getSerialized().printCourseList();
    }
    
     @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException, Exception {
        boolean result = ConfirmationBox.displayConfirm("Cancel new user", "Are you sure you want to exit?");
        if (result){
            getMainInstance().switchScenes("InstructorLogin");
        }
        
        else;
    }
    
    /**
     * @return the mainInstance
     */
    public static ClaskApp getMainInstance() {
        return mainInstance;
    }

    /**
     * @param aMainInstance the mainInstance to set
     */
    public static void setMainInstance(ClaskApp aMainInstance) {
        mainInstance = aMainInstance;
    }

    /**
     * @return the serialized
     */
    public static SerializedCollection getSerialized() {
        return serialized;
    }

    /**
     * @param aSerialized the serialized to set
     */
    public static void setSerialized(SerializedCollection aSerialized) {
        serialized = aSerialized;
    }

    /**
     * @return the courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @param courseName the courseName to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * @return the roomNum
     */
    public String getRoomNum() {
        return roomNum;
    }

    /**
     * @param roomNum the roomNum to set
     */
    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPass
     */
    public String getConfirmPass() {
        return confirmPass;
    }

    /**
     * @param confirmPass the confirmPass to set
     */
    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    /**
     * @return the createCourseName
     */
    public TextField getCreateCourseName() {
        return createCourseName;
    }

    /**
     * @param createCourseName the createCourseName to set
     */
    public void setCreateCourseName(TextField createCourseName) {
        this.createCourseName = createCourseName;
    }

    /**
     * @return the createCoursePswd
     */
    public PasswordField getCreateCoursePswd() {
        return createCoursePswd;
    }

    /**
     * @param createCoursePswd the createCoursePswd to set
     */
    public void setCreateCoursePswd(PasswordField createCoursePswd) {
        this.createCoursePswd = createCoursePswd;
    }
    
    /**
     * @return the createCoursePass
     */
    public PasswordField getCreateConfirmPass() {
        return createConfirmPass;
    }

    /**
     * @param createConfirmPass the createConfirmPass to set
     */
    public void setCreateConfirmPass(PasswordField createConfirmPass) {
        this.createConfirmPass = createConfirmPass;
    }

    /**
     * @return the createCourseRoom
     */
    public ComboBox getCreateCourseRoom() {
        return createCourseRoom;
    }

    /**
     * @param createCourseRoom the createCourseRoom to set
     */
    public void setCreateCourseRoom(ComboBox createCourseRoom) {
        this.createCourseRoom = createCourseRoom;
    }

    /**
     * @return the errorCourseName
     */
    public Label getErrorCourseName() {
        return errorCourseName;
    }

    /**
     * @param errorCourseName the errorCourseName to set
     */
    public void setErrorCourseName(Label errorCourseName) {
        this.errorCourseName = errorCourseName;
    }

    /**
     * @return the errorRoomSelected
     */
    public Label getErrorRoomSelected() {
        return errorRoomSelected;
    }

    /**
     * @param errorRoomSelected the errorRoomSelected to set
     */
    public void setErrorRoomSelected(Label errorRoomSelected) {
        this.errorRoomSelected = errorRoomSelected;
    }

    /**
     * @return the errorPassword
     */
    public Label getErrorPassword() {
        return errorPassword;
    }

    /**
     * @param errorPassword the errorPassword to set
     */
    public void setErrorPassword(Label errorPassword) {
        this.errorPassword = errorPassword;
    }
    
    /**
     * @return the errorConfirmPassword
     */
    public Label getErrorConfirmPass() {
        return errorConfirmPass;
    }

    /**
     * @param errorConfirmPass the errorConfirmPassword to set
     */
    public void setErrorConfirmPass(Label errorConfirmPass) {
        this.errorConfirmPass = errorConfirmPass;
    }

    
}
