package com.mastercard.testapp.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mastercard.testapp.R;
import com.mastercard.testapp.domain.SettingsListOptions;
import com.mastercard.testapp.domain.model.Item;
import java.util.List;
import java.util.Locale;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 10-10-17.
 *
 * Adapter for items on confirmation list
 */
public class CartConfirmationAdapter extends BaseAdapter {

  private Context context;
  private List<Item> mItem;

  /**
   * Instantiates a new Cart confirmation adapter.
   *
   * @param context the context
   * @param item the item
   */
  public CartConfirmationAdapter(Context context, List<Item> item) {
    this.context = context;
    setList(item);
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

  @Override public int getCount() {
    return mItem.size();
  }

  @Override public Item getItem(int i) {
    return mItem.get(i);
  }

  @Override public long getItemId(int i) {
    return i;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;

    if (view == null) {
      // initialize the view holder
      viewHolder = new ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
      view = inflater.inflate(R.layout.cart_confirmation_cell_item, viewGroup, false);

      viewHolder.nameTV = view.findViewById(R.id.item_cell_name);
      viewHolder.salePriceTV = view.findViewById(R.id.item_cell_sale_price);
      viewHolder.totalTV = view.findViewById(R.id.item_cell_total);

      view.setClickable(false);
      view.setOnClickListener(null);
      view.setTag(viewHolder);
    } else {
      // recycle the already inflated view
      viewHolder = (ViewHolder) view.getTag();
    }

    final Item item = getItem(i);

    viewHolder.nameTV.setText(item.getName());
    viewHolder.salePriceTV.setText(
        SettingsListOptions.getCurrencySymbol(context) + String.format(Locale.US,"%.2f",
            item.getTotalPrice()));
    viewHolder.totalTV.setText(Integer.toString(item.getTotalCount()));

    return view;
  }

  private static class ViewHolder {
    /**
     * The Name tv.
     */
    TextView nameTV;
    /**
     * The Sale price tv.
     */
    TextView salePriceTV;
    /**
     * The Total tv.
     */
    TextView totalTV;
  }
}