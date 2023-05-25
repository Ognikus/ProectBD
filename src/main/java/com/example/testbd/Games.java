package com.example.testbd;

public class Games {
    private String gamename;
    private String category_id;
    private Integer gameprice;
    private Integer gamecount;


    public Games(String gamename, String category_id, String gameprice, String gamecount) {
        this.gamename = gamename;
        this.category_id = category_id;
        this.gameprice = Integer.valueOf(gameprice);
        this.gamecount = Integer.valueOf(gamecount);
    }

    public String getGameName() {
        return gamename;
    }

    public void setGameName(String gamename) {
        this.gamename = gamename;
    }

    public String  getGameCategory() {
        return category_id;
    }

    public void setGameCategory(String category_id) {
        this.category_id = category_id;
    }

    public Integer getGamePrice() {
        return gameprice;
    }

    public void setGamePrice(Integer gameprice) {
        this.gameprice = gameprice;
    }

    public Integer getGameCount() {
        return gamecount;
    }

    public void setGameCount(Integer gamecount) {
        this.gamecount = gamecount;
    }
}
