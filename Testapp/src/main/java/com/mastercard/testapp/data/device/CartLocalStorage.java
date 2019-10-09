package com.mastercard.testapp.data.device;

import android.content.Context;
import android.util.Log;
import com.mastercard.testapp.domain.Constants;
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
 * Local class to handle persisted data on device.
 * <p>
 * Created by Sebastian Farias on 10-10-17.
 */
public class CartLocalStorage implements Serializable {

  private static final long serialVersionUID = 6262263888850263238L;
  private static String TAG = CartLocalStorage.class.getSimpleName();
  private static CartLocalStorage sCartLocalStorage;
  private static String FILENAME = "CartLocalStorage.fwd";
  private static Context context;
  private HashMap<String, HashMap<String, Object>> cartLocalList = new HashMap<>();

  private CartLocalStorage() {
    loadData();
  }

  /**
   * Get instance of CartLocalStorage.
   *
   * @param contextApp android application context
   * @return instance of class
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

  /**
   * Get persisted data on device
   */
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
    } catch (NullPointerException e) {
      Log.e(TAG, "Null pointer ex ");
    } catch (IOException e) {
      Log.e(TAG, "Error while reading the file ");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      Log.e(TAG, "Error while deserializing");
      e.printStackTrace();
    }
  }

  /**
   * Persist data on device.
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
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      Log.e(TAG, "Error while writing the file ");
      e.printStackTrace();
    }
  }

  /**
   * Remove local data with the items on shopping cart.
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
   * Set shopping cart local with list of {@link CartLocalObject}.
   *
   * @param key identifier of the list on model
   * @param cartLocal list of items on list
   * @param cartTotal total price of elements on shopping cart
   */
  public void setCartLocal(String key, List<CartLocalObject> cartLocal, double cartTotal) {
    HashMap<String, Object> entry = new HashMap<>();
    entry.put("cartLocal", cartLocal);
    entry.put("cartTotal", cartTotal);
    cartLocalList.put(key, entry);
  }

  /**
   * Get shopping cart saved on local.
   *
   * @param key the key
   * @return the shopping cart local list
   */
  public List<CartLocalObject> getCartLocal(String key) {
    checkIfKeyExists(key);
    List<CartLocalObject> list =
        (List<CartLocalObject>) this.cartLocalList.get(key).get("cartLocal");
    if (list != null) {
      return list;
    }
    return new ArrayList<>();
  }

  /**
   * Get total items on cart.
   *
   * @param key identifier of the list on model
   * @return total price of shopping cart
   */
  public double getCartTotal(String key) {
    double total = Constants.TAX_VALUE;
    if (this.cartLocalList.containsKey(key)) {
      if (this.cartLocalList.get(key).containsKey("cartTotal")) {
        if (this.cartLocalList.get(key).containsKey("cartLocal")) {
          List<CartLocalObject> cartList =
              ((ArrayList) this.cartLocalList.get(key).get("cartLocal"));
          for (CartLocalObject cart : cartList) {
            total += cart.getTotalPrice();
          }
        }
      }
    }
    return total;
  }

  /**
   * Check if exist list with key selected
   *
   * @param key identifier of the list on model
   */
  private void checkIfKeyExists(String key) {
    if (!cartLocalList.containsKey(key)) {
      HashMap<String, Object> entry = new HashMap<>();
      entry.put("cartLocal", null);
      cartLocalList.put(key, entry);
    }
  }
}
