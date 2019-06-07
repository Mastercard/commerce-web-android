package com.us.masterpass.merchantapp.data.external;

import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.domain.model.Item;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class ItemExternalDataSource implements ItemDataSource {
  private static ItemExternalDataSource INSTANCE;

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static ItemExternalDataSource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ItemExternalDataSource();
    }
    return INSTANCE;
  }

  // Prevent direct instantiation.
  private ItemExternalDataSource() {
  }

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

  private List<Item> itemsToList(List<Item> items, String responseString) {
    try {
      String urlImagesRepo = MasterpassUrlConstants.URL_IMAGE_REPO;
      JSONArray jsonArray = new JSONArray(responseString);
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
    //Gets item
  }

  @Override public void addItem(@NonNull Item item, GetItemOnCartCallback callback) {
    //Add Item
  }

  @Override public void removeItem(@NonNull Item item, GetItemOnCartCallback callback) {
    //Remove Item
  }

  @Override public void removeAllItem(@NonNull Item item, GetItemOnCartCallback callback) {
    //Remove all Item
  }

  @Override public void getItemsOnCart(GetItemOnCartShippingOptionCallback callback) {
    //Gets Items on Cart
  }

  @Override public void saveItem(@NonNull Item item) {
    //Save Item
  }

  @Override public void completeItem(@NonNull Item proud) {
    //Complete Item
  }

  @Override public void completeItem(@NonNull String itemId) {
    //Complete Item
  }

  @Override public void activateItem(@NonNull Item item) {
    //Activate Item
  }

  @Override public void activateItem(@NonNull String itemId) {
    //Activate Item
  }

  @Override public void clearCompletedItems() {
    //Clear completed Item
  }

  @Override public void refreshItems() {
    //Refresh Items
  }

  @Override public void deleteAllItems() {
    //Delete all Items
  }

  @Override public void deleteItem(@NonNull String itemId) {
    //Delete Item
  }
}