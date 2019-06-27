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
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.domain.SettingsListOptions;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.ItemsOnClickInterface;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 * Adapter to handle a list of {@link Item}
 */
public class ItemsAdapter extends BaseAdapter {

    private Context context;
    private List<Item> mItem;
    private ItemsOnClickInterface mItemOnClickInterface;

    /**
     * Instantiates a new Items adapter.
     *
     * @param context              the context
     * @param items                the item
     * @param itemOnClickInterface the item on click interface
     */
    public ItemsAdapter(Context context, List<Item> items,
                        ItemsOnClickInterface itemOnClickInterface) {
        this.context = context;
        setList(items);
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
            view = inflater.inflate(R.layout.items_cell_item, viewGroup, false);

            viewHolder.nameTV = view.findViewById(R.id.item_cell_name);
            viewHolder.priceTV = view.findViewById(R.id.item_cell_price);
            viewHolder.salePriceTV = view.findViewById(R.id.item_cell_sale_price);
            viewHolder.descriptionTV = view.findViewById(R.id.item_cell_description);
            viewHolder.imageIV = view.findViewById(R.id.item_cell_image);
            viewHolder.addIV = view.findViewById(R.id.item_cell_add);

            view.setClickable(false);
            view.setOnClickListener(null);
            view.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) view.getTag();
        }

        final Item item = getItem(i);

        viewHolder.nameTV.setText(item.getName());
        viewHolder.priceTV.setText(SettingsListOptions.getCurrencySymbol(context) + Double.toString(item.getPrice()));
        viewHolder.salePriceTV.setText(SettingsListOptions.getCurrencySymbol(context) + Double.toString(item.getSalePrice()));
        viewHolder.descriptionTV.setText(item.getDescription());

        int image = context.getResources()
            .getIdentifier(item.getImage(), "drawable", context.getPackageName());
        Glide.with(context).load(image).into(viewHolder.imageIV);

        viewHolder.addIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemOnClickInterface.onAddItemsCart(item);
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
         * The Image iv.
         */
        ImageView imageIV;
        /**
         * The Add iv.
         */
        ImageView addIV;
    }
}