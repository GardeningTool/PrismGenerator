package dev.gardeningtool.prism.accountstock;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;

public class AccountStock {

    private HashMap<Stock, List<String>> comboMap;

    public AccountStock() {
        comboMap = new HashMap<>();

    }

    private enum Stock {
        MINECRAFT("Minecraft"), SPOTIFY("Spotify"), NORDVPN("NordVPN");

        @Getter private String name;

        Stock(String name) {
            this.name = name;
        }
    }
}
