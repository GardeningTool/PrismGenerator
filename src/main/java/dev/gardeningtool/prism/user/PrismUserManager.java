package dev.gardeningtool.prism.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class PrismUserManager {

    private static final HashMap<Long, PrismUser> prismUsers = new HashMap<>();

    public static void init() {
        Path path = Paths.get("users/");
        if (!path.toFile().isDirectory()) {
            path.toFile().mkdir();
        }
        Stream.of(path.toFile().listFiles()).forEach(file -> {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = null;
            try {
                jsonObject = (JsonObject) jsonParser.parse(new FileReader(file));
            } catch (IOException exc) {
                exc.printStackTrace(); //shouldn't ever be thrown
            }
            long startTime = jsonObject.get("start").getAsLong(), endTime = jsonObject.get("end").getAsLong();
            prismUsers.put(Long.parseLong(file.getName().replace(".json", "")), new PrismUser(startTime, endTime));
        });
    }

    public static PrismUser from(long id) {
        if (!prismUsers.containsKey(id)) {
            prismUsers.put(id, new PrismUser());
        }
        return prismUsers.get(id);
    }
}
