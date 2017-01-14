package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> implements PrefListable {

    List<Category> categoryList = new ArrayList<>();
    Context cContext;


    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.cContext = context;
        this.categoryList = categoryList;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_adapter_row_1, null);
        CategoryAdapter.CategoryHolder cHolder = new CategoryAdapter.CategoryHolder(view);
        return cHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryHolder holder, final int position) {

        final Category categoryItem = categoryList.get(position);
        holder.categoryName.setText(categoryItem.getCategoryName());
        holder.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(cContext);
                editText.setText(categoryItem.getCategoryName());
                new AlertDialog.Builder(cContext)
                        .setCancelable(true)
                        .setView(editText)
                        .setTitle(R.string.label_edittext_dialog_title)
                        .setPositiveButton(R.string.label_button_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CategoryAdapter.super.notifyItemChanged(position);
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
        return (null != categoryList ? categoryList.size() : 0);
    }


    public class CategoryHolder extends RecyclerView.ViewHolder {
        protected View view;
        protected String categoryID;
        protected TextView categoryName;
        protected ImageView iconEdit;

        public CategoryHolder(View view) {
            super(view);

            this.view = view;
            this.categoryName = (TextView) view.findViewById(R.id.prefText);
            this.iconEdit = (ImageView) view.findViewById(R.id.prefIconEdit);
        }

    }
}

