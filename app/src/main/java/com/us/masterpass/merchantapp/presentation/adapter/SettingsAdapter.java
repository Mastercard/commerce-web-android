package com.us.masterpass.merchantapp.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.domain.model.SettingsOnClickInterface;
import com.us.masterpass.merchantapp.domain.model.SettingsVO;
import com.us.masterpass.merchantapp.presentation.SettingsConstants;
import com.us.masterpass.merchantapp.presentation.fragment.SettingsFragment;
import java.util.List;
import java.util.StringTokenizer;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 */
public class SettingsAdapter extends BaseAdapter {

  private List<SettingsVO> mItem;
  private SettingsOnClickInterface mSettingsOnCLickInterface;

  /**
   * Instantiates a new Settings adapter.
   *
   * @param settings the settings
   * @param settingsOnClickInterface the settings on click interface
   */
  public SettingsAdapter(List<SettingsVO> settings,
      SettingsOnClickInterface settingsOnClickInterface) {
    mItem = settings;
    setList(settings);
    mSettingsOnCLickInterface = settingsOnClickInterface;
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

  @Override public int getCount() {
    return mItem.size();
  }

  @Override public Object getItem(int position) {
    return mItem.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    final ViewHolder viewHolder;
    String type;
    final SettingsVO item = mItem.get(i);
    type = item.getType();

    if (view == null) {
      viewHolder = new ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
      if (type.equalsIgnoreCase(SettingsConstants.TYPE_CARD)) {
        view = inflater.inflate(R.layout.settings_cell_item_cards, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            mSettingsOnCLickInterface.onClickSettingItem(item);
          }
        });
        viewHolder.cardAmerican = view.findViewById(R.id.settings_card_american);
        viewHolder.cardDiscove = view.findViewById(R.id.settings_card_discove);
        viewHolder.cardJcb = view.findViewById(R.id.settings_card_jcb);
        viewHolder.cardMaster = view.findViewById(R.id.settings_card_mastercard);
        viewHolder.cardUnion = view.findViewById(R.id.settings_card_union);
        viewHolder.cardVisa = view.findViewById(R.id.settings_card_visa);
      } else if (type.equalsIgnoreCase(SettingsConstants.TYPE_ARROW)) {
        view = inflater.inflate(R.layout.settings_cell_item_arrow, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            mSettingsOnCLickInterface.onClickSettingItem(item);
          }
        });
        viewHolder.descriptionTV = view.findViewById(R.id.settings_cell_description);
      } else if (type.equalsIgnoreCase(SettingsConstants.TYPE_SWITCH)) {
        view = inflater.inflate(R.layout.settings_cell_item_switch, viewGroup, false);
        viewHolder.enableSW = view.findViewById(R.id.settings_switch);
      }

      viewHolder.nameTV = view.findViewById(R.id.settings_cell_name);

      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }

    viewHolder.nameTV.setText(item.getName());
    if (type.equalsIgnoreCase(SettingsConstants.TYPE_SWITCH)) {
      if (item.getName()
          .equalsIgnoreCase(SettingsConstants.ITEM_SUPRESS)) {
        if (item.isSelected()) {
          viewHolder.enableSW.setChecked(true);
        }
        viewHolder.enableSW.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
              @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelected(isChecked);
                mSettingsOnCLickInterface.onClickSettingItem(item);
              }
            });
      }
    } else if (type.equalsIgnoreCase(SettingsConstants.TYPE_ARROW)) {
      if (item.getDescription() != null) {
        viewHolder.descriptionTV.setVisibility(View.VISIBLE);
        viewHolder.descriptionTV.setText(item.getDescription());
      } else {
        viewHolder.descriptionTV.setVisibility(View.GONE);
      }
    } else if (type.equalsIgnoreCase(SettingsConstants.TYPE_CARD)) {
      viewHolder.cardAmerican.setVisibility(View.GONE);
      viewHolder.cardDiscove.setVisibility(View.GONE);
      viewHolder.cardJcb.setVisibility(View.GONE);
      viewHolder.cardMaster.setVisibility(View.GONE);
      viewHolder.cardUnion.setVisibility(View.GONE);
      viewHolder.cardVisa.setVisibility(View.GONE);

      StringTokenizer st = new StringTokenizer(item.getDescription(), ",");
      while (st.hasMoreTokens()) {
        switch (st.nextToken()) {
          case SettingsConstants.CARD_AMERICAN:
            viewHolder.cardAmerican.setVisibility(View.VISIBLE);
            break;
          case SettingsConstants.CARD_DISCOVER:
            viewHolder.cardDiscove.setVisibility(View.VISIBLE);
            break;
          case SettingsConstants.CARD_JCB:
            viewHolder.cardJcb.setVisibility(View.VISIBLE);
            break;
          case SettingsConstants.CARD_MASTERCARD:
            viewHolder.cardMaster.setVisibility(View.VISIBLE);
            break;
          case SettingsConstants.CARD_UNIONPAY:
            viewHolder.cardUnion.setVisibility(View.VISIBLE);
            break;
          case SettingsConstants.CARD_VISA:
            viewHolder.cardVisa.setVisibility(View.VISIBLE);
            break;
          default:
            break;
        }
      }
    }

    return view;
  }

  private static class ViewHolder {
    /**
     * The Name tv.
     */
    TextView nameTV;
    /**
     * The Description tv.
     */
    TextView descriptionTV;
    /**
     * The Enable sw.
     */
    Switch enableSW;
    /**
     * The Card american.
     */
    ImageView cardAmerican;
    /**
     * The Card discove.
     */
    ImageView cardDiscove;
    /**
     * The Card jcb.
     */
    ImageView cardJcb;
    /**
     * The Card master.
     */
    ImageView cardMaster;
    /**
     * The Card union.
     */
    ImageView cardUnion;
    /**
     * The Card visa.
     */
    ImageView cardVisa;
  }
}
