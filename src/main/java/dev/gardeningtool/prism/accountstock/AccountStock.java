package dev.gardeningtool.prism.accountstock;

import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

//TODO: Use queues/dequeues instead of List<String> for storing combos
public class AccountStock {

    private final HashMap<Stock, List<String>> comboMap = new HashMap<>();
    private static AccountStock instance;

    public AccountStock() {
        List<File> files = new ArrayList<>();
        Path path = Paths.get("stock/");
        if (!path.toFile().isDirectory()) {
            path.toFile().mkdir();
            Stream.of(Stock.values()).forEach(type -> {
                File file = new File(String.format("stock/%s.txt", type.name()));
                try {
                    file.createNewFile();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            });
            throw new RuntimeException("No stock found! Exiting!"); //If there's no stock, the generator should automatically shut down, since having no stock renders the generator pointless
        }
        Stream.of(Stock.values()).forEach(type -> {
            File file = new File(String.format("stock/%s.txt", type.name()));
            files.add(file);
            List<String> accounts = new ArrayList<>();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (IOException exc) {
                exc.printStackTrace();
            }
            String line = "";
            try {
                while((line = reader.readLine()) != null) {
                    accounts.add(line);
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
            comboMap.put(type, accounts);
        });
        instance = this;
    }

    public static AccountStock getInstance() {
        return instance;
    }

    public void saveAll() {
        Stream.of(Stock.values()).forEach(type -> {
            Path path = Paths.get(String.format("stock/%s.txt", type.name()));
            try {
                Files.write(path, comboMap.get(type));
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
    }

    //Can throw an exception but it's caught in the the GenerateCommand class
    public String getAccount(Stock stockType) {
        List<String> list = comboMap.get(stockType);
        String account = list.get(0);
        list.remove(0);
        return account;
    }

    public enum Stock {
        MINECRAFT("Minecraft")/*, SPOTIFY("Spotify"), NORDVPN("NordVPN")*/;

        @Getter private String name;

        Stock(String name) {
            this.name = name;
        }
    }
}
