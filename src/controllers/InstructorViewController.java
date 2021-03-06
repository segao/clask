package controllers;

import clask.ClaskApp;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import models.Message;
import models.Course;
import models.Topic;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class InstructorViewController {
    private static ClaskApp mainInstance;
    private static InstructorViewController instructorViewInstance;
    private static SerializedCollection serialized;
    
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
    private Button submitA;
    @FXML
    private GridPane counterPane;
    @FXML 
    private GridPane topicPane;
    @FXML
    private Button createTopic;
    @FXML
    private Button deleteTopic;
    @FXML
    private Button renameTopic;
    
    private final Course activeCourse;
    private Topic selectedTopic = null;

    public InstructorViewController() {
        mainInstance = ClaskApp.getInstance();
        instructorViewInstance = this;
        serialized = SerializedCollection.getInstance();
        activeCourse = mainInstance.getCurrentCourse();
    }
    
    @FXML
    private void initialize() {
        deleteTopic.setDisable(true);
        renameTopic.setDisable(true);
        resetTopicButtons();
        displayTopics();
        displayTopicMessages();
    }
    
    public static InstructorViewController getInstance() {
        return instructorViewInstance;
    }
    
    /**
     * Creates a new topic with the name provided in the text box.
     * A topic is created if the user presses 'OK' and there are less than 8 topics already created. 
     * If the new topic name is the same as an existing topic, the existing topic of that name is 
     * selected and a duplicate is not created.
     * @param event Associated with onClick action for button createTopic
     * @throws IOException
     * @throws Exception 
     */
    @FXML
    private void createTopic(ActionEvent event) throws IOException, Exception {
        boolean result = ConfirmationBox.displayNewTopic("Create New Topic", "Enter topic name:");
        if (result) {
            if (activeCourse.getTopicsList().size() < 8 && !activeCourse.getTopicsList().contains(new Topic(ConfirmationBox.text, selectedTopic))) {
                Topic newTopic = new Topic(ConfirmationBox.text, selectedTopic);
                activeCourse.addTopic(newTopic);
                serialized.addToListOfCourses(activeCourse);
                displayTopic(newTopic, activeCourse.getTopicsList().size()-1);
                if (selectedTopic != null) {
                    selectedTopic.deselect();
                    selectedTopic = null;
                }
                if (activeCourse.getTopicsList().size() == 8)
                    createTopic.setDisable(true);
            } else if (activeCourse.getTopicsList().contains(new Topic(ConfirmationBox.text, selectedTopic))) {
                int index = activeCourse.getTopicsList().indexOf(new Topic(ConfirmationBox.text, selectedTopic));
                chooseTopic(activeCourse.getTopicsList().get(index));
            }
        }
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
        GridPane labelGP = createLabelGridPane(topic.getUnderstandLabel(), topic.getDontUnderstandLabel());
        GridPane counterGP = createLabelGridPane(topic.getUnderstandCounterLabel(), topic.getDontUnderstandCounterLabel());
        counterPane.add(labelGP, 0, rowIndex);
        GridPane.setMargin(labelGP, new Insets(-1, -1, 0, -1));
        counterPane.add(counterGP, 1, rowIndex);
        GridPane.setMargin(counterGP, new Insets(-1, -1, 0, 0));
        topicPane.add(topic.getTopicButton(), 0, rowIndex);
        GridPane.setMargin(topic.getTopicButton(), new Insets(-1, -1, 0, 0));
    }
    
    private void resetTopicButtons() {
        for (Topic topic : activeCourse.getTopicsList()) {
            topic.deselect();
        }
    }
    
    /***
     * Creates a two row GridPane to be added to the left counterPane GridPane.
     * Called twice per row, one to display "Don't Understand" and "Understand" labels and the other 
     * to show the student counters.
     * @param label1 Label to be put in the first row
     * @param label2 Label to be put in the second row
     * @return 
     */
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
    
    /**
     * Selects a topic. Occurs on onClick action for a topic's button.
     * Deselects an already selected topic if it exists.
     * Sets the background of the topic to select to blue.
     * Enables the renameTopic and deleteTopic buttons applied to the newly selected topic.
     * @param topic Topic to select
     */
    @FXML
    public void chooseTopic(Topic topic) {
        if (selectedTopic != null && selectedTopic.isUsed()) {
            selectedTopic.deselect();
            selectedTopic = null;
        }
        topic.getTopicButton().setStyle("-fx-background-color: Blue; -fx-text-fill: White;");
        topic.use();
        selectedTopic = topic;
        deleteTopic.setDisable(false);
        deleteTopic.setOnAction(e -> deleteTopic(selectedTopic));
        renameTopic.setDisable(false);
        renameTopic.setOnAction(e -> renameTopic(selectedTopic));
        displayTopicMessages();
    }
    
    /**
     * Deletes a topic.
     * Called through onClick action on button deleteTopic.
     * Refreshes the topic list display.
     * @param topic Selected Topic
     */
    @FXML
    public void deleteTopic(Topic topic) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Topic");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete Topic " + topic.getTopicName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            activeCourse.getTopicsList().remove(topic);
            topicPane.getChildren().clear();
            counterPane.getChildren().clear();
            selectedTopic = null;
            deleteTopic.setDisable(true);
            renameTopic.setDisable(true);
            createTopic.setDisable(false);
            displayTopics();
        }
    }
    
    /**
     * Renames a topic.
     * Called through onClick action on button renameTopic.
     * @param topic Topic to select.
     */
    @FXML
    public void renameTopic(Topic topic) {
        boolean result = ConfirmationBox.displayNewTopic("Rename Topic", "Enter a new name for Topic " + topic.getTopicName() + ":");
        if (result) {
            topic.setTopicName(ConfirmationBox.text);
            topic.getTopicButton().setText(topic.getTopicName());
        }
    }

    
    @FXML
    private void handleInstructorLogOutButtonAction(ActionEvent event) throws IOException, Exception {
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
            getMainInstance().switchScenes("InstructorLogin");
        }
        
        else;
    }
    
    @FXML
    public void enterA() {
        addMessageToTopic("Answer----",(messageTextArea.getText()), "----Posted by Instructor" );
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
    


    @FXML
    public void createNewTopic() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Topics");
        dialog.setHeaderText("New Topic");
        dialog.setContentText("Please enter the new topic:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("New Topic: " + result.get());
        }

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("New Topic: " + name));
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
}
