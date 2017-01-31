package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Interfaces.PrefListable;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements PrefListable {

    List<Product> productList ;
    Context prContext;


    public ProductAdapter(Context context) {
        this.prContext = context;
        this.productList = UtlFirebase.getProducts(UserSingleton.getInstance().getUserCompanyID());
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_adapter_row_6, null);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, final int position) {

        final Product productItem = productList.get(position);
        holder.productName.setText(productItem.getProductName());
        holder.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(prContext);
                editText.setText(productItem.getProductName());
                new MaterialDialog.Builder(prContext)
                        .title(R.string.label_edit_prefitem_dialog_title)
                        .input("", productItem.getProductName(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        })
                        //.onPositive() //TODO:add method
                        .positiveText(R.string.label_button_save)
                        .contentColorRes(R.color.textColor_primary)
                        .contentGravity(GravityEnum.CENTER)
                        .negativeText(R.string.label_button_cancel)
                        .titleGravity(GravityEnum.END)
                        .buttonsGravity(GravityEnum.END)
                        .backgroundColorRes(R.color.app_bg)
                        .titleColorRes(R.color.textColor_lighter)
                        .positiveColorRes(R.color.colorPrimary)
                        .negativeColorRes(R.color.colorPrimaryDark)
                        .dividerColorRes(R.color.textColor_lighter)
                        .show();
            }
        });

        holder.iconRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(prContext)
                        .title(R.string.label_remove_prefitem_dialog_title)
                        .content(productList.get(position).getProductName())
                        .positiveText(R.string.label_button_confirm)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick( MaterialDialog dialog,  DialogAction which) {
                                dialog.dismiss();
                                new MaterialDialog.Builder(prContext)
                                        .title(R.string.label_confirm_remove)
                                        .content(productList.get(position).getProductName())
                                        .positiveText(R.string.label_button_confirm)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick( MaterialDialog dialog,  DialogAction which) {
                                                productList.remove(position);
                                                ProductAdapter.super.notifyItemRemoved(position);
                                            }
                                        })
                                        .contentColorRes(R.color.textColor_primary)
                                        .contentGravity(GravityEnum.CENTER)
                                        .negativeText(R.string.label_button_cancel)
                                        .titleGravity(GravityEnum.END)
                                        .buttonsGravity(GravityEnum.END)
                                        .backgroundColorRes(R.color.app_bg)
                                        .titleColorRes(R.color.textColor_lighter)
                                        .positiveColorRes(R.color.colorPrimary)
                                        .negativeColorRes(R.color.colorPrimaryDark)
                                        .dividerColorRes(R.color.textColor_lighter)
                                        .show();
                            }
                        })
                        .contentColorRes(R.color.textColor_primary)
                        .contentGravity(GravityEnum.CENTER)
                        .negativeText(R.string.label_button_cancel)
                        .titleGravity(GravityEnum.END)
                        .buttonsGravity(GravityEnum.END)
                        .backgroundColorRes(R.color.app_bg)
                        .titleColorRes(R.color.textColor_lighter)
                        .positiveColorRes(R.color.colorPrimary)
                        .negativeColorRes(R.color.colorPrimaryDark)
                        .dividerColorRes(R.color.textColor_lighter)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != productList ? productList.size() : 0);
    }


    public class ProductHolder extends RecyclerView.ViewHolder {
        protected View view;
        protected String productID;
        protected TextView productName;
        protected ImageView iconEdit, iconRemove;

        public ProductHolder(View view) {
            super(view);

            this.view = view;
            this.productName = (TextView) view.findViewById(R.id.prefText);
            this.iconEdit = (ImageView) view.findViewById(R.id.prefIconEdit);
            this.iconRemove = (ImageView) view.findViewById(R.id.prefIconRemove);
        }

    }
}

