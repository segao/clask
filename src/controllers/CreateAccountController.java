package controllers;

import clask.ClaskApp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class CreateAccountController {
    private static ClaskApp mainInstance;
    private static SerializedCollection serialized;
    private String fName;
    private String lName;
    private String usrType;
    private String username;
    private String password;
    private String confirmPass;
    
    @FXML
    private TextField createFirstName;
    @FXML
    private TextField createLastName;
    @FXML
    private TextField createUsername;
    @FXML
    private PasswordField createPswd;
    @FXML
    private TextField createConfirmPswd;
    @FXML
    private Label errorCreateFirst;
    @FXML
    private Label errorCreateLast;
    @FXML
    private Label errorCreateUser;
    @FXML
    private Label errorCreatePass;
    @FXML
    private Label errorConfirmPass;
    
    @FXML
    private ComboBox createTypeComboBox;

    public CreateAccountController() {
        mainInstance = ClaskApp.getInstance();
        serialized = SerializedCollection.getInstance();
        
    }

    @FXML
    private void initialize() {
        getCreateTypeComboBox().getItems().removeAll(getCreateTypeComboBox().getItems());
        getCreateTypeComboBox().getItems().addAll("Student", "Instructor");
        getCreateTypeComboBox().getSelectionModel().select("Student");
    }
    
    @FXML
    private void handleCreateCancelButtonAction(ActionEvent event) throws IOException, Exception {
        boolean result = ConfirmationBox.displayConfirm("Cancel new user", "Are you sure you want to exit?");
        if (result){
            getMainInstance().switchScenes("InitialLogin");
        }
        
        else;
    }
    
    public boolean validateAccount() {
        
        getErrorConfirmPass().setText("");
        getErrorCreateFirst().setText("");
        getErrorCreateLast().setText("");
        getErrorCreateUser().setText("");
        getErrorCreatePass().setText("");
        
        setUsrType((String) getCreateTypeComboBox().getSelectionModel().getSelectedItem());
        //System.out.println(getUsrType());
        boolean flag = false;
        setfName(getCreateFirstName().getText());
        if (getfName().length() == 0 || getfName().equals("")) {
            getErrorCreateFirst().setText("ERROR: First Name cannot be empty.");
            flag = true;
        } else if (getfName().length() < 3 || getfName().length() > 20) {
            getErrorCreateFirst().setText("ERROR: Username must be between 3-20 characters.");
            flag = true;
        
        } else {
            //getErrorCreateFirst().setText("");
        }
        
        setlName(getCreateLastName().getText());
        if (getlName().length() == 0 || getlName().equals("")) {
            getErrorCreateLast().setText("ERROR: Last name cannot be empty.");
            flag = true;
        } else if (getlName().length() < 3 || getlName().length() > 20) {
            getErrorCreateLast().setText("ERROR: Last name must be between 3-20 characters.");
            flag = true;
        
        } else {
            //getErrorCreateLast().setText("");
        }
        
        setUsername(getCreateUsername().getText());
        if (getUsername().length() == 0 || getUsername().equals("")) {
            getErrorCreateUser().setText("ERROR: Username cannot be empty.");
            flag = true;
        } else if (getUsername().length() < 3 || getUsername().length() > 20) {
            getErrorCreateUser().setText("ERROR: Username must be between 3-20 characters.");
            flag = true;
        } else if (serialized.checkIfUsernameExist(getUsername(), getUsrType())) {
            getErrorCreateUser().setText("ERROR: Username already exists.");
            flag = true;
        } else {
            //getErrorCreateUser().setText("");
        }
        
        
        setPassword(getCreatePswd().getText());
        if (getPassword().length() == 0 || getPassword().equals("")) {
            getErrorCreatePass().setText("ERROR: Password cannot be empty.");
            flag = true;
        } else if (getPassword().length() < 3 || getPassword().length() > 20) {
            getErrorCreatePass().setText("ERROR: Password must be between 3-20 characters.");
            flag = true;
        
        } else {
            //getErrorCreatePass().setText("");
        }

        setConfirmPass(getCreateConfirmPswd().getText());
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

    public void createUser() {
        if ((validateAccount()) && (getUsrType().equals("Student"))) {
            getSerialized().addToListOfStudents(getfName(), getlName(), getUsername(), getPassword(),"");
            try {
                getMainInstance().setAccountMessage(true);
                getMainInstance().switchScenes("InitialLogin");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if ((validateAccount()) && (getUsrType().equals("Instructor"))) {
            getSerialized().addToListOfInstructors(getfName(), getlName(), getUsername(), getPassword(),"");
            try {
                getMainInstance().setAccountMessage(true);
                getMainInstance().switchScenes("InitialLogin");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        getSerialized().readStudentListFile();
        getSerialized().readInstructorListFile();
        getSerialized().printUserList();
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
     * @return the fName
     */
    public String getfName() {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * @return the lName
     */
    public String getlName() {
        return lName;
    }

    /**
     * @param lName the lName to set
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * @return the usrType
     */
    public String getUsrType() {
        return usrType;
    }

    /**
     * @param usrType the usrType to set
     */
    public void setUsrType(String usrType) {
        this.usrType = usrType;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @return the createFirstName
     */
    public TextField getCreateFirstName() {
        return createFirstName;
    }

    /**
     * @param createFirstName the createFirstName to set
     */
    public void setCreateFirstName(TextField createFirstName) {
        this.createFirstName = createFirstName;
    }

    /**
     * @return the createLastName
     */
    public TextField getCreateLastName() {
        return createLastName;
    }

    /**
     * @param createLastName the createLastName to set
     */
    public void setCreateLastName(TextField createLastName) {
        this.createLastName = createLastName;
    }

    /**
     * @return the createUsername
     */
    public TextField getCreateUsername() {
        return createUsername;
    }

    /**
     * @param createUsername the createUsername to set
     */
    public void setCreateUsername(TextField createUsername) {
        this.createUsername = createUsername;
    }

    /**
     * @return the createPswd
     */
    public PasswordField getCreatePswd() {
        return createPswd;
    }

    /**
     * @param createPswd the createPswd to set
     */
    public void setCreatePswd(PasswordField createPswd) {
        this.createPswd = createPswd;
    }

    /**
     * @return the createConfirmPswd
     */
    public TextField getCreateConfirmPswd() {
        return createConfirmPswd;
    }

    /**
     * @param createConfirmPswd the createConfirmPswd to set
     */
    public void setCreateConfirmPswd(TextField createConfirmPswd) {
        this.createConfirmPswd = createConfirmPswd;
    }

    /**
     * @return the errorCreateFirst
     */
    public Label getErrorCreateFirst() {
        return errorCreateFirst;
    }

    /**
     * @param errorCreateFirst the errorCreateFirst to set
     */
    public void setErrorCreateFirst(Label errorCreateFirst) {
        this.errorCreateFirst = errorCreateFirst;
    }

    /**
     * @return the errorCreateLast
     */
    public Label getErrorCreateLast() {
        return errorCreateLast;
    }

    /**
     * @param errorCreateLast the errorCreateLast to set
     */
    public void setErrorCreateLast(Label errorCreateLast) {
        this.errorCreateLast = errorCreateLast;
    }

    /**
     * @return the errorCreateUser
     */
    public Label getErrorCreateUser() {
        return errorCreateUser;
    }

    /**
     * @param errorCreateUser the errorCreateUser to set
     */
    public void setErrorCreateUser(Label errorCreateUser) {
        this.errorCreateUser = errorCreateUser;
    }

    /**
     * @return the errorCreatePass
     */
    public Label getErrorCreatePass() {
        return errorCreatePass;
    }

    /**
     * @param errorCreatePass the errorCreatePass to set
     */
    public void setErrorCreatePass(Label errorCreatePass) {
        this.errorCreatePass = errorCreatePass;
    }

    /**
     * @return the errorConfirmPass
     */
    public Label getErrorConfirmPass() {
        return errorConfirmPass;
    }

    /**
     * @param errorConfirmPass the errorConfirmPass to set
     */
    public void setErrorConfirmPass(Label errorConfirmPass) {
        this.errorConfirmPass = errorConfirmPass;
    }

    /**
     * @return the createTypeComboBox
     */
    public ComboBox getCreateTypeComboBox() {
        return createTypeComboBox;
    }

    /**
     * @param createTypeComboBox the createTypeComboBox to set
     */
    public void setCreateTypeComboBox(ComboBox createTypeComboBox) {
        this.createTypeComboBox = createTypeComboBox;
    }

}
