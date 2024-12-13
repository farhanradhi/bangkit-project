// Generated by view binder compiler. Do not edit!
package com.capstone.sampahin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.capstone.sampahin.R;
import com.google.android.material.appbar.AppBarLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView appBarLine;

  @NonNull
  public final AppBarLayout appBars;

  @NonNull
  public final ImageView logoSammy;

  @NonNull
  public final ImageView mapLayout;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final RecyclerView rvCategoryHome;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView tvHome1;

  @NonNull
  public final TextView tvHome2;

  @NonNull
  public final TextView tvHome3;

  private FragmentHomeBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView appBarLine,
      @NonNull AppBarLayout appBars, @NonNull ImageView logoSammy, @NonNull ImageView mapLayout,
      @NonNull ProgressBar progressBar, @NonNull RecyclerView rvCategoryHome,
      @NonNull TextView textView, @NonNull TextView tvHome1, @NonNull TextView tvHome2,
      @NonNull TextView tvHome3) {
    this.rootView = rootView;
    this.appBarLine = appBarLine;
    this.appBars = appBars;
    this.logoSammy = logoSammy;
    this.mapLayout = mapLayout;
    this.progressBar = progressBar;
    this.rvCategoryHome = rvCategoryHome;
    this.textView = textView;
    this.tvHome1 = tvHome1;
    this.tvHome2 = tvHome2;
    this.tvHome3 = tvHome3;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_bar_line;
      ImageView appBarLine = ViewBindings.findChildViewById(rootView, id);
      if (appBarLine == null) {
        break missingId;
      }

      id = R.id.appBars;
      AppBarLayout appBars = ViewBindings.findChildViewById(rootView, id);
      if (appBars == null) {
        break missingId;
      }

      id = R.id.logo_sammy;
      ImageView logoSammy = ViewBindings.findChildViewById(rootView, id);
      if (logoSammy == null) {
        break missingId;
      }

      id = R.id.map_layout;
      ImageView mapLayout = ViewBindings.findChildViewById(rootView, id);
      if (mapLayout == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.rv_category_home;
      RecyclerView rvCategoryHome = ViewBindings.findChildViewById(rootView, id);
      if (rvCategoryHome == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.tv_home1;
      TextView tvHome1 = ViewBindings.findChildViewById(rootView, id);
      if (tvHome1 == null) {
        break missingId;
      }

      id = R.id.tv_home2;
      TextView tvHome2 = ViewBindings.findChildViewById(rootView, id);
      if (tvHome2 == null) {
        break missingId;
      }

      id = R.id.tv_home3;
      TextView tvHome3 = ViewBindings.findChildViewById(rootView, id);
      if (tvHome3 == null) {
        break missingId;
      }

      return new FragmentHomeBinding((ConstraintLayout) rootView, appBarLine, appBars, logoSammy,
          mapLayout, progressBar, rvCategoryHome, textView, tvHome1, tvHome2, tvHome3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
