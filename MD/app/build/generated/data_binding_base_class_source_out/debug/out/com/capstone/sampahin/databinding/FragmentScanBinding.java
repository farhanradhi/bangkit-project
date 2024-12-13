// Generated by view binder compiler. Do not edit!
package com.capstone.sampahin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.capstone.sampahin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentScanBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextView Result;

  @NonNull
  public final MaterialButton analyzeButton;

  @NonNull
  public final MaterialButton btnCamera;

  @NonNull
  public final MaterialButton chatButton;

  @NonNull
  public final ConstraintLayout clProfile;

  @NonNull
  public final TextView descResult;

  @NonNull
  public final MaterialButton galleryButton;

  @NonNull
  public final Guideline guideline;

  @NonNull
  public final ImageView previewImageView;

  @NonNull
  public final LinearProgressIndicator progressIndicator;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final TextView tvResult;

  private FragmentScanBinding(@NonNull ScrollView rootView, @NonNull TextView Result,
      @NonNull MaterialButton analyzeButton, @NonNull MaterialButton btnCamera,
      @NonNull MaterialButton chatButton, @NonNull ConstraintLayout clProfile,
      @NonNull TextView descResult, @NonNull MaterialButton galleryButton,
      @NonNull Guideline guideline, @NonNull ImageView previewImageView,
      @NonNull LinearProgressIndicator progressIndicator, @NonNull ScrollView scrollView,
      @NonNull TextView tvResult) {
    this.rootView = rootView;
    this.Result = Result;
    this.analyzeButton = analyzeButton;
    this.btnCamera = btnCamera;
    this.chatButton = chatButton;
    this.clProfile = clProfile;
    this.descResult = descResult;
    this.galleryButton = galleryButton;
    this.guideline = guideline;
    this.previewImageView = previewImageView;
    this.progressIndicator = progressIndicator;
    this.scrollView = scrollView;
    this.tvResult = tvResult;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentScanBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentScanBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_scan, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentScanBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Result;
      TextView Result = ViewBindings.findChildViewById(rootView, id);
      if (Result == null) {
        break missingId;
      }

      id = R.id.analyze_button;
      MaterialButton analyzeButton = ViewBindings.findChildViewById(rootView, id);
      if (analyzeButton == null) {
        break missingId;
      }

      id = R.id.btnCamera;
      MaterialButton btnCamera = ViewBindings.findChildViewById(rootView, id);
      if (btnCamera == null) {
        break missingId;
      }

      id = R.id.chatButton;
      MaterialButton chatButton = ViewBindings.findChildViewById(rootView, id);
      if (chatButton == null) {
        break missingId;
      }

      id = R.id.cl_profile;
      ConstraintLayout clProfile = ViewBindings.findChildViewById(rootView, id);
      if (clProfile == null) {
        break missingId;
      }

      id = R.id.descResult;
      TextView descResult = ViewBindings.findChildViewById(rootView, id);
      if (descResult == null) {
        break missingId;
      }

      id = R.id.galleryButton;
      MaterialButton galleryButton = ViewBindings.findChildViewById(rootView, id);
      if (galleryButton == null) {
        break missingId;
      }

      id = R.id.guideline;
      Guideline guideline = ViewBindings.findChildViewById(rootView, id);
      if (guideline == null) {
        break missingId;
      }

      id = R.id.previewImageView;
      ImageView previewImageView = ViewBindings.findChildViewById(rootView, id);
      if (previewImageView == null) {
        break missingId;
      }

      id = R.id.progressIndicator;
      LinearProgressIndicator progressIndicator = ViewBindings.findChildViewById(rootView, id);
      if (progressIndicator == null) {
        break missingId;
      }

      ScrollView scrollView = (ScrollView) rootView;

      id = R.id.tvResult;
      TextView tvResult = ViewBindings.findChildViewById(rootView, id);
      if (tvResult == null) {
        break missingId;
      }

      return new FragmentScanBinding((ScrollView) rootView, Result, analyzeButton, btnCamera,
          chatButton, clProfile, descResult, galleryButton, guideline, previewImageView,
          progressIndicator, scrollView, tvResult);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
