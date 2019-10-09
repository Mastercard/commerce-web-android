package com.mastercard.testapp.presentation.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.mastercard.testapp.R;

/**
 * Provides custom AlertDialog implementation based on dialogType
 */
final class BaseAlertDialog extends AlertDialog implements View.OnClickListener {

  static final String MEX_ERROR_DIALOG = "mexErrorDialog";
  private final String message;
  private final SpannableString spannableString;
  private final String title;
  private final String dialogType;
  private DialogInteractionListener dialogInteractionListener;
  private String rightButtonText;
  private String leftButtonText;

  private BaseAlertDialog(Builder builder) {
    super(builder.context);
    this.message = builder.dialogMessage;
    this.spannableString = builder.spannableString;
    this.title = builder.dialogTitle;
    this.leftButtonText = builder.leftButtonText;
    this.rightButtonText = builder.rightButtonText;
    this.dialogType = builder.dialogType;
    this.dialogInteractionListener = builder.dialogListener;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    switch (dialogType) {
      case MEX_ERROR_DIALOG:
        View mexDialogView = LayoutInflater.from(getContext())
            .inflate(R.layout.fragment_mex_error_dialog, null, false);
        setContentView(mexDialogView);
        setDialog(mexDialogView);
        ImageButton closeBtn = (ImageButton) mexDialogView.findViewById(R.id.closeBtn);
        Button okBtn = (Button) mexDialogView.findViewById(R.id.button_done);
        closeBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        break;
    }
  }

  private void setLeftButtonTextAndAction(View dialogView) {
    setButtonAction(dialogView, R.id.button_cancel, leftButtonText);
  }

  private void setRightButtonTextAndAction(View dialogView) {
    setButtonAction(dialogView, R.id.button_done, rightButtonText);
  }

  private void setButtonAction(View dialogView, int buttonId, String buttonText) {
    if (!TextUtils.isEmpty(buttonText)) {
      Button button = (Button) dialogView.findViewById(buttonId);
      button.setVisibility(View.VISIBLE);
      button.setOnClickListener(this);
      button.setText(buttonText);
    }
  }

  private void setDialog(View dialogView) {
    TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
    TextView dialogMessage = (TextView) dialogView.findViewById(R.id.dialog_message);

    if (spannableString == null) {
      if (TextUtils.isEmpty(message)) {
        dialogMessage.setVisibility(dialogView.GONE);
      } else {
        dialogMessage.setText(message);
      }
    } else {
      dialogMessage.setText(spannableString);
      dialogMessage.setMovementMethod(LinkMovementMethod.getInstance());
    }

    dialogTitle.setText(title);
    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    setCanceledOnTouchOutside(false);
  }

  @Override public void onClick(View view) {
    if (view.getId() == R.id.button_done || view.getId() == R.id.closeBtn) {
      dialogInteractionListener.onRightButtonClicked();
      dismiss();
    } else if (view.getId() == R.id.button_cancel) {
      dialogInteractionListener.onLeftButtonClick();
      dismiss();
    }
  }

  /**
   * Interface to notify {@link BaseAlertDialog} actions.
   */
  interface DialogInteractionListener {
    void onRightButtonClicked();

    void onLeftButtonClick();
  }

  static class Builder {
    private Context context;
    private String dialogTitle;
    private String dialogMessage;
    private SpannableString spannableString;
    private String leftButtonText;
    private String rightButtonText;
    private String dialogType;
    private DialogInteractionListener dialogListener;

    Builder(Context context, String dialogType) {
      this.context = context;
      this.dialogType = dialogType;
    }

    public Builder setDialogTitle(String title) {
      this.dialogTitle = title;
      return this;
    }

    public Builder setDialogMessage(String message) {
      this.dialogMessage = message;
      return this;
    }

    public Builder setDialogMessage(SpannableString message) {
      this.spannableString = message;
      return this;
    }

    public Builder setLeftButtonText(String positiveButtonText) {
      this.leftButtonText = positiveButtonText;
      return this;
    }

    public Builder setRightButtonText(String negativeButtonText) {
      this.rightButtonText = negativeButtonText;
      return this;
    }

    public Builder setDialogListener(DialogInteractionListener dialogListener) {
      this.dialogListener = dialogListener;
      return this;
    }

    BaseAlertDialog build() {
      return new BaseAlertDialog(this);
    }
  }
}
