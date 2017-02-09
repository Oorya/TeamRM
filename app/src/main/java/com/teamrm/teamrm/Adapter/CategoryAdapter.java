package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> implements PrefListable {

    List<Category> categoryList = new ArrayList<>();
    Context cContext;
    private static final String TAG = "CategoryAdapter:::";


    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.cContext = context;
        Log.d(TAG, "called constructor");
        this.categoryList = categoryList;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_adapter_row_6, null);
        return new CategoryHolder(view);
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
                new MaterialDialog.Builder(cContext)
                        .title(R.string.label_edit_prefitem_dialog_title)
                        .input("", categoryItem.getCategoryName(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                Log.d(TAG, "updating category " + categoryItem.getCategoryName());
                                UtlFirebase.updateCategory(UserSingleton.getInstance().getAssignedCompanyID(), categoryItem, input.toString());
                            }
                        })
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
                new MaterialDialog.Builder(cContext)
                        .title(R.string.label_remove_text)
                        .content(categoryList.get(position).getCategoryName())
                        .positiveText(R.string.label_button_confirm)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                new MaterialDialog.Builder(cContext)
                                        .title(R.string.label_confirm_remove)
                                        .content(categoryList.get(position).getCategoryName())
                                        .positiveText(R.string.label_button_confirm)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                UtlFirebase.removeCategory(UserSingleton.getInstance().getAssignedCompanyID(), categoryItem);
                                            }
                                        })
                                        .contentColorRes(R.color.textColor_primary)
                                        .contentGravity(GravityEnum.CENTER)
                                        .negativeText(R.string.label_button_cancel)
                                        .titleGravity(GravityEnum.END)
                                        .buttonsGravity(GravityEnum.END)
                                        .backgroundColorRes(R.color.meterial_red_100)
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
        return (null != categoryList ? categoryList.size() : 0);
    }


    public class CategoryHolder extends RecyclerView.ViewHolder {
        protected View view;
        protected TextView categoryName;
        protected ImageView iconEdit, iconRemove;

        public CategoryHolder(View view) {
            super(view);
            this.view = view;
            this.categoryName = (TextView) view.findViewById(R.id.prefText);
            this.iconEdit = (ImageView) view.findViewById(R.id.prefIconEdit);
            this.iconRemove = (ImageView) view.findViewById(R.id.prefIconRemove);
        }

    }

}

