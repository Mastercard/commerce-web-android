package com.us.masterpass.merchantapp.presentation.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.device.CartLocalObject;
import com.us.masterpass.merchantapp.data.device.CartLocalStorage;
import com.us.masterpass.merchantapp.domain.model.Item;
import com.us.masterpass.merchantapp.domain.model.ItemsOnClickInterface;
import com.us.masterpass.merchantapp.presentation.AnimateUtils;
import com.us.masterpass.merchantapp.presentation.activity.CartActivity;
import com.us.masterpass.merchantapp.presentation.activity.LoginActivity;
import com.us.masterpass.merchantapp.presentation.activity.MyAccountActivity;
import com.us.masterpass.merchantapp.presentation.activity.SettingsActivity;
import com.us.masterpass.merchantapp.presentation.adapter.ItemsAdapter;
import com.us.masterpass.merchantapp.presentation.presenter.ItemsPresenter;
import com.us.masterpass.merchantapp.presentation.presenter.base.ItemsPresenterInterface;
import com.us.masterpass.merchantapp.presentation.view.ItemsListView;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 07-10-17.
 *
 * Screen that shows the items, first screen
 */
public class ItemsFragment extends Fragment implements ItemsListView {

    private ItemsAdapter mAdapter;
    private ItemsPresenterInterface mPresenter;
    private TextView badgeCart;
    private ImageView imageCart;
    private AlertDialog.Builder alert;
    /**
     * The Point on screen.
     */
    Point pointOnScreen;

    /**
     * Listener for clicks on tasks in the ListView.
     */
    ItemsOnClickInterface mItemsOnClickInterface = new ItemsOnClickInterface() {
        @Override
        public void onAddItemsCart(Item addedItem) {
            mPresenter.addItem(addedItem);
            animateBadge();
        }

        @Override
        public void onRemoveItemsCart(Item removedItem) {
            // No use for this yet.
        }

        @Override
        public void onRemoveAllItems(Item removeAllItems) {
            // No use for this yet.
        }
    };

    private View.OnClickListener loadPopupMenu = new PopupMenuClickListener();

    /**
     * Apply opacity to component
     */
    private View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setAlpha(.5f);
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                v.setAlpha(1f);
            }
            return false;
        }
    };

    /**
     * Instantiates a new Items fragment.
     */
    public ItemsFragment() {
        // No use for this yet.
    }

    /**
     * New instance items fragment.
     *
     * @return the items fragment
     */
    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ItemsAdapter(getActivity(), new ArrayList<Item>(0),
                mItemsOnClickInterface);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items_list_view, container, false);

        GridView gridView = view.findViewById(R.id.items_list_grid);
        gridView.setAdapter(mAdapter);
        final LinearLayout llToolbarSettings = view.findViewById(R.id.toolbar_settings);
        RelativeLayout llToolbarCheckout = view.findViewById(R.id.toolbar_checkout);
        badgeCart = view.findViewById(R.id.item_list_cart_badge);
        imageCart = view.findViewById(R.id.toolbar_image_cart);

        llToolbarSettings.setOnClickListener(loadPopupMenu);

        llToolbarCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getItemsOnCart();
            }
        });

        mPresenter.showBadge();
        badgePresenterTest();

        //Only for visual effect
        llToolbarSettings.setOnTouchListener(onTouch);
        llToolbarCheckout.setOnTouchListener(onTouch);
        return view;
    }

    private void badgePresenterTest() {
        //TODO CALL PRESENTER
        List<CartLocalObject> mItemsOnCart =
                CartLocalStorage.getInstance(this.getContext()).getCartLocal("localCart");
        CartLocalObject tempCart = new CartLocalObject();
        tempCart.setTotalCount(1);
        int totalInCart = 0;
        if (!mItemsOnCart.isEmpty()) {
            for (int i = 0; i < mItemsOnCart.size(); i++) {
                totalInCart = +totalInCart + mItemsOnCart.get(i).getTotalCount();
            }
        }
        badgeCart.setText(Integer.toString(totalInCart));
        imageCart.setImageResource(R.drawable.shopping_cart_nav_bar_icon);

        if (totalInCart == 0) {
            badgeCart.setVisibility(View.INVISIBLE);
            imageCart.setImageResource(R.drawable.shopping_cart_icon_none);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        badgePresenterTest();
    }

    @Override
    public void setPresenter(ItemsPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        // No use for this yet.
    }

    @Override
    public void showItems(List<Item> items) {
        mAdapter.replaceData(items);
    }

    @Override
    public void showLoadingItemsError() {
        showSnackbar(getString(R.string.get_items_error));
    }

    @Override
    public void updateBadge(String totalCart) {
        badgeCart.setVisibility(View.VISIBLE);
        badgeCart.setText(totalCart);
        imageCart.setImageResource(R.drawable.shopping_cart_nav_bar_icon);
    }

    @Override
    public void loadCartActivity() {
        Intent intent = new Intent(getContext(), CartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void loadCartActivityShowError() {
        alert = new AlertDialog.Builder(getActivity());
        alert.setPositiveButton(getString(R.string.ok), null);
        alert.setCancelable(true);
        alert.setMessage(getString(R.string.cart_empty_alert));
        alert.show();
    }

    @Override
    public void loadSettingsActivity() {
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override public void loadMyAccountActivity() {
        Intent intent = new Intent(getContext(), MyAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAlertIsLogged() {
        alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(getString(R.string.login_alert_logged_title));
        alert.setMessage(getString(R.string.login_alert_logged_detail));
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mPresenter.logout();
            }
        });
        alert.setNegativeButton(getString(R.string.cancel), null);
        alert.show();
    }

    private void showSnackbar(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Animate badge.
     */
    public void animateBadge() {
        final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bouncy);
        AnimateUtils interpolator = new AnimateUtils(0.2, 20);
        animation.setInterpolator(interpolator);
        imageCart.startAnimation(animation);
    }

    private class PopupMenuClickListener implements View.OnClickListener{

        @Override public void onClick(View v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View menuItem = inflater.inflate(R.layout.items_list_menu, null);
            int[] location = new int[2];
            final PopupWindow popup = new PopupWindow(getContext());
            int offsetX = 6;
            int offsetY = 0;
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;

            v.getLocationOnScreen(location);
            pointOnScreen = new Point();
            pointOnScreen.x = location[0];
            pointOnScreen.y = location[1];

            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(width, height, conf);

            popup.setContentView(menuItem);
            popup.setWidth(width);
            popup.setHeight(height);
            popup.setFocusable(true);
            popup.setBackgroundDrawable(new BitmapDrawable(getContext().getResources(), bmp));
            popup.showAtLocation(menuItem,
                Gravity.NO_GRAVITY,
                pointOnScreen.x + offsetX,
                pointOnScreen.y + offsetY
            );

            ImageView loginMenu = menuItem.findViewById(R.id.menu_login);
            loginMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.loadLoginActivity();
                    popup.dismiss();
                }
            });

            ImageView settingsMenu = menuItem.findViewById(R.id.menu_settings);
            settingsMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.loadSettings();
                    popup.dismiss();
                }
            });

            ImageView menuMenu = menuItem.findViewById(R.id.menu_menu);
            menuMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
        }
    }

}