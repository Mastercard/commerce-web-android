package com.mastercard.testapp.domain.usecase.items;

import androidx.annotation.NonNull;
import com.mastercard.testapp.data.ItemDataSource;
import com.mastercard.testapp.data.ItemRepository;
import com.mastercard.testapp.data.device.CartLocalObject;
import com.mastercard.testapp.domain.Constants;
import com.mastercard.testapp.domain.ItemsOnCartTransform;
import com.mastercard.testapp.domain.model.Item;
import com.mastercard.testapp.domain.usecase.base.UseCase;
import java.util.List;
import java.util.Locale;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Remove all elements on local model for shopping cart
 */
public class RemoveAllItemUseCase
    extends UseCase<RemoveAllItemUseCase.RequestValues, RemoveAllItemUseCase.ResponseValue> {

  private final ItemRepository mItemsRepository;

  /**
   * Instantiates a new Remove all item use case.
   *
   * @param tasksRepository the tasks repository
   */
  public RemoveAllItemUseCase(@NonNull ItemRepository tasksRepository) {
    mItemsRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
  }

  @Override protected void executeUseCase(final RequestValues values) {
    Item item = values.getAddItem();
    mItemsRepository.removeAllItem(item, new ItemDataSource.GetItemOnCartCallback() {
      @Override public void onItemOnCart(String totalItem, List<Item> newItemOnCart,
          List<CartLocalObject> itemsOnCart) {
        ItemsOnCartTransform itemsOnCartTransform = new ItemsOnCartTransform(itemsOnCart);
        List<Item> newItemOnCartFinal = itemsOnCartTransform.newItemOnCartFinal;
        double totalSalePrice = itemsOnCartTransform.newTotalSalePrice;

        String subtotalPriceText = String.format(Locale.US,"%.2f", totalSalePrice);
        totalSalePrice = totalSalePrice + Constants.TAX_VALUE;
        String totalSalePriceText = String.format(Locale.US,"%.2f", totalSalePrice);
        String taxText = Double.toString(Constants.TAX_VALUE);
        ResponseValue responseValue =
            new ResponseValue(totalItem, newItemOnCartFinal, itemsOnCart, "$" + totalSalePriceText,
                "$" + taxText, "$" + subtotalPriceText);
        getUseCaseCallback().onSuccess(responseValue);
      }

      @Override public void onDataNotAvailable() {
        getUseCaseCallback().onError();
      }
    });
  }

  /**
   * The type Request values.
   */
  public static final class RequestValues implements UseCase.RequestValues {
    private final Item mAddItem;

    /**
     * Instantiates a new Request values.
     *
     * @param addItem the add item
     */
    public RequestValues(@NonNull Item addItem) {
      mAddItem = checkNotNull(addItem, "completedTask cannot be null!");
    }

    /**
     * Gets add item.
     *
     * @return the add item
     */
    public Item getAddItem() {
      return mAddItem;
    }
  }

  /**
   * The type Response value.
   */
  public static final class ResponseValue implements UseCase.ResponseValue {
    private final String addItemCount;
    private final List<Item> newItemOnCart;
    private final List<CartLocalObject> itemsOnCart;
    private final String totalPrice;
    private final String tax;
    private final String subTotalPrice;

    /**
     * Instantiates a new Response value.
     *
     * @param itemCount the item count
     * @param newItemOnCart the new item on cart
     * @param itemsOnCart the items on cart
     * @param totalPrice the total price
     * @param tax the tax
     * @param subTotalPrice the sub total price
     */
    public ResponseValue(@NonNull String itemCount, List<Item> newItemOnCart,
        List<CartLocalObject> itemsOnCart, String totalPrice, String tax, String subTotalPrice) {
      this.addItemCount = checkNotNull(itemCount, "Total Items of cache cart");
      this.newItemOnCart = newItemOnCart;
      this.itemsOnCart = itemsOnCart;
      this.totalPrice = totalPrice;
      this.tax = tax;
      this.subTotalPrice = subTotalPrice;
    }

    /**
     * Gets add item count.
     *
     * @return the add item count
     */
    public String getAddItemCount() {
      return addItemCount;
    }

    /**
     * Gets items on cart.
     *
     * @return the items on cart
     */
    public List<CartLocalObject> getItemsOnCart() {
      return itemsOnCart;
    }

    /**
     * Gets new item on cart.
     *
     * @return the new item on cart
     */
    public List<Item> getNewItemOnCart() {
      return newItemOnCart;
    }

    /**
     * Gets total price.
     *
     * @return the total price
     */
    public String getTotalPrice() {
      return totalPrice;
    }

    /**
     * Gets tax.
     *
     * @return the tax
     */
    public String getTax() {
      return tax;
    }

    /**
     * Gets sub total price.
     *
     * @return the sub total price
     */
    public String getSubTotalPrice() {
      return subTotalPrice;
    }
  }
}