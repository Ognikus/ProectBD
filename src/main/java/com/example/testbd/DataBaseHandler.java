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
        String getGames = "SELECT games.id, games.gamename, categorygames.categoryname, games.gameprice, games.gamecount " +
                "FROM games " +
                "JOIN categorygames ON games.gamecategory_id = categorygames.id;";

        PreparedStatement prST = getDBConnection().prepareStatement(getGames);

        resSet = prST.executeQuery();
        return resSet;
    }

    public void insertGames(Games games) throws SQLException {
        String insertGames = "INSERT INTO games(gamename, gamecategory_id, gameprice, gamecount) VALUES (?, ?, ?, ?)";
        PreparedStatement prST = connection.prepareStatement(insertGames);
        prST.setString(1, games.getGameName());
        prST.setInt(2, Integer.parseInt(games.getGameCategory()));
        prST.setInt(3, games.getGamePrice());
        prST.setInt(4, games.getGameCount());
        prST.executeUpdate();
    }

    public void updateGames(Games games) throws SQLException {
        String updateQuery = "UPDATE games SET gamename = ?, gamecategory_id = ?, gameprice = ?, gamecount = ? WHERE id = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(updateQuery);
        prSt.setString(1, games.getGameName());
        prSt.setInt(2, Integer.parseInt(games.getGameCategory()));
        prSt.setInt(3, games.getGamePrice());
        prSt.setInt(4, games.getGameCount());
        prSt.setInt(5, games.getGameId());
        prSt.executeUpdate();
    }

    public void deleteGame(int gameId) throws SQLException {
        String deleteQuery = "DELETE FROM games WHERE id = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(deleteQuery);
        prSt.setInt(1, gameId);
        prSt.executeUpdate();
    }

    public ResultSet getGamesSearch(String gameName) throws SQLException{
        String searchQuery = "SELECT * FROM games WHERE gamename = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(searchQuery);
        prSt.setString(1, gameName);

        resSet = prSt.executeQuery();
        return resSet;
    }


    public ResultSet getCategorySearch(String categoryName) throws SQLException{
        String searchCategory = "SELECT * FROM categorygames WHERE categoryname = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(searchCategory);
        prSt.setString(1, categoryName);

        resSet = prSt.executeQuery();
        return resSet;
    }

    public void insertCategory(Category category) throws SQLException{
        String insertCategory = "INSERT INTO categorygames(id, categoryname) VALUES (?, ?)";
        PreparedStatement prST = connection.prepareStatement(insertCategory);
        prST.setInt(1, category.getCategoryId());
        prST.setString(2, category.getCategoryName());
        prST.executeUpdate();
    }

    public ResultSet getCategory() throws SQLException {
        String getCategory = "SELECT id, categoryname FROM categorygames";

        PreparedStatement prSt = getDBConnection().prepareStatement(getCategory);

        resSet = prSt.executeQuery();
        return resSet;
    }

    public void updateCategory(Category category) throws SQLException{
        String updateQuery = "UPDATE categorygames SET categoryname = ? WHERE id = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(updateQuery);
        prSt.setString(1, category.getCategoryName());
        prSt.setInt(2, category.getCategoryId());
        prSt.executeUpdate();
    }

    public void deleteCategory(int categoryId) throws SQLException {
        String deleteQuery = "DELETE FROM categorygames  WHERE id = ?";
        PreparedStatement prSt= getDBConnection().prepareStatement(deleteQuery);
        prSt.setInt(1, categoryId);
        prSt.executeUpdate();
    }
}
