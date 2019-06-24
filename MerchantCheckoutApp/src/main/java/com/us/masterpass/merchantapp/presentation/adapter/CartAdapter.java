package com.us.masterpass.merchantapp.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.ItemsOnClickInterface;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 *
 * Adapter for items on cart
 */
public class CartAdapter extends BaseAdapter{

    private Context context;
    private List<Item> mItem;
    private ItemsOnClickInterface mItemOnClickInterface;

    /**
     * Instantiates a new Cart adapter.
     *
     * @param context              the context
     * @param item                 the item
     * @param itemOnClickInterface the item on click interface
     */
    public CartAdapter(Context context, List<Item> item, ItemsOnClickInterface itemOnClickInterface) {
        this.context = context;
        setList(item);
        mItemOnClickInterface = itemOnClickInterface;
    }

    /**
     * Replace data.
     *
     * @param item the item
     */
    public void replaceData(List<Item> item) {
        setList(item);
        notifyDataSetChanged();
    }

    private void setList(List<Item> item) {
        mItem = checkNotNull(item);
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public Item getItem(int i) {
        return mItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            // initialize the view holder
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.cart_cell_item, viewGroup, false);

            viewHolder.nameTV = view.findViewById(R.id.item_cell_name);
            viewHolder.salePriceTV = view.findViewById(R.id.item_cell_sale_price);
            viewHolder.imageIV = view.findViewById(R.id.item_cell_image);
            viewHolder.totalTV = view.findViewById(R.id.item_cell_total);
            viewHolder.addIV = view.findViewById(R.id.item_cell_add);
            viewHolder.removeIV = view.findViewById(R.id.item_cell_remove);
            viewHolder.removeAllTV = view.findViewById(R.id.item_cell_remove_all);

            view.setClickable(false);
            view.setOnClickListener(null);
            view.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) view.getTag();
        }

        final Item item = getItem(i);

        viewHolder.nameTV.setText(item.getName());
        viewHolder.salePriceTV.setText(SettingsListOptions.getCurrencySymbol(context) + String.format("%.2f", item.getTotalPrice()));
        viewHolder.totalTV.setText(Integer.toString(item.getTotalCount()));

        RequestOptions requestOptions = new RequestOptions()
                .dontAnimate()
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(context.getResources().getIdentifier(item.getImage(), "drawable", context.getPackageName()))
                .apply(requestOptions)
                .into(viewHolder.imageIV);

        viewHolder.addIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemOnClickInterface.onAddItemsCart(item);
            }
        });

        viewHolder.removeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemOnClickInterface.onRemoveItemsCart(item);
            }
        });

        viewHolder.removeAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemOnClickInterface.onRemoveAllItems(item);
            }
        });

        return view;
    }

    private static class ViewHolder{
        /**
         * The Name tv.
         */
        TextView nameTV;
        /**
         * The Price tv.
         */
        TextView priceTV;
        /**
         * The Sale price tv.
         */
        TextView salePriceTV;
        /**
         * The Description tv.
         */
        TextView descriptionTV;
        /**
         * The Total tv.
         */
        TextView totalTV;
        /**
         * The Image iv.
         */
        ImageView imageIV;
        /**
         * The Add iv.
         */
        ImageView addIV;
        /**
         * The Remove iv.
         */
        ImageView removeIV;
        /**
         * The Remove all tv.
         */
        ImageView removeAllTV;
    }
}