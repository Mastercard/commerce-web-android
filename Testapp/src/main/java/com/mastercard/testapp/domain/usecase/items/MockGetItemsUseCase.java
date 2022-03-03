package com.mastercard.testapp.domain.usecase.items;

import android.content.Context;
import android.content.res.AssetManager;
import androidx.annotation.NonNull;
import com.mastercard.testapp.data.ItemRepository;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Get items from external repository in this case, HTTP request
 */
public class MockGetItemsUseCase
    extends UseCase<MockGetItemsUseCase.RequestValues, MockGetItemsUseCase.ResponseValue> {

  private final Context mContext;

  /**
   * Instantiates a new Get items use case.
   *
   * @param itemsRepository the items repository
   */
  public MockGetItemsUseCase(@NonNull ItemRepository itemsRepository, Context context) {
    mContext = context;
  }

  @Override protected void executeUseCase(final RequestValues values) {

    AssetManager assetManager = mContext.getAssets();
    try {
      InputStream is = assetManager.open("product_list.json");
      ByteArrayOutputStream result = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int length;

      while ((length = is.read(buffer)) != -1) {
        result.write(buffer, 0, length);
      }
      ResponseValue responseValue = new ResponseValue(itemsToList(result.toString()));
      getUseCaseCallback().onSuccess(responseValue);
    } catch (IOException e) {
      getUseCaseCallback().onError();
    }
  }

  /**
   * Manage the response from server and returns a list of {@link Item}
   *
   * @param responseString response from server
   * @return list of {@link Item}
   */
  private List<Item> itemsToList(String responseString) {
    List<Item> items = new ArrayList<>();
    try {
      JSONArray jsonArray = new JSONArray(responseString);
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Item item = new Item();
        item.setProductId(jsonObject.getString("productId"));
        item.setName(jsonObject.getString("name"));
        item.setPrice(jsonObject.getDouble("price"));
        item.setSalePrice(jsonObject.getDouble("salePrice"));
        String mImageEndPoint = jsonObject.getString("image");
        int lastSeparatorIndex = mImageEndPoint.lastIndexOf('/') + 1;
        int extensionStartIndex = mImageEndPoint.lastIndexOf('.');
        String image = mImageEndPoint.substring(lastSeparatorIndex, extensionStartIndex);
        item.setImage(image);
        item.setDescription(jsonObject.getString("description"));

        items.add(item);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return items;
  }

  /**
   * The type Request values.
   */
  public static final class RequestValues implements UseCase.RequestValues {
    private final boolean mForceUpdate;

    /**
     * Instantiates a new Request values.
     *
     * @param forceUpdate the force update
     */
    public RequestValues(boolean forceUpdate) {
      mForceUpdate = forceUpdate;
    }

    /**
     * Is force update boolean.
     *
     * @return the boolean
     */
    public boolean isForceUpdate() {
      return mForceUpdate;
    }
  }

  /**
   * The type Response value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {
    private final List<Item> mItems;

    /**
     * Instantiates a new Response value.
     *
     * @param items the items
     */
    public ResponseValue(@NonNull List<Item> items) {
      mItems = checkNotNull(items, "items cannot be null!");
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<Item> getItems() {
      return mItems;
    }
  }
}