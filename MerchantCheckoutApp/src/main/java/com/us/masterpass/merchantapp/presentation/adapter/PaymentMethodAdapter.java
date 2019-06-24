package com.us.masterpass.merchantapp.presentation.adapter;

/**
 * Adapter for PaymentMethodFragment
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.device.MerchantPaymentMethod;
import com.us.masterpass.merchantapp.domain.model.PaymentMethodOnClickInterface;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodAdapter
    extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder> {

  private List<MerchantPaymentMethod> dataSet = new ArrayList<>();
  private PaymentMethodOnClickInterface paymentMethodOnClickInterface;

  static class PaymentMethodViewHolder extends RecyclerView.ViewHolder {

    private ImageView paymentMethodLogoImageView;
    private TextView paymentMethodNameTextView;
    private TextView paymentMethodLastFourDigitsTextView;
    private ImageView deletePaymentMethodImageView;
    private ImageView editPaymentMethodImageView;
    private ImageView addPaymentMethodImageView;

    PaymentMethodViewHolder(View v) {
      super(v);
      paymentMethodLogoImageView = v.findViewById(R.id.payment_image);
      paymentMethodNameTextView = v.findViewById(R.id.payment_masterpass_text);
      paymentMethodLastFourDigitsTextView = v.findViewById(R.id.last_four_digits);
      deletePaymentMethodImageView = v.findViewById(R.id.action_delete);
      editPaymentMethodImageView = v.findViewById(R.id.action_edit);
      addPaymentMethodImageView = v.findViewById(R.id.action_add);
    }
  }

  @Override public PaymentMethodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.add_payment_method_view, parent, false);
    return new PaymentMethodViewHolder(view);
  }

  @Override public void onBindViewHolder(PaymentMethodViewHolder holder, final int position) {

    final MerchantPaymentMethod paymentMethod = dataSet.get(position);

    byte[] paymentMethodLogoByteArray = paymentMethod.getPaymentMethodLogo();
    Bitmap bitmap = BitmapFactory.decodeByteArray(paymentMethodLogoByteArray, 0,
        paymentMethodLogoByteArray.length);

    holder.paymentMethodLogoImageView.setImageBitmap(bitmap);
    holder.paymentMethodNameTextView.setText(paymentMethod.getPaymentMethodName());
    holder.paymentMethodLastFourDigitsTextView.setText(
        paymentMethod.getPaymentMethodLastFourDigits());

    holder.deletePaymentMethodImageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dataSet.remove(position);
        notifyDataSetChanged();
        paymentMethodOnClickInterface.deletePaymentMethod(paymentMethod);
      }
    });

    holder.editPaymentMethodImageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        paymentMethodOnClickInterface.editPaymentMethod(paymentMethod);
      }
    });

    holder.addPaymentMethodImageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        paymentMethodOnClickInterface.selectPaymentMethod(paymentMethod);
      }
    });
  }

  @Override public int getItemCount() {
    return dataSet != null ? dataSet.size() : 0;
  }

  /**
   * Instantiates new instance of PaymentMethodAdapter
   */
  public PaymentMethodAdapter(List<MerchantPaymentMethod> merchantPaymentMethodList,
      PaymentMethodOnClickInterface paymentMethodCLickInterface) {
    this.paymentMethodOnClickInterface = paymentMethodCLickInterface;
    setList(merchantPaymentMethodList);
  }

  /**
   * Replaces dataSet with the new dataSet and refreshing UI
   */
  public void replaceData(List<MerchantPaymentMethod> merchantPaymentMethodList) {
    setList(merchantPaymentMethodList);
    notifyDataSetChanged();
  }

  /**
   * Assigns paymentMethodList to local dataSet
   */
  private void setList(List<MerchantPaymentMethod> merchantPaymentMethodList) {
    this.dataSet = merchantPaymentMethodList;
  }
}
