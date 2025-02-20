package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //endpoints
        app.post("/register", this::registrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesPostedByUserHandler);
        return app;
    }

    // Handlers

    /* Register a user with username and password (no account_id) upon POST req */
    private void registrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.accountRegistration(account);
        if (newAccount == null){
            ctx.status(400);
        }
        else {
            ctx.json(om.writeValueAsString(newAccount));
        }
    }

    /* User Login with username and password (no account_id) upon POST req */
    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.accountLogin(account);
        if (loggedInAccount == null){
            ctx.status(401);
        }
        else {
            ctx.json(om.writeValueAsString(loggedInAccount));
        }
    }

    /* Returns all messages upon GET request */
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List <Message> messages = messageService.getAllMessages();
        ctx.json(messages);

    }

    /* Gets all messages posted by a specific user upon GET req */
    private void getAllMessagesPostedByUserHandler(Context ctx) throws JsonProcessingException {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAllMessagesPostedByUser(account_id));

    }

    /* Gets message by message_id upon GET req. Response body contains message if present */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        MessageService ms = new MessageService();
        ObjectMapper om = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ms.getMessageById(message_id);
        if (message == null){
            ctx.json("");
        }
        else {
            ctx.json(om.writeValueAsString(message));
        }
    }

    /* Creates a message upon POST request, response contains a client error if message
     *  isnt successfully created
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage == null){
            ctx.status(400);
        }
        else {
            ctx.json(om.writeValueAsString(createdMessage));
        }
    }

    /* Deletes message by message_id upon POST req and response body contains the deleted message
     *  if the message was successfully deleted.
     */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageById(message_id);
        if (message == null){
            ctx.json("");
        }
        else {
            ctx.json(om.writeValueAsString(message));
        }
    }

    /* Updates message by message_id upon PATCH req. Response contains client error 
     *  if user isnt updated succesfullly and the new message if it is
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message_text = om.readValue(ctx.body(), Message.class);
        Message new_message = messageService.updateMessageById(message_id, message_text);
        if (new_message == null){
            ctx.status(400);
        }
        else {
            ctx.json(om.writeValueAsString(new_message));
        }
    }

}