package com.mastercard.testapp.presentation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.mastercard.mp.checkout.MasterpassMerchant;
import com.mastercard.testapp.R;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkInterface;
import com.mastercard.testapp.domain.model.SettingsOnClickInterface;
import com.mastercard.testapp.domain.model.SettingsVO;
import com.mastercard.testapp.domain.usecase.masterpass.InitializeSdkUseCase;
import com.mastercard.testapp.presentation.SettingsConstants;
import com.mastercard.testapp.presentation.fragment.SettingsFragment;
import java.util.List;
import java.util.StringTokenizer;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Adapter used on settings list
 */
public class SettingsAdapter extends BaseAdapter {

  private static final int VIEW_COUNT = 3;
  private static final int VIEW_CARD = 0;
  private static final int VIEW_ARROW = 1;
  private static final int VIEW_SWITCH = 2;
  private Context context;
  private List<SettingsVO> mItem;
  private SettingsOnClickInterface mSettingsOnCLickInterface;
  private boolean isPaymentEnabled;
  private boolean isExpressCheckout;

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
    context = viewGroup.getContext();
    final SettingsVO item = mItem.get(i);
    final int viewType = getItemViewType(i);
    if (item.getName().equalsIgnoreCase(SettingsConstants.ITEM_MASTERPASS)) {
      isPaymentEnabled = item.isSelected();
    } else if (item.getName().equalsIgnoreCase(SettingsConstants.ITEM_EXPRESS)) {
      isExpressCheckout = item.isSelected();
    }
    viewHolder = new ViewHolder();
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    if (VIEW_CARD == viewType) {
      view = inflater.inflate(R.layout.settings_cell_item_cards, viewGroup, false);
      viewHolder.nameTV = view.findViewById(R.id.settings_cell_name);

      view.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mSettingsOnCLickInterface.onClickSettingItem(item);
        }
      });
      viewHolder.cardAmerican = view.findViewById(R.id.settings_card_american);
      viewHolder.cardDiscove = view.findViewById(R.id.settings_card_discove);
      viewHolder.cardJcb = view.findViewById(R.id.settings_card_jcb);
      viewHolder.cardMaster = view.findViewById(R.id.settings_card_mastercard);
      viewHolder.cardMestro = view.findViewById(R.id.settings_card_mestro);
      viewHolder.cardDiners = view.findViewById(R.id.settings_card_diners);
      viewHolder.cardVisa = view.findViewById(R.id.settings_card_visa);
    } else if (VIEW_ARROW == viewType) {
      view = inflater.inflate(R.layout.settings_cell_item_arrow, viewGroup, false);
      viewHolder.rootView = view.findViewById(R.id.root);
      viewHolder.nameTV = view.findViewById(R.id.settings_cell_name);
      viewHolder.descriptionTV = view.findViewById(R.id.settings_cell_description);
    } else if (VIEW_SWITCH == viewType) {
      view = inflater.inflate(R.layout.settings_cell_item_switch, viewGroup, false);
      viewHolder.nameTV = view.findViewById(R.id.settings_cell_name);
      viewHolder.enableSW = view.findViewById(R.id.settings_switch);
      if (item.getName().equalsIgnoreCase(SettingsConstants.ITEM_EXPRESS) || item.getName()
          .equals(SettingsConstants.ENABLE_WEB_CHECKOUT)) {
        if (!SettingsFragment.isLogged && !item.getName()
            .equals(SettingsConstants.ENABLE_WEB_CHECKOUT)) {
          viewHolder.enableSW.setOnCheckedChangeListener(
              new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  mSettingsOnCLickInterface.loadLogin();
                  viewHolder.enableSW.setChecked(false);
                }
              });
        } else {
          if (!isExpressCheckout && item.getName().equals(SettingsConstants.ENABLE_WEB_CHECKOUT)) {
            viewHolder.enableSW.setEnabled(false);
            viewHolder.enableSW.setClickable(false);
          } else {
            viewHolder.enableSW.setChecked(item.isSelected());
          }
        }
      }
    } else {
      view = null;
    }
    view.setTag(viewHolder);

    viewHolder.nameTV.setText(item.getName());

    if (!isPaymentEnabled && item.getName().equals(SettingsConstants.ITEM_PAYMENT_METHOD)) {
      viewHolder.nameTV.setTextColor(Color.LTGRAY);
    } else if (!isExpressCheckout && ((item.getName().equals(SettingsConstants.PAIRING_ONLY))
        || item.getName().equals(SettingsConstants.ENABLE_WEB_CHECKOUT))) {
      viewHolder.nameTV.setTextColor(Color.LTGRAY);
    }

    if (VIEW_SWITCH == viewType) {
      if (item.getName().equalsIgnoreCase(SettingsConstants.ITEM_ENABLE_PAYMENT_METHOD)) {
        if (!isPaymentEnabled) {
          viewHolder.enableSW.setEnabled(false);
          viewHolder.enableSW.setSelected(false);
        } else {
          viewHolder.enableSW.setEnabled(true);
          viewHolder.enableSW.setChecked(item.isSelected());
          viewHolder.enableSW.setOnCheckedChangeListener(
              new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  item.setSelected(isChecked);
                  mSettingsOnCLickInterface.onClickSettingItem(item);
                }
              });
        }
      }
      if (SettingsFragment.isLogged || item.getName()
          .equalsIgnoreCase(SettingsConstants.ITEM_SUPRESS) || item.getName()
          .equalsIgnoreCase(SettingsConstants.ITEM_MASTERPASS) || item.getName()
          .equalsIgnoreCase(SettingsConstants.SUPRESS_3DS)) {
        viewHolder.enableSW.setChecked(item.isSelected());
        viewHolder.enableSW.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
              @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelected(isChecked);
                mSettingsOnCLickInterface.onClickSettingItem(item);
                if (item.getName().equalsIgnoreCase(SettingsConstants.ITEM_MASTERPASS)) {
                  isPaymentEnabled = item.isSelected();
                }
              }
            });
      }
    } else if (VIEW_ARROW == viewType) {
      viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          SettingsVO data = (SettingsVO) v.getTag();
          if (null != data) {
            if (data.getName().equalsIgnoreCase(SettingsConstants.ITEM_PAYMENT_METHOD)) {
              if (isPaymentEnabled) {
                viewHolder.nameTV.setTextColor(Color.BLACK);
                mSettingsOnCLickInterface.onClickSettingItem(data);
              }
            } else if (data.getName().equalsIgnoreCase(SettingsConstants.PAIRING_ONLY)) {
              if (isExpressCheckout) {
                viewHolder.nameTV.setTextColor(Color.BLACK);
                if (!MasterpassSdkCoordinator.isSDKInitialized()) {
                  mSettingsOnCLickInterface.showProgress();
                  initializeMasterpassMerchant(context);
                } else {
                  pairingCall();
                }
              }
            } else {
              mSettingsOnCLickInterface.onClickSettingItem(data);
            }
          }
        }
      });
      viewHolder.rootView.setTag(item);
      if (item.getDescription() != null) {
        viewHolder.descriptionTV.setVisibility(View.VISIBLE);
        viewHolder.descriptionTV.setText(item.getDescription());
      } else {
        viewHolder.descriptionTV.setVisibility(View.GONE);
      }
    } else if (VIEW_CARD == viewType) {
      viewHolder.cardAmerican.setVisibility(View.GONE);
      viewHolder.cardDiscove.setVisibility(View.GONE);
      viewHolder.cardJcb.setVisibility(View.GONE);
      viewHolder.cardMaster.setVisibility(View.GONE);
      viewHolder.cardMestro.setVisibility(View.GONE);
      viewHolder.cardDiners.setVisibility(View.GONE);
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
          case SettingsConstants.CARD_MESTRO:
            viewHolder.cardMestro.setVisibility(View.VISIBLE);
            break;
          case SettingsConstants.CARD_DINERS:
            viewHolder.cardDiners.setVisibility(View.VISIBLE);
            break;
          case SettingsConstants.CARD_VISA:
            viewHolder.cardVisa.setVisibility(View.VISIBLE);
            break;
        }
      }
    }

    return view;
  }

  @Override public int getItemViewType(int position) {
    switch (mItem.get(position).getType()) {
      case SettingsConstants.TYPE_CARD:
        return VIEW_CARD;
      case SettingsConstants.TYPE_ARROW:
        return VIEW_ARROW;
      case SettingsConstants.TYPE_SWITCH:
        return VIEW_SWITCH;
    }

    return super.getItemViewType(position);
  }

  @Override public int getViewTypeCount() {
    return VIEW_COUNT;
  }

  public void initializeMasterpassMerchant(final Context context) {
    InitializeSdkUseCase.initializeSdk(context, new MasterpassSdkInterface.GetFromMasterpassSdk() {
      @Override public void sdkResponseSuccess() {
        mSettingsOnCLickInterface.hideProgress();
        pairingCall();
      }

      @Override public void sdkResponseError(String error) {
        mSettingsOnCLickInterface.hideProgress();
        mSettingsOnCLickInterface.showError();
      }
    });
  }

  private void pairingCall() {
    try {
      MasterpassMerchant.pairing(false, MasterpassSdkCoordinator.getInstance());
    } catch (IllegalStateException e) {
      Toast.makeText(context, R.string.sdk_reinitialize, Toast.LENGTH_LONG).show();
    }
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
     * The Card Mestro.
     */
    ImageView cardMestro;
    /**
     * The Card Diners.
     */
    ImageView cardDiners;
    /**
     * The Card visa.
     */
    ImageView cardVisa;

    /**
     * Root View
     */
    View rootView;
  }
}
