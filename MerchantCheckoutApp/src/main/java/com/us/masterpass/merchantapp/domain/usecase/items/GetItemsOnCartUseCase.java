package com.us.masterpass.merchantapp.domain.usecase.items;

import android.content.Context;
import android.support.annotation.NonNull;
import com.us.masterpass.merchantapp.data.ItemDataSource;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.domain.Constants;
import com.us.masterpass.merchantapp.domain.ItemsOnCartTransform;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCase;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * Get items on shopping cart, items saved on device
 */
public class GetItemsOnCartUseCase extends UseCase<GetItemsOnCartUseCase.RequestValues, GetItemsOnCartUseCase.ResponseValue> {

    private final ItemRepository mItemsRepository;
    private final Context mContext;

    /**
     * Instantiates a new Get items on cart use case.
     *
     * @param itemsRepository the items repository
     */
    public GetItemsOnCartUseCase(@NonNull ItemRepository itemsRepository, Context context) {
        mItemsRepository = checkNotNull(itemsRepository, "Item must exist");
        this.mContext = context;
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        mItemsRepository.getItemsOnCart(new ItemDataSource.GetItemOnCartShippingOptionCallback() {
            @Override
            public void onItemOnCart(String totalItem,
                                     List<Item> newItemOnCart,
                                     List<CartLocalObject> itemsOnCart,
                                     boolean suppressShipping) {
                ItemsOnCartTransform itemsOnCartTransform = new ItemsOnCartTransform(itemsOnCart);
                List<Item> newItemOnCartFinal = itemsOnCartTransform.newItemOnCartFinal;
                double totalSalePrice = itemsOnCartTransform.newTotalSalePrice;

                String subtotalPriceText = String.format("%.2f", totalSalePrice);
                totalSalePrice = totalSalePrice + Constants.TAX_VALUE;
                String totalSalePriceText = String.format("%.2f", totalSalePrice);
                String taxText = Double.toString(Constants.TAX_VALUE);
                ResponseValue responseValue = new ResponseValue(
                        totalItem,
                        newItemOnCartFinal,
                        itemsOnCart,
                        SettingsListOptions.getCurrencySymbol(mContext) + totalSalePriceText,
                        SettingsListOptions.getCurrencySymbol(mContext) + taxText,
                        SettingsListOptions.getCurrencySymbol(mContext) + subtotalPriceText,
                        suppressShipping
                );
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });

    }

    /**
     * The type Request values.
     */
    public static final class RequestValues implements UseCase.RequestValues {
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
        private final boolean suppressShipping;

        /**
         * Instantiates a new Response value.
         *
         * @param itemCount        the item count
         * @param newItemOnCart    the new item on cart
         * @param itemsOnCart      the items on cart
         * @param totalPrice       the total price
         * @param tax              the tax
         * @param subTotalPrice    the sub total price
         * @param suppressShipping the suppress shipping
         */
        public ResponseValue(@NonNull String itemCount,
                             List<Item> newItemOnCart,
                             List<CartLocalObject> itemsOnCart,
                             String totalPrice,
                             String tax,
                             String subTotalPrice,
                             boolean suppressShipping) {
            this.addItemCount = checkNotNull(itemCount, "Total Items of cache cart");
            this.newItemOnCart = newItemOnCart;
            this.itemsOnCart = itemsOnCart;
            this.totalPrice = totalPrice;
            this.tax = tax;
            this.subTotalPrice = subTotalPrice;
            this.suppressShipping = suppressShipping;
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

        /**
         * Is suppress shipping boolean.
         *
         * @return the boolean
         */
        public boolean isSuppressShipping() {
            return suppressShipping;
        }
    }
}