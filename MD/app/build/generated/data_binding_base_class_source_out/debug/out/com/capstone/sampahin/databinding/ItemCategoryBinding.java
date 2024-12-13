// Generated by view binder compiler. Do not edit!
package com.capstone.sampahin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.capstone.sampahin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemCategoryBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final CardView cvCategory;

  @NonNull
  public final ImageView ivCategory;

  @NonNull
  public final TextView tvCategory;

  private ItemCategoryBinding(@NonNull CardView rootView, @NonNull CardView cvCategory,
      @NonNull ImageView ivCategory, @NonNull TextView tvCategory) {
    this.rootView = rootView;
    this.cvCategory = cvCategory;
    this.ivCategory = ivCategory;
    this.tvCategory = tvCategory;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemCategoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemCategoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_category, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemCategoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      CardView cvCategory = (CardView) rootView;

      id = R.id.iv_category;
      ImageView ivCategory = ViewBindings.findChildViewById(rootView, id);
      if (ivCategory == null) {
        break missingId;
      }

      id = R.id.tv_category;
      TextView tvCategory = ViewBindings.findChildViewById(rootView, id);
      if (tvCategory == null) {
        break missingId;
      }

      return new ItemCategoryBinding((CardView) rootView, cvCategory, ivCategory, tvCategory);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
