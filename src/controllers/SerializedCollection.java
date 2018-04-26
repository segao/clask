package controllers;

import clask.ClaskApp;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import models.Course;
import models.Instructor;
import models.Student;
import models.Topic;

public class SerializedCollection {
    private static ClaskApp mainInstance;
    private static SerializedCollection instance;
    
    
    final String listOfStudentsFileName = "listOfStudents.ser";
    final String listOfCoursesFileName = "listofCourses.ser";
    final String listOfInstructorsFileName = "listOfInstructors.ser";
    
    public SerializedCollection(){
        mainInstance = ClaskApp.getInstance();
        instance = this;
        this.readStudentListFile();
        this.readInstructorListFile();
        this.readCourseListFile();
        
        if(mainInstance.getListOfStudents().isEmpty() || mainInstance.getListOfStudents() == null){
            this.createStudentList();
            this.writeStudentListFile();
            this.readStudentListFile();
        }
        
        if(mainInstance.getListOfInstructors().isEmpty() || mainInstance.getListOfInstructors() == null){
            this.createInstructorList();
            this.writeInstructorListFile();
            this.readInstructorListFile();
        }
        
        if(mainInstance.getListOfCourses().isEmpty() || mainInstance.getListOfCourses() == null){
            this.createCourseList();
            this.writeCourseListFile();
            this.readCourseListFile();
        }
        
        this.printUserList();
        this.printCourseList();
    }
    
    // reads the list of students
    public void readStudentListFile(){
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(listOfStudentsFileName);
            in = new ObjectInputStream(fis);
            mainInstance.setListOfStudents((ArrayList<Student>)in.readObject());
            in.close();
            if(!mainInstance.getListOfStudents().isEmpty()){
                System.out.println("There are users in the user list");
            }
        }
        catch(IOException | ClassNotFoundException ex) {
        }
    }
    
    // reads the list of users
    public void readInstructorListFile(){
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(listOfInstructorsFileName);
            in = new ObjectInputStream(fis);
            mainInstance.setListOfInstructors((ArrayList<Instructor>)in.readObject());
            in.close();
            if(!mainInstance.getListOfInstructors().isEmpty()){
                System.out.println("There are instructors in the instructor list");
            }
        }
        catch(IOException | ClassNotFoundException ex) {
        }
    }
    
    //reads the list of courses
    public void readCourseListFile(){
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(listOfCoursesFileName);
            in = new ObjectInputStream(fis);
            mainInstance.setListOfCourses((ArrayList<Course>)in.readObject());
            for (Course course : mainInstance.getListOfCourses()) {
                course.resetSessionIDs();
            }
            in.close();
            if(!mainInstance.getListOfCourses().isEmpty()){
                System.out.println("There are courses in the course list");
            }
        }
        catch(IOException | ClassNotFoundException ex) {
        }
    }
    
    //writes list of users
    public void writeStudentListFile(){
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(listOfStudentsFileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(mainInstance.getListOfStudents());
            out.close();
        }
        catch(IOException ex) {
        }
    }
    
    //writes list of users
    public void writeInstructorListFile(){
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(listOfInstructorsFileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(mainInstance.getListOfInstructors());
            out.close();
        }
        catch(IOException ex) {
        }
    }
    
    //writes list of courses
    public void writeCourseListFile(){
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(listOfCoursesFileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(mainInstance.getListOfCourses());
            out.close();
        }
        catch(IOException ex) {
        }
    }
    
    // creates students if list is empty
    public void createStudentList() {
        mainInstance.getListOfStudents().add(new Student("Adam", "Smith", "as001", "pass", "0001"));
        mainInstance.getListOfStudents().add(new Student("Ian", "Roach","ir002", "pass2", "0002"));
        mainInstance.getListOfStudents().add(new Student("Jeffrey", "Rowe", "jmr202", "pass3", "0003"));
        System.out.println("Student list created");
    }
    //creates instructors if list is empty
    public void createInstructorList() {
        mainInstance.getListOfInstructors().add(new Instructor("Richard", "Lomotey", "rkl5137", "pass4", "5137"));
        System.out.println("Instructor list created");
    }
    
    //Creates courses if list is empty
    public void createCourseList(){
        Course courseToAdd = new Course("IST311", "3", "room3");
        mainInstance.getListOfCourses().add(courseToAdd);
        System.out.println("Courses created");
    }
    
    //prints the list of users
    public void printUserList(){
        System.out.println("The Student list has these users:");
        for(int i = 0; i < mainInstance.getListOfStudents().size(); i++){
            Student curStudent = (Student) mainInstance.getListOfStudents().get(i);
            System.out.println(curStudent.getStudentName());
        }
    
        System.out.println("The Instructor list has these users:");
        for(int i = 0; i < mainInstance.getListOfInstructors().size(); i++){
            Instructor curInstructor = (Instructor) mainInstance.getListOfInstructors().get(i);
            System.out.println(curInstructor.getInstructorName());
        }
    }
    
    public void printCourseList(){
        System.out.println("The course list has these names");
        for(int i = 0; i < mainInstance.getListOfCourses().size(); i++){
            Course curCourse = (Course) mainInstance.getListOfCourses().get(i);
            System.out.println(curCourse.getCourseName());
        }
    }
    
    public void addToListOfStudents(String fName, String lName, String Username, String Password, String blank){
        mainInstance.getListOfStudents().add(new Student(fName, lName, Username, Password, blank));
        writeStudentListFile();
    }
    
    public void addToListOfInstructors(String fName, String lName, String Username, String Password, String blank){
        mainInstance.getListOfInstructors().add(new Instructor(fName, lName, Username, Password, blank));
        writeInstructorListFile();
    }

    
    public void addToListOfCourses(Course courseToAdd){
        if (mainInstance.getListOfCourses().contains(courseToAdd)) {
            int index = mainInstance.getListOfCourses().indexOf(courseToAdd);
            mainInstance.getListOfCourses().get(index).setTopicsList(courseToAdd.getTopicsList());
        } else
            mainInstance.getListOfCourses().add(courseToAdd);
        writeCourseListFile();
    }
   
    
    
    public boolean checkIfUsernameExist(String userName, String type){
        boolean result = false;
        switch (type) {
            case "Student":
                for(int i = 0; i < mainInstance.getListOfStudents().size(); i++){
                    if(userName.equals(mainInstance.getListOfStudents().get(i).getStudentUsername()))
                        result = true;
                }   break;
            case "Instructor":
                for(int i = 0; i < mainInstance.getListOfInstructors().size(); i++){
                    if(userName.equals(mainInstance.getListOfInstructors().get(i).getInstructorUsername()))
                        result = true;
                }   break;
            default:
                ;
                break;
        }
        return result;
    }
    
    public boolean checkIfCourseNameExist(String course){
        boolean result = false;
        for(int i = 0; i < mainInstance.getListOfCourses().size(); i++){
                    if(course.equalsIgnoreCase(mainInstance.getListOfCourses().get(i).getCourseName()))
                        result = true;
        }
        return result;
    }
    
    public boolean verifyRoomNumber(String course, String room){
        boolean result = false;
        for(int i = 0; i < mainInstance.getListOfCourses().size(); i++){
            if(course.equalsIgnoreCase(mainInstance.getListOfCourses().get(i).getCourseName()) && room.equalsIgnoreCase(mainInstance.getListOfCourses().get(i).getRoomNum())){
                result = true;
            }
        }
        return result;
    }
    
    public ArrayList getAvail_rooms(){
        ArrayList<String> rooms_avail = new ArrayList<>();
        ArrayList<String> rooms_used = new ArrayList<>();
        ArrayList<String> rooms = new ArrayList<>();
        for(int i = 0; i < mainInstance.getListOfCourses().size(); i++){
            rooms_used.add(mainInstance.getListOfCourses().get(i).getRoomNum());
        }
        for(int i = 1; i <= 10; i++){
            rooms.add(Integer.toString(i));                
        }
        rooms_avail = new ArrayList<>(rooms);
        rooms_avail.removeAll(rooms_used);
        return rooms_avail;
    }
    
    public static SerializedCollection getInstance(){
        return instance;
    }
     
}
