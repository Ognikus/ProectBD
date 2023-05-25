package com.example.testbd;

public class Games {
    String gameName;
    String gameCategory;
    Integer gamePrice;
    Integer gameCount;


    public Games(String gameName, String gameCategory, String gamePrice, String gameCount) {
        this.gameName = gameName;
        this.gameCategory = gameCategory;
        this.gamePrice = Integer.valueOf(gamePrice);
        this.gameCount = Integer.valueOf(gameCount);
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(String gameCategory) {
        this.gameCategory = gameCategory;
    }

    public Integer getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(Integer gamePrice) {
        this.gamePrice = gamePrice;
    }

    public Integer getGameCount() {
        return gameCount;
    }

    public void setGameCount(Integer gameCount) {
        this.gameCount = gameCount;
    }
}
