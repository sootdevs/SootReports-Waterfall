package dev.codeerror.sootreports.util;

public class TydiumCraftAPI {

    private String uuid;

    public TydiumCraftAPI(String uuid) { this.uuid = uuid; }

    public String getSkinURL(String type, String size, String flip) {

        String skin = "https://api.tydiumcraft.net/skin?uuid=" + this.uuid;

        return skin;

    }

}
