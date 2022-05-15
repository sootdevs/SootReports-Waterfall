package dev.codeerror.sootreports.util;

import org.jetbrains.annotations.Nullable;

public class TydiumCraftAPI {

    private final String uuid;

    public TydiumCraftAPI(String uuid) { this.uuid = uuid; }

    public String getSkinURL(@Nullable String type, @Nullable String size, @Nullable String flip) {

        String baseURL = "https://api.tydiumcraft.net/v1/players/skin?uuid=" + this.uuid;
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

        if (flip != null && (type.equals("body") || type.equals("head"))) {

            if ((flip.equals("left") || flip.equals("right"))) {

                urlBuilder.append("&direction=").append(flip);

            } else {

                urlBuilder.append("&direction=right");

            }

            return new String(urlBuilder);

        }

        return new String(urlBuilder);

    }

}
