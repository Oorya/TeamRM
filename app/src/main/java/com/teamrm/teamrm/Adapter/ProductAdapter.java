package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamrm.teamrm.Interfaces.PrefListable;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements PrefListable {

    List<Product> productList = new ArrayList<>();
    Context prContext;


    public ProductAdapter(Context context, List<Product> productList) {
        this.prContext = context;
        this.productList = productList;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_adapter_row_1, null);
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
                new AlertDialog.Builder(prContext)
                        .setCancelable(true)
                        .setView(editText)
                        .setTitle(R.string.label_edittext_dialog_title)
                        .setPositiveButton(R.string.label_button_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ProductAdapter.super.notifyItemChanged(position);
                            }
                        })
                        .setNegativeButton(R.string.label_button_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
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
        protected ImageView iconEdit;

        public ProductHolder(View view) {
            super(view);

            this.view = view;
            this.productName = (TextView) view.findViewById(R.id.prefText);
            this.iconEdit = (ImageView) view.findViewById(R.id.prefIconEdit);
        }

    }
}

