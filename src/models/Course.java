package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import models.Topic;

public class Course implements Serializable {

    String courseName;
    String roomNum;
    String crsPassword;
    ArrayList<Topic> topicsList;
    HashMap<Student, String> sessionIDs;
    

    public Course(String cName, String rNum, String coursePswd){
        courseName = cName;
        roomNum = rNum;
        crsPassword = coursePswd;
        topicsList = new ArrayList<>();
        topicsList.add(new Topic("General Q&A", null));
        sessionIDs = new HashMap<>();
    }
    
    public Course(String cName, String rNum, String coursePswd, ArrayList<Topic> topics){
        courseName = cName;
        roomNum = rNum;
        crsPassword = coursePswd;
        topicsList = topics;
        sessionIDs = new HashMap<>();
    }

    public boolean authenticate(String course, String room, String password) {
        boolean result = false;
        if((course.equalsIgnoreCase(courseName)) && (room.equals(roomNum)) && (password.equals(crsPassword))){
            result = true;
        }

        return result;
    }
    
    public void resetSessionIDs() {
        sessionIDs = new HashMap<>();
    }
    
    public String getSessionID(Student student) {
        if (!sessionIDs.containsKey(student))
            createNewStudentSession(student);
        return sessionIDs.get(student);
    }
    
    public void createNewStudentSession(Student student) {
        String newSessionID = generateSessionID();
        sessionIDs.put(student, newSessionID);
    }
    
    public String generateSessionID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char randChar = (char)(rand.nextInt(26) + 'a');
            id.append(randChar);
        }
        rand = new Random();
        for (int i = 0; i < 3; i++) {
            int randInt = rand.nextInt(10);
            id.append(randInt);
        }
        return id.toString();
    }
    
    public String getRoomNum(){
        return roomNum;
    }
    
    public String getCourseName(){
        return courseName;
    }
    
    public String getCrsPassword(){
        return crsPassword;
    }
    
    public void addTopic(Topic topic) {
        topicsList.add(topic);
    }
    
    public ArrayList<Topic> getTopicsList() {
        return topicsList;
    }
    
    public void setTopicsList(ArrayList<Topic> topics) {
        topicsList = topics;
    }
    
    public boolean equals(Course other) {
        if (other == this)
            return true;
        Course o = (Course) other;
        if (this.courseName.equals(other.courseName))
            return true;
        return false;
    }
}