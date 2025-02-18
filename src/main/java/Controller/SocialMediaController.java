package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

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
    AccountService accountService;

    public SocialMediaController(){
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create().start(8080);

        //endpoints
        app.post("register", this::registrationHandler);

        return app;
    }

    // Handlers

    /* Register a user with username and password (no account_id) */
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


}