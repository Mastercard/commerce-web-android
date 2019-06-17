package com.us.masterpass.merchantapp.data.device;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartLocalStorage implements Serializable {

    private static final long serialVersionUID = 6262263888850263238L;
    private static String TAG = CartLocalStorage.class.getSimpleName();
    private static CartLocalStorage sCartLocalStorage;
    private static String FILENAME = "CartLocalStorage.fwd";
    private HashMap<String, HashMap<String, Object>> cartLocalList = new HashMap<>();
    private static Context context;
    private String cartLocal = "cartLocal";
    private String cartTotal = "cartTotal";

    private CartLocalStorage() {
        loadData();
    }

    /**
     * Gets instance.
     *
     * @param contextApp the context app
     * @return the instance
     */
    public static CartLocalStorage getInstance(Context contextApp) {
        context = contextApp;
        if (sCartLocalStorage == null) {
            synchronized (CartLocalStorage.class) {
                sCartLocalStorage = new CartLocalStorage();
            }
        }
        return sCartLocalStorage;
    }

    private void loadData() {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = context.openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);

            sCartLocalStorage = (CartLocalStorage) ois.readObject();
            ois.close();
            fis.close();
            cartLocalList = sCartLocalStorage.cartLocalList;
        } catch (IOException e) {
            Log.e(TAG, "Error while reading the file ");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Error while deserializing");
        }
    }

    /**
     * Persist data.
     */
    public void persistData() {
        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            try {
                out = new ObjectOutputStream(fos);
                out.writeObject(sCartLocalStorage);
                out.close();
            } catch (IOException e) {
                Log.e(TAG, "Error while writing the file ");
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error while writing the file ");
        }

    }

    /**
     * Remove model.
     */
    public void removeModel() {
        FileOutputStream fos;
        ObjectOutputStream out;

        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fos);
            out.reset();
            cartLocalList.clear();
        } catch (Exception e) {
            Log.e(TAG, "Error trying delete file ");
        }
    }

    /**
     * Sets cart local.
     *
     * @param key       the key
     * @param cartLocal the cart local
     * @param cartTotal the cart total
     */
    public void setCartLocal(String key, List<CartLocalObject> cartLocal, double cartTotal) {
        HashMap<String, Object> entry = new HashMap<>();
        entry.put(this.cartLocal, cartLocal);
        entry.put(this.cartTotal, cartTotal);
        cartLocalList.put(key, entry);
    }

    /**
     * Gets cart local.
     *
     * @param key the key
     * @return the cart local
     */
    public List<CartLocalObject> getCartLocal(String key) {
        checkIfKeyExists(key);
        List<CartLocalObject> list = (List<CartLocalObject>) this.cartLocalList.get(key).get(
            cartLocal);
        if (list != null) {
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * Gets cart total.
     *
     * @param key the key
     * @return the cart total
     */
    public double getCartTotal(String key) {
        double total = 0;
        if (this.cartLocalList.get(key).containsKey(cartTotal)) {
            total = (double) this.cartLocalList.get(key).get(cartTotal);
        }
        return total;
    }

    private void checkIfKeyExists(String key) {
        if (!cartLocalList.containsKey(key)) {
            HashMap<String, Object> entry = new HashMap<>();
            entry.put(cartLocal, null);
            cartLocalList.put(key, entry);
        }
    }
}
