package com.mastercard.testapp.data.external;

import android.support.annotation.NonNull;
import com.mastercard.testapp.data.ItemDataSource;
import com.mastercard.testapp.domain.model.Item;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Manage items from server
 * <p>
 * Created by Sebastian Farias on 08-10-17.
 */
public class ItemExternalDataSource implements ItemDataSource {
  private static ItemExternalDataSource INSTANCE;

  // Prevent direct instantiation.
  private ItemExternalDataSource() {
  }

  /**
   * Get instance of class.
   *
   * @return class instance
   */
  public static ItemExternalDataSource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ItemExternalDataSource();
    }
    return INSTANCE;
  }

  /**
   * Get items from URL using GET
   *
   * @param callback listener ready to return list of items
   */
  @Override public void getItems(@NonNull LoadItemsCallback callback) {
    final RestApi restApi = new RestApi();
    List<Item> items = new ArrayList<>();
    String response = restApi.getItems();

    if (response == null) {
      callback.onDataNotAvailable();
    } else {
      itemsToList(items, response);
      callback.onItemsLoaded(items);
    }
  }

  /**
   * Manage the response from server and returns a list of {@link Item}
   *
   * @param items empty {@link Item} list
   * @param responseString response from server
   * @return list of {@link Item}
   */
  private List<Item> itemsToList(List<Item> items, String responseString) {
    try {
      String urlImagesRepo = MasterpassUrlConstants.URL_IMAGE_REPO;
      JSONArray jsonArray = new JSONArray(responseString.toString());
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Item item = new Item();
        item.setProductId(jsonObject.getString("productId"));
        item.setName(jsonObject.getString("name"));
        item.setPrice(jsonObject.getDouble("price"));
        item.setSalePrice(jsonObject.getDouble("salePrice"));
        item.setImage(urlImagesRepo + jsonObject.getString("image"));
        item.setDescription(jsonObject.getString("description"));

        items.add(item);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return items;
  }

  @Override public void getItem(@NonNull String itemId, @NonNull GetItemCallback callback) {
  }

  @Override public void addItem(@NonNull Item item, GetItemOnCartCallback callback) {

  }

  @Override public void removeItem(@NonNull Item item, GetItemOnCartCallback callback) {

  }

  @Override public void removeAllItem(@NonNull Item item, GetItemOnCartCallback callback) {

  }

  @Override public void getItemsOnCart(GetItemOnCartShippingOptionCallback callback) {

  }

  @Override public void saveItem(@NonNull Item item) {

  }

  @Override public void refreshItems() {

  }

  @Override public void deleteAllItems() {

  }

  @Override public void deleteItem(@NonNull String itemId) {

  }
}