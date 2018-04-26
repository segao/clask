package controllers;

import clask.ClaskApp;
import static com.oracle.nio.BufferSecrets.instance;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import models.Instructor;
import models.Student;

public class LoginController
{
    private static ClaskApp mainInstance;
    private static LoginController instance;
    @FXML
    private Label accountCreationMessage;
    @FXML
    private Label errorMessageUser;
    @FXML
    private Label errorMessagePass;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    private static SerializedCollection currentSerialized;
    CreateAccountController createController;

    public LoginController() {
        mainInstance = ClaskApp.getInstance();
        instance = this;
        currentSerialized = new SerializedCollection();
        createController = new CreateAccountController();
    }

    @FXML
    private void initialize() {
        if (mainInstance.getAccountMessageBoolean()) {
            accountCreationMessage.setText("Account creation successful!");
        } else {
            accountCreationMessage.setText("");
        }
        mainInstance.setAccountMessage(false);
    }

    @FXML
    public void authenticateUser() {
        
        errorMessageUser.setText("");
        errorMessagePass.setText("");

        String user_input = usernameField.getText();
        String pass_input = passwordField.getText();
        boolean userFlag = false;
        boolean passFlag = false;
        
        if (user_input.length() == 0 || user_input.equals("")) {
            errorMessageUser.setText("ERROR: Username cannot be empty.");
            userFlag = true;
        } 
        else if (user_input.length() < 3 || user_input.length() > 20) {
            errorMessageUser.setText("ERROR: Invalid username. Username must be between 3-20 characters.");
            userFlag = true;
        } 
        else if (!currentSerialized.checkIfUsernameExist(user_input, "Student") && (!currentSerialized.checkIfUsernameExist(user_input, "Instructor"))) {
            errorMessageUser.setText("ERROR: Username does not exist.");
            userFlag = true;
        }
        else if (pass_input.length() == 0 || pass_input.equals("")) {
                errorMessagePass.setText("ERROR: Password cannot be empty.");
                userFlag = true;
            } 
         else {
            //errorMessageUser.setText("");
            //errorMessagePass.setText("");
        }

        if (!userFlag){
            for(int i = 0; i < mainInstance.getListOfStudents().size(); i++){
                if(mainInstance.getListOfStudents().get(i).authenticate(user_input, pass_input)){
                    mainInstance.setCurrentStudent(mainInstance.getListOfStudents().get(i));
                    try {
                        mainInstance.switchScenes("StudentLogin");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else;
            }
            
            for(int i = 0; i < mainInstance.getListOfInstructors().size(); i++){
                if(mainInstance.getListOfInstructors().get(i).authenticate(user_input, pass_input)){
                    mainInstance.setCurrentInstructor(mainInstance.getListOfInstructors().get(i));
                    passFlag = true;
                    try {
                    mainInstance.switchScenes("InstructorLogin");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                
            }
        }    
        else; 
        errorMessagePass.setText("ERROR: Incorrect password.");
                
    }
    
    @FXML
    public void createAccount() {
        try {
            mainInstance.switchScenes("CreateAccount");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static LoginController getInstance() {
        return instance;
    }

}
