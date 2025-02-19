package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;
import java.util.ArrayList;

public class AccountService {
    private AccountDAO accountDAO;

    /* No args constructor */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /* Constructor when an accountDAO is provided */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /* Method to register account (add to accountDAO). Before adding checks that:
     *  username isn't empty, username isn't already used, and password is 4+ characters
     */
    public Account accountRegistration(Account account){
        if (account.getUsername().isEmpty()) {
            return null;
        } else if (this.accountDAO.getAccountByUsername(account.getUsername()) != null){
            return null;
        } else if (account.getPassword().length() < 4){
            return null;
        }
        else {
            return this.accountDAO.addAccount(account);
        }
    }

    /* Method to Login. Only if username and password match */
    public Account accountLogin(Account account){
        if (this.accountDAO.isUserValid(account) && this.accountDAO.isUserCredentialsValid(account)){
            return this.accountDAO.getAccountByUsername(account.getUsername());
        }
        return null;
    }
}
