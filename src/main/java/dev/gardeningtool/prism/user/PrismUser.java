package dev.gardeningtool.prism.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PrismUser {

    private long startTime, endTime;

    public boolean hasActiveSub() {
        return System.currentTimeMillis() < endTime;
    }

}
