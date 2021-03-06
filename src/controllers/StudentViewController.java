package controllers;

import clask.ClaskApp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Course;
import models.Message;
import models.Topic;

public class StudentViewController {
    private static ClaskApp mainInstance;
    private static StudentViewController studentViewInstance;
    
    @FXML
    private Label userLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private Label roomLabel;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button submitQ;
    @FXML
    private Button submitA;
    @FXML
    private GridPane counterPane;
    @FXML 
    private GridPane topicPane;
    
    private Course activeCourse;
    private Topic selectedTopic;
    private int numTopics;
    private String text = "";
    
    public StudentViewController() {    
        mainInstance = ClaskApp.getInstance();
        studentViewInstance = this;
        activeCourse = mainInstance.getCurrentCourse();
        numTopics = 0;
    }

    @FXML
    private void initialize() {
        getUserLabel().setText("User: " + mainInstance.getCurrentStudent().getStudentName() 
                + " - Session ID: " 
                + mainInstance.getCurrentCourse().getSessionID(mainInstance.getCurrentStudent()));
        getCourseLabel().setText(mainInstance.getCurrentCourse().getCourseName());
        getRoomLabel().setText(("Room #" + getMainInstance().getCurrentCourse().getRoomNum()));
        
        resetTopicButtons();
        displayTopics();
        displayTopicMessages();   
    }
    
    public static StudentViewController getInstance() {
        return studentViewInstance;
    }

  /**
     * Creates a new VBox with a 1-pixel, black border.
     * Used to fill out the rest of a GridPane so that the entire grid has borders.
     * This is needed since setGridLinesVisible is only for debugging and is cleared when topics are reset.
     * @return 
     */
    private VBox displayEmptyCellWithBorder() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-color: black;");
        return vbox;
    }
    
    /**
     * Displays all topics in the active course's topics list.
     * If the number of topics is less than 8, creates empty cells to fill out the rest of the grid.
     */
    private void displayTopics() {
        for (int i = 0; i < activeCourse.getTopicsList().size(); i++) {
            displayTopic(activeCourse.getTopicsList().get(i), i);
        }
        for (int i = activeCourse.getTopicsList().size(); i < 8; i++) {
            VBox labelBox = displayEmptyCellWithBorder();
            counterPane.add(labelBox, 0, i);
            GridPane.setMargin(labelBox, new Insets(-1, -1, 0, -1));
            VBox counterBox = displayEmptyCellWithBorder();
            GridPane.setFillWidth(counterBox, true);
            counterPane.add(counterBox, 1, i);
            GridPane.setMargin(counterBox, new Insets(-1, -1, 0, 0));
            VBox buttonBox = displayEmptyCellWithBorder();
            topicPane.add(buttonBox, 0, i);
            GridPane.setMargin(buttonBox, new Insets(0, -1, -1, 0));
        }
    }
    
    /**
     * Displays associated components showing the information of the given topic.
     * Adds the label GridPane, counter GridPane, and button to the counterPane and the topicPane.
     * ___________________________________      ________________________________
     * |        Understand         |  0  |      |                              |
     * |      Don't Understand     |  0  |      |          Topic 1 Name        |
     * -----------------------------------      --------------------------------
     * @param topic Topic to be displayed
     * @param rowIndex Row to insert components 
     */
    private void displayTopic(Topic topic, int rowIndex) {
        topic.getTopicButton().setOnAction(e -> chooseTopic(topic));
        GridPane radioGP = createRadioGridPane(topic.getUnderstandRadio(), topic.getDontUnderstandRadio());
        GridPane counterGP = createLabelGridPane(topic.getUnderstandCounterLabel(), topic.getDontUnderstandCounterLabel());
        counterPane.add(radioGP, 0, rowIndex);
        GridPane.setFillWidth(counterGP, true);
        GridPane.setMargin(radioGP, new Insets(-1, -1, 0, -1));
        counterPane.add(counterGP, 1, rowIndex);
        GridPane.setMargin(counterGP, new Insets(-1, -1, 0, 0));
        topicPane.add(topic.getTopicButton(), 0, rowIndex);
        GridPane.setMargin(topic.getTopicButton(), new Insets(-1, -1, 0, 0));
    }
    
    
    private void resetTopicButtons() {
        for (Topic topic : activeCourse.getTopicsList()) {
            topic.deselect();
            if (topic.getStudentsDontUnderstandList().contains(mainInstance.getCurrentStudent())) {
                topic.getDontUnderstandRadio().setSelected(true);
                topic.getUnderstandRadio().setSelected(false);
            } else if (topic.getStudentsUnderstandList().contains(mainInstance.getCurrentStudent())) {
                topic.getDontUnderstandRadio().setSelected(false);
                topic.getUnderstandRadio().setSelected(true);
            } else {
                topic.getDontUnderstandRadio().setSelected(false);
                topic.getUnderstandRadio().setSelected(false);  
            }
        }
    }
    
    @FXML
    public void chooseTopic(Topic topic){
        if (selectedTopic != null && selectedTopic.isUsed()) {
            selectedTopic.deselect();
            selectedTopic = null;
        }
        topic.getTopicButton().setStyle("-fx-background-color: Blue; -fx-text-fill: White;");
        topic.use();
        selectedTopic = topic;
        displayTopicMessages();
    }
    
    
    private GridPane createLabelGridPane(Label label1, Label label2) {
        GridPane labelGP = new GridPane();
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(50);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100);
        labelGP.getRowConstraints().addAll(rc);
        labelGP.getColumnConstraints().addAll(cc);
        labelGP.add(label1, 0, 0);
        labelGP.add(label2, 0, 1);
        labelGP.setStyle("-fx-border-color: black;");
        return labelGP;
    }
    
        private GridPane createRadioGridPane(RadioButton understand, RadioButton dontUnderstand) {
        GridPane radioGP = new GridPane();
        radioGP.setPadding(new Insets(0, 10, 0, 10));
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(50);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100);
        radioGP.getRowConstraints().addAll(rc);
        radioGP.getColumnConstraints().addAll(cc);
        
        ToggleGroup tg = new ToggleGroup();
        understand.setToggleGroup(tg);
        dontUnderstand.setToggleGroup(tg);
        radioGP.add(understand, 0, 0);
        radioGP.add(dontUnderstand, 0, 1);
        radioGP.setStyle("-fx-border-color: black;");
        return radioGP;
    }
   
    @FXML
    private void handleLogOutButtonAction(ActionEvent event) throws IOException, Exception {
        boolean result = ConfirmationBox.displayConfirm("Logout request", "Are you sure you want to logout?");
        if (result){
            getMainInstance().switchScenes("InitialLogin");
        }
        
        else;
    }
    
    @FXML
    private void handleChangeRoomButtonAction(ActionEvent event) throws IOException, Exception {
        boolean result = ConfirmationBox.displayConfirm("Room Change", "Are you sure you want to change rooms?");
        if (result){
            getMainInstance().switchScenes("StudentLogin");
        }
        
        else;
    }


    @FXML
    public void enterQ() {
        addMessageToTopic("Question----",(messageTextArea.getText()), 
                "\t----Posted by Student " + mainInstance.getCurrentCourse().getSessionID(mainInstance.getCurrentStudent()));
        messageTextArea.clear();
        displayTopicMessages();
    }
    
    @FXML
    public void enterA() {
        addMessageToTopic("Answer----",(messageTextArea.getText()), 
                "\t----Posted by Student " + mainInstance.getCurrentCourse().getSessionID(mainInstance.getCurrentStudent()));
        messageTextArea.clear();
        displayTopicMessages();
    }
    
    @FXML
    public void addMessageToTopic(String type, String messageBody, String poster){
        if (selectedTopic != null) {
            selectedTopic.getMessages().add(new Message(type, messageBody, poster));
        }
    }
    
    public void displayTopicMessages(){
        GridPane msgPane = new GridPane();
        scrollPane.setContent(msgPane);
        msgPane.setPrefWidth(0.95 * scrollPane.getPrefWidth());
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100);
        msgPane.getColumnConstraints().add(cc);
        if (selectedTopic != null) {
            for(int i = 0; i < selectedTopic.getMessages().size(); i++){
                GridPane currMsg = new GridPane();
                Text type = new Text(selectedTopic.getMessages().get(i).getType());
                type.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
                type.setTextAlignment(TextAlignment.LEFT);
                Text body = new Text(selectedTopic.getMessages().get(i).getMessageBody());
                body.setTextAlignment(TextAlignment.LEFT);
                body.setWrappingWidth(0.9 * msgPane.getPrefWidth());
                currMsg.setHalignment(body, HPos.CENTER);
                Text poster = new Text(selectedTopic.getMessages().get(i).getPostedBy());
                poster.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                poster.setTextAlignment(TextAlignment.RIGHT);
                poster.setWrappingWidth(msgPane.getPrefWidth());
                currMsg.addRow(0, type);
                currMsg.addRow(1, body);
                currMsg.addRow(2, poster);
                msgPane.addRow(i, currMsg);
            }    
        }    
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
     * @return the userLabel
     */
    public Label getUserLabel() {
        return userLabel;
    }

    /**
     * @param userLabel the userLabel to set
     */
    public void setUserLabel(Label userLabel) {
        this.userLabel = userLabel;
    }

    /**
     * @return the courseLabel
     */
    public Label getCourseLabel() {
        return courseLabel;
    }

    /**
     * @param courseLabel the courseLabel to set
     */
    public void setCourseLabel(Label courseLabel) {
        this.courseLabel = courseLabel;
    }

    /**
     * @return the roomLabel
     */
    public Label getRoomLabel() {
        return roomLabel;
    }

    /**
     * @param roomLabel the roomLabel to set
     */
    public void setRoomLabel(Label roomLabel) {
        this.roomLabel = roomLabel;
    }


    /**
     * @return the submitQ
     */
    public Button getSubmitQ() {
        return submitQ;
    }

    /**
     * @param submitQ the submitQ to set
     */
    public void setSubmitQ(Button submitQ) {
        this.submitQ = submitQ;
    }

    /**
     * @return the submitA
     */
    public Button getSubmitA() {
        return submitA;
    }

    /**
     * @param submitA the submitA to set
     */
    public void setSubmitA(Button submitA) {
        this.submitA = submitA;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}