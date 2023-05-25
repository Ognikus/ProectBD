package com.example.testbd;

import java.sql.*;

public class DataBaseHandler {
    Connection connection;
    ResultSet resSet = null;

    public Connection getDBConnection() throws SQLException {
        String connectionStrings = "jdbc:postgresql://localhost:5432/labaBD";
        connection = DriverManager.getConnection(connectionStrings, "postgres", "admin");
        return connection;
    }

    public ResultSet getUser(String loginText, String passText) throws SQLException {
        String getUserQuery = "SELECT * FROM users WHERE login = ? AND password = ?";
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(getUserQuery);
        preparedStatement.setString(1, loginText);
        preparedStatement.setString(2, passText);
        return preparedStatement.executeQuery();
    }

    public ResultSet getUserName() throws SQLException {
        String getUserName = "SELECT login FROM users";

        try (PreparedStatement prSt = getDBConnection().prepareStatement(getUserName)) {
            resSet = prSt.executeQuery();
            return resSet;
        }
    }


    public boolean insertUser(User user) throws SQLException {
        String insertUser = "INSERT INTO users(login, password) VALUES (?, ?)";

        try (PreparedStatement prSt = getDBConnection().prepareStatement(insertUser)) {
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getPassword());
            int rowsAffected = prSt.executeUpdate();
            return rowsAffected > 0; // Возвращаем true, если были затронуты строки
        }
    }




    public ResultSet getGames() throws SQLException {
        String getGames = "SELECT * FROM games";
        PreparedStatement prST = getDBConnection().prepareStatement(getGames);

        resSet = prST.executeQuery();
        return resSet;
    }

    public void insertGames(Games games) throws SQLException {
        String insertGames = "INSERT INTO games(gamename, gamecategory, gameprice, gamecount) VALUES (?, ?, ?, ?)";
        PreparedStatement prST = connection.prepareStatement(insertGames);
        prST.setString(1, games.getGameName());
        prST.setString(2, games.getGameCategory());
        prST.setInt(3, games.getGamePrice());
        prST.setInt(4, games.getGameCount());
        prST.executeUpdate();
    }

}
