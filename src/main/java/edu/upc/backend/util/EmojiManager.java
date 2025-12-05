package edu.upc.backend.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class EmojiManager {

    private static JSONObject emojiMap;

    static {
        JSONParser parser = new JSONParser();
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(EmojiManager.class.getClassLoader().getResourceAsStream("emoji.json")))) {
            emojiMap = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            emojiMap = new JSONObject(); // Initialize as empty to avoid NullPointerException
        }
    }

    public static String getEmoji(int id) {
        return (String) emojiMap.getOrDefault(String.valueOf(id), "❓"); // Default to a question mark emoji
    }
}
