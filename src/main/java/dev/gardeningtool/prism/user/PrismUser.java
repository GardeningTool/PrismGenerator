package dev.gardeningtool.prism.user;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data @AllArgsConstructor @NoArgsConstructor
public class PrismUser {

    private long startTime, endTime;

    public boolean hasActiveSub() {
        return System.currentTimeMillis() < endTime;
    }

    public void saveFile(long id) {
        Path path = Paths.get(String.format("users/%d.json", id));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("start", startTime);
        jsonObject.addProperty("end", endTime);
        try {
            Files.write(path, jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
