package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    /* Get all Accounts */
    public List <Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List <Account> accounts = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), 
                    rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    /* Get account given an id parameter */
    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                    rs.getString("password"));
                return account;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean isUserValid (Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (rs.getString("username") == account.getUsername()){
                    return true;
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;     
    }

    public boolean isUserCredentialsValid (Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (rs.getString("username") == account.getUsername()){
                    if (rs.getString("password") == account.getPassword()){
                        return true;
                    }
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;     
    }

    /* Get account given an username parameter */
    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                    rs.getString("password"));
                return account;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    /* Adds an account with an auto generated account_id */
    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account (username, password) values (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            
            ps.executeUpdate();

            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
