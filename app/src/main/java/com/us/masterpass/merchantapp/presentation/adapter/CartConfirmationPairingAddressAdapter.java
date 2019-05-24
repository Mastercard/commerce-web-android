package com.us.masterpass.merchantapp.presentation.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.domain.model.MasterpassPreCheckoutShippingObject;
import java.util.List;

/**
 * Created by Sebastian Farias on 11/12/17.
 */
public class CartConfirmationPairingAddressAdapter extends PagerAdapter {
    private Context context;
    private List<MasterpassPreCheckoutShippingObject> mItem;

    /**
     * Instantiates a new Cart confirmation pairing address adapter.
     *
     * @param context the context
     * @param item    the item
     */
    public CartConfirmationPairingAddressAdapter(Context context, List<MasterpassPreCheckoutShippingObject> item) {
        this.context = context;
        this.mItem = item;
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cart_confirmation_pairing_address, null);
        TextView line1 = view.findViewById(R.id.card_confirmation_shipping_line1);
        TextView line2 = view.findViewById(R.id.card_confirmation_shipping_line2);
        TextView line3 = view.findViewById(R.id.card_confirmation_shipping_line3);

        final MasterpassPreCheckoutShippingObject item = mItem.get(position);
        line1.setText(new StringBuilder().append(item.getPreLine1()).append(" ").append(item.getPreLine2()).toString());
        line2.setText(new StringBuilder().append(item.getPreCity()).append(" ").append(item.getPreSubdivision()).toString());
        line3.setText(item.getPrePostalCode());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getPreLine1(), Toast.LENGTH_SHORT).show();
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

}
