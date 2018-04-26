package models;

public class Message {
    
    private String type;
    private String messageBody;
    private String postedBy;
    
    public Message(String type, String messageBody, String postedBy){
        this.type = type;
        this.messageBody = messageBody;
        this.postedBy = postedBy;
    }
    
    public String returnMessage(){
        return(type + messageBody + postedBy);
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the messageBody
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * @param messageBody the messageBody to set
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * @return the postedBy
     */
    public String getPostedBy() {
        return postedBy;
    }

    /**
     * @param postedBy the postedBy to set
     */
    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
    
    
            
    
}
