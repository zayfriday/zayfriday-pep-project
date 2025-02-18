package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;
import java.util.ArrayList;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    // public List <Message> getAllMessages(){

    // }

}
