package dev.codeerror.sootreports.util;

public class TydiumCraftAPI {

    private String uuid;

    public TydiumCraftAPI(String uuid) { this.uuid = uuid; }

    public String getSkinURL(String type, String size, String flip) {

        String baseURL = "https://api.tydiumcraft.net/skin?uuid=" + this.uuid;
        StringBuilder urlBuilder = new StringBuilder(baseURL);

        if (type == null) {

            urlBuilder.append("&type=body");
            return new String(urlBuilder);

        } else if (type.equals("skin")) {

            urlBuilder.append("&type=skin&download");
            return new String(urlBuilder);

        }

        urlBuilder.append("&type=").append(type);

        if (size != null) {

            urlBuilder.append("&size=").append(Integer.parseInt(size));
            return new String(urlBuilder);

        }

        if ((type.equals("body") || type.equals("head")) && (flip.equals("left") || flip.equals("right"))) {

            urlBuilder.append("&direction=").append(flip);
            return new String(urlBuilder);

        }

        return new String(urlBuilder);

    }

}
