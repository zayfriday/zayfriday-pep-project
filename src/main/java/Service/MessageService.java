package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;
import java.util.ArrayList;

public class MessageService {
    MessageDAO messageDAO;

    AccountDAO accountDAO = new AccountDAO();

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List <Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }

    public List <Message> getAllMessagesPostedByUser(int user_id){
        return this.messageDAO.getAllMessagesPostedByUser(user_id);
    }

    public Message getMessageById(int id){
        return this.messageDAO.getMessageById(id);
    }

    /* Creates message after checking that message_text isn't empty and not above 25 characters,
     * and checking that the posted_by id refers to an actual account.
     */
    public Message createMessage(Message message){
        if (message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255){
            return null;
        }
        if (this.accountDAO.getAccountById(message.getPosted_by()) == null){
            return null;
        }
        return this.messageDAO.createMessage(message);
    }

    /* Deletes message (if present) and returns the message that was deleted */
    public Message deleteMessageById(int id){ 
        Message m = this.messageDAO.getMessageById(id);
        boolean didDelete = this.messageDAO.deleteMessageById(id);   
        if (didDelete && !(m == null)){
            return m;
        }
        return null;
    }

    /* Updates message (if present) and returns the message that was deleted */
    public Message updateMessageById(int id, Message message){ 
        String new_text = message.getMessage_text();
        if (this.messageDAO.getMessageById(id) == null){
            return null;
        }
        if (new_text.isEmpty() || new_text.length() <= 255){
            return null;
        }
        boolean didUpdate = this.messageDAO.updateMessageById(id, new_text);
        if (didUpdate){
            return this.messageDAO.getMessageById(id);
        }
        return null;
    }
}
