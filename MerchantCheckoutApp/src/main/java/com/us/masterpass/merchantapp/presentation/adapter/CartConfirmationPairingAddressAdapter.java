package com.us.masterpass.merchantapp.presentation.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.domain.model.MasterpassPreCheckoutShippingObject;
import java.util.List;

/**
 * Created by Sebastian Farias on 11/12/17.
 * <p>
 * Adapter used by confirmation flow with shipping options
 */
public class CartConfirmationPairingAddressAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
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
        if(mItem != null) {
            return mItem.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cart_confirmation_pairing_address, null);
        TextView line1 = view.findViewById(R.id.card_confirmation_shipping_line1);
        TextView line2 = view.findViewById(R.id.card_confirmation_shipping_line2);
        TextView line3 = view.findViewById(R.id.card_confirmation_shipping_line3);

        final MasterpassPreCheckoutShippingObject item = mItem.get(position);
        line1.setText(new StringBuilder().append(item.getPreLine1()).append(" ").append(item.getPreLine2()).toString());
        line2.setText(new StringBuilder().append(item.getPreLine1()));
        line3.setText(new StringBuilder().append(item
            .getPreSubdivision()).append(" ").append(item.getPrePostalCode()).toString());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, item.getPreLine1(), Toast.LENGTH_SHORT).show();
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
