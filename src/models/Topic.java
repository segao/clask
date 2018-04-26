package models;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.geometry.Pos;
import controllers.InstructorViewController;
import controllers.StudentViewController;
import clask.ClaskApp;

public class Topic {
    private String topicName;
    private ArrayList<Message> messages;
    private boolean inUse;
    @FXML
    private Button topicButton;
    @FXML
    private Label dontUnderstandLabel;
    @FXML
    private Label understandLabel;
    @FXML
    private RadioButton dontUnderstandRadio;
    @FXML
    private RadioButton understandRadio;
    @FXML
    private Label dontUnderstandCounterLabel;
    @FXML
    private Label understandCounterLabel;
    
    private ClaskApp mainInstance;
    private int dontUnderstandCounter;
    private ArrayList<Student> studentsDontUnderstandList;
    private ArrayList<Student> studentsUnderstandList;
    private int understandCounter;

    public Topic(String tName, Topic selectedTopic) {
        this.mainInstance = ClaskApp.getInstance();
        this.topicName = tName;
        this.messages = new ArrayList<>();
        this.inUse = false;
        this.dontUnderstandCounter = 0;
        this.understandCounter = 0;
        this.studentsDontUnderstandList = new ArrayList<>();
        this.studentsUnderstandList = new ArrayList<>();
        initializeComponents();
    }
    
    public Topic(String tName, ArrayList<Message> msgs, Topic selectedTopic){
        this.mainInstance = ClaskApp.getInstance();
        this.topicName = tName;
        this.messages = msgs;  
        this.inUse = false;
        this.dontUnderstandCounter = 0;
        this.understandCounter = 0;
        this.studentsDontUnderstandList = new ArrayList<>();
        this.studentsUnderstandList = new ArrayList<>();
        initializeComponents();
        
    }
    
    public void initializeComponents() {
        topicButton = new Button(this.topicName);
        dontUnderstandLabel = new Label("Not Understand");
        understandLabel = new Label("Understand");
        dontUnderstandRadio = new RadioButton("Not Understand");
        understandRadio = new RadioButton("Understand");
        dontUnderstandCounterLabel = new Label(Integer.toString(dontUnderstandCounter));
        understandCounterLabel = new Label(Integer.toString(understandCounter));
        styleComponents();
    }
    
    public void styleComponents() {
        topicButton.setMaxWidth(Double.MAX_VALUE);
        topicButton.setMaxHeight(Double.MAX_VALUE);
        topicButton.setStyle("-fx-content-display: CENTER;");

        dontUnderstandLabel.setStyle("-fx-text-fill: White;");
        dontUnderstandLabel.setAlignment(Pos.CENTER);
        dontUnderstandLabel.setMaxWidth(Double.MAX_VALUE);
        dontUnderstandLabel.setMaxHeight(Double.MAX_VALUE);
        
        dontUnderstandCounterLabel.setStyle("-fx-text-fill: Red;");
        dontUnderstandCounterLabel.setAlignment(Pos.CENTER);
        dontUnderstandCounterLabel.setMaxWidth(Double.MAX_VALUE);
        dontUnderstandCounterLabel.setMaxHeight(Double.MAX_VALUE);
        
        understandLabel.setStyle("-fx-text-fill: #0aee0a;");
        understandLabel.setAlignment(Pos.CENTER);
        understandLabel.setMaxWidth(Double.MAX_VALUE);
        understandLabel.setMaxHeight(Double.MAX_VALUE);
        
        understandCounterLabel.setStyle("-fx-text-fill: #0aee0a;");
        understandCounterLabel.setAlignment(Pos.CENTER);
        understandCounterLabel.setMaxWidth(Double.MAX_VALUE);
        understandCounterLabel.setMaxHeight(Double.MAX_VALUE);
        
        dontUnderstandRadio.setStyle("-fx-text-fill: Red;");
        dontUnderstandRadio.setAlignment(Pos.CENTER_LEFT);
        dontUnderstandRadio.setMaxHeight(Double.MAX_VALUE);
        dontUnderstandRadio.setOnAction(e -> incrementDontUnderstandCounter());
        
        understandRadio.setStyle("-fx-text-fill: #0aee0a;");
        understandRadio.setAlignment(Pos.CENTER_LEFT);
        understandRadio.setMaxHeight(Double.MAX_VALUE);
        understandRadio.setOnAction(e -> incrementUnderstandCounter());
    }
    
    public void deselect() {
        topicButton.setStyle("");
        inUse = false;
    }
    
    
    public void use() {
        inUse = true;
    }
    
    public boolean isUsed() {
        return inUse;
    }
    
    public Button getTopicButton() {
        return topicButton;
    }
    
    public Label getDontUnderstandLabel() {
        return dontUnderstandLabel;
    }
    
    public Label getUnderstandLabel() {
        return understandLabel;
    }
    
    public Label getDontUnderstandCounterLabel() {
        return dontUnderstandCounterLabel;
    }
    
    public Label getUnderstandCounterLabel() {
        return understandCounterLabel;
    }
    
    public RadioButton getDontUnderstandRadio() {
        return dontUnderstandRadio;
    }
    
    public RadioButton getUnderstandRadio() {
        return understandRadio;
    }
    
    public ArrayList<Student> getStudentsDontUnderstandList() {
        return studentsDontUnderstandList;
    }
    
    public ArrayList<Student> getStudentsUnderstandList() {
        return studentsUnderstandList;
    }
    
    public void addMessagetoList(String type, String msgBody, String postedBy){
        getMessages().add(new Message(type, msgBody, postedBy));
    }
    
    @FXML
    public void incrementDontUnderstandCounter() {
        if (studentsUnderstandList.contains(mainInstance.getCurrentStudent())) {
            studentsUnderstandList.remove(mainInstance.getCurrentStudent());
            understandCounter = studentsUnderstandList.size();
        }
        if (!studentsDontUnderstandList.contains(mainInstance.getCurrentStudent())) {
            studentsDontUnderstandList.add(mainInstance.getCurrentStudent());
            dontUnderstandCounter = studentsDontUnderstandList.size();
        }
        understandCounterLabel.setText("" + understandCounter);
        dontUnderstandCounterLabel.setText("" + dontUnderstandCounter);
    }
    
    @FXML
    public void incrementUnderstandCounter() {
        if (studentsDontUnderstandList.contains(mainInstance.getCurrentStudent())) {
            studentsDontUnderstandList.remove(mainInstance.getCurrentStudent());
            dontUnderstandCounter = studentsDontUnderstandList.size();
        }
        if (!studentsUnderstandList.contains(mainInstance.getCurrentStudent())) {
            studentsUnderstandList.add(mainInstance.getCurrentStudent());
            understandCounter = studentsUnderstandList.size();
        } 
        understandCounterLabel.setText("" + understandCounter);
        dontUnderstandCounterLabel.setText("" + dontUnderstandCounter);
    }

    /**
     * @return the topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName the topicName to set
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * @return the messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    
    public boolean equals(Object o) {
        if (o == this)
            return true;
        Topic other = (Topic) o;
        if (this.topicName.equals(other.topicName))
            return true;
        return false;
    }

}
