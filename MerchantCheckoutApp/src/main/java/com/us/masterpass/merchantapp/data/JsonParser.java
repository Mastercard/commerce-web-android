package com.us.masterpass.merchantapp.data;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Util json parser
 * <p>
 * Created by Sebastian Farias on 11/15/17.
 */
public class JsonParser {
    private static final String TAG = JsonParser.class.getSimpleName();

    /**
     * Get a String from a JSONObject, if the key doesn't exists return a default value
     *
     * @param object       JSONObject to search the key
     * @param key          Key to search in JSONObject
     * @param defaultValue Return this default value if key doesn't exists
     * @return String if key exists
     */
    public static String getString(JSONObject object, String key, String defaultValue) {
        String value = defaultValue;
        try {
            Object valueObject = object.get(key);
            if (valueObject.getClass().equals(String.class)) {
                value = valueObject.toString();
            }
        } catch (JSONException e) {
            Log.d(TAG, "Cannot get the key " + key + " from: " + object + ". Error: " + e.getMessage());
        }
        return value;
    }

    /**
     * Get JSONObject from string object
     *
     * @param object string of json object
     * @param key    Key to search in JSONObject
     * @return JSONObject json object
     */
    public static JSONObject getJSONObject(String object, String key) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = createJSONObject(object).getJSONObject(key);
        } catch (JSONException e) {
            Log.d(TAG, "Cannot create JSONObject from: " + object + " with the key: " + key + " Error: " + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * Create JSONArray from string object
     *
     * @param object String object to search the JSONArray
     * @param key    String key to search in object
     * @return JSONArray json array
     */
    public static JSONArray getJSONArray(String object, String key) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = createJSONObject(object).getJSONArray(key);
        } catch (JSONException e) {
            Log.d(TAG, "Cannot create jsonArray from: " + object + " with the key: " + key + " Error: " + e.getMessage());
        }
        return jsonArray;
    }

    /**
     * Create a JSONObject from string object
     *
     * @param object string object
     * @return JSONObject json object
     */
    public static JSONObject createJSONObject(String object) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(object);
        } catch (JSONException e) {
            Log.d(TAG, "Cannot create JSONObject with: " + object + ". Error: " + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * Create a JSONArray from string array
     *
     * @param object string object
     * @return JSONObject json array
     */
    public static JSONArray createJSONArray(String object) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(object);
        } catch (JSONException e) {
            Log.d(TAG, "Cannot create JSONArray with: " + object + ". Error: " + e.getMessage());
        }
        return jsonArray;
    }

    /**
     * Get the value of one JSONObject from the key
     *
     * @param object       JSONObject to get the value
     * @param key          String has contains the key
     * @param defaultValue String value if the key does not exists
     * @return String value
     */
    public static boolean getBoolean(JSONObject object, String key, boolean defaultValue) {
        boolean value = defaultValue;
        try {
            value = object.getBoolean(key);
        } catch (JSONException e) {
            Log.d(TAG, "Cannot get the boolean from: " + object + " with the key: " + key + " . Error: " + e.getMessage());
        }
        return value;
    }

    /**
     * Get the value of one JSONObject from the key
     *
     * @param object       JSONObject to get the value
     * @param key          String has contains the key
     * @param defaultValue Int value if the key does not exists
     * @return Int value
     */
    public static int getInt(JSONObject object, String key, int defaultValue) {
        int value = defaultValue;
        try {
            value = object.getInt(key);
        } catch (JSONException e) {
            Log.d(TAG, "Cannot get the int from: " + object + " with the key: " + key + " . Error: " + e.getMessage());
        }
        return value;
    }
}
