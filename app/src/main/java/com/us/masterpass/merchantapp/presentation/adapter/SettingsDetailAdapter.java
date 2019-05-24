package com.us.masterpass.merchantapp.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.presentation.SettingsConstants;
import java.util.List;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 */
public class SettingsDetailAdapter extends BaseAdapter {

    private List<SettingsVO> mItem;
    private Context mContext;

    /**
     * Instantiates a new Settings detail adapter.
     *
     * @param context  the context
     * @param settings the settings
     */
    public SettingsDetailAdapter(Context context, List<SettingsVO> settings) {
        mContext = context;
        mItem = settings;
        setList(settings);
    }

    /**
     * Replace data.
     *
     * @param settings the settings
     */
    public void replaceData(List<SettingsVO> settings) {
        setList(settings);
        notifyDataSetChanged();
    }

    private void setList(List<SettingsVO> settings) {
        mItem = checkNotNull(settings);
    }

    /**
     * Gets list.
     *
     * @return the list
     */
    public List<SettingsVO> getList() {
        return mItem;
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        final SettingsVO item = mItem.get(i);
        String type = item.getType();

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.settings_detail_cell_item, viewGroup, false);
            view.setTag(viewHolder);
            viewHolder.nameTV = view.findViewById(R.id.settings_cell_name);
            viewHolder.selectedCB = view.findViewById(R.id.settings_check);
            if (type.equalsIgnoreCase(SettingsConstants.ITEM_CARDS)){
                viewHolder.cardIV = view.findViewById(R.id.settings_cell_image);
            }
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.nameTV.setText(item.getName());
        if (type.equalsIgnoreCase(SettingsConstants.ITEM_CARDS)){
            viewHolder.cardIV.setVisibility(View.VISIBLE);

            String nameImage = item.getImageName();
            int image = mContext.getResources().getIdentifier(nameImage, "drawable", mContext.getPackageName());
            viewHolder.cardIV.setImageResource(image);
        }
        if (type.equalsIgnoreCase(SettingsConstants.ITEM_LANGUAGE) ||
                type.equalsIgnoreCase(SettingsConstants.ITEM_CURRENCY)) {
            viewHolder.selectedCB.setChecked(item.isSelected());
            viewHolder.selectedCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!item.isSelected()) {
                        if (isChecked) {
                            for (int x = 0; x < mItem.size(); x++) {
                                mItem.get(x).setSelected(false);
                            }
                        }
                        item.setSelected(isChecked);
                    }
                    notifyDataSetChanged();
                }
            });
        } else {
            viewHolder.selectedCB.setChecked(item.isSelected());
            viewHolder.selectedCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setSelected(isChecked);
                }
            });
        }
        return view;
    }

    private static class ViewHolder {
        /**
         * The Name tv.
         */
        TextView nameTV;
        /**
         * The Selected cb.
         */
        CheckBox selectedCB;
        /**
         * The Card iv.
         */
        ImageView cardIV;
    }

}
