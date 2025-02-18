package Service;

import Util.ConnectionUtil;
import DAO.AccountDAO;
import Model.Account;

import java.util.List;

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

    /* Method to register account (add to accountDAO) according to specific requirements */
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

    // public Account accountLogin(Account account){

    // }

}
