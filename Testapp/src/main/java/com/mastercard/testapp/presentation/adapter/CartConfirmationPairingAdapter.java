package com.mastercard.testapp.presentation.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.mastercard.testapp.R;
import com.mastercard.testapp.domain.model.MasterpassPreCheckoutCardObject;
import java.util.List;

/**
 * Created by Sebastian Farias on 11/12/17.
 *
 * Adapter used by confirmation flow with cards options
 */
public class CartConfirmationPairingAdapter extends PagerAdapter {
  private Context context;
  private LayoutInflater layoutInflater;
  private List<MasterpassPreCheckoutCardObject> mItem;

  /**
   * Instantiates a new Cart confirmation pairing adapter.
   *
   * @param context the context
   * @param item the item
   */
  public CartConfirmationPairingAdapter(Context context,
      List<MasterpassPreCheckoutCardObject> item) {
    this.context = context;
    this.mItem = item;
  }

  @Override public int getCount() {
    return mItem.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public Object instantiateItem(ViewGroup container, final int position) {
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = layoutInflater.inflate(R.layout.cart_confirmation_pairing_card, null);
    TextView cardName = view.findViewById(R.id.card_confirmation_name_vp);
    TextView cardNumber = view.findViewById(R.id.card_confirmation_number_vp);

    final MasterpassPreCheckoutCardObject item = mItem.get(position);

    cardName.setText(item.getPreBrandName());
    cardNumber.setText(item.getPreLastFour());

    view.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(context, item.getPreBrandName(), Toast.LENGTH_SHORT).show();
      }
    });

    ViewPager vp = (ViewPager) container;
    vp.addView(view, 0);
    return view;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {

    ViewPager vp = (ViewPager) container;
    View view = (View) object;
    vp.removeView(view);
  }
}
