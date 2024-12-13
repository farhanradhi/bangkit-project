// Generated by view binder compiler. Do not edit!
package com.capstone.sampahin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.capstone.sampahin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button aboutButton;

  @NonNull
  public final ConstraintLayout clProfile;

  @NonNull
  public final Button contactButton;

  @NonNull
  public final Guideline guideline;

  @NonNull
  public final Button historyButton;

  @NonNull
  public final ImageView ivProfile;

  @NonNull
  public final Button settingsButton;

  @NonNull
  public final Button signOutButton;

  @NonNull
  public final TextView tvEmail;

  @NonNull
  public final TextView tvUsername;

  private FragmentProfileBinding(@NonNull ConstraintLayout rootView, @NonNull Button aboutButton,
      @NonNull ConstraintLayout clProfile, @NonNull Button contactButton,
      @NonNull Guideline guideline, @NonNull Button historyButton, @NonNull ImageView ivProfile,
      @NonNull Button settingsButton, @NonNull Button signOutButton, @NonNull TextView tvEmail,
      @NonNull TextView tvUsername) {
    this.rootView = rootView;
    this.aboutButton = aboutButton;
    this.clProfile = clProfile;
    this.contactButton = contactButton;
    this.guideline = guideline;
    this.historyButton = historyButton;
    this.ivProfile = ivProfile;
    this.settingsButton = settingsButton;
    this.signOutButton = signOutButton;
    this.tvEmail = tvEmail;
    this.tvUsername = tvUsername;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.about_button;
      Button aboutButton = ViewBindings.findChildViewById(rootView, id);
      if (aboutButton == null) {
        break missingId;
      }

      id = R.id.cl_profile;
      ConstraintLayout clProfile = ViewBindings.findChildViewById(rootView, id);
      if (clProfile == null) {
        break missingId;
      }

      id = R.id.contact_button;
      Button contactButton = ViewBindings.findChildViewById(rootView, id);
      if (contactButton == null) {
        break missingId;
      }

      id = R.id.guideline;
      Guideline guideline = ViewBindings.findChildViewById(rootView, id);
      if (guideline == null) {
        break missingId;
      }

      id = R.id.history_button;
      Button historyButton = ViewBindings.findChildViewById(rootView, id);
      if (historyButton == null) {
        break missingId;
      }

      id = R.id.iv_profile;
      ImageView ivProfile = ViewBindings.findChildViewById(rootView, id);
      if (ivProfile == null) {
        break missingId;
      }

      id = R.id.settings_button;
      Button settingsButton = ViewBindings.findChildViewById(rootView, id);
      if (settingsButton == null) {
        break missingId;
      }

      id = R.id.sign_out_button;
      Button signOutButton = ViewBindings.findChildViewById(rootView, id);
      if (signOutButton == null) {
        break missingId;
      }

      id = R.id.tvEmail;
      TextView tvEmail = ViewBindings.findChildViewById(rootView, id);
      if (tvEmail == null) {
        break missingId;
      }

      id = R.id.tvUsername;
      TextView tvUsername = ViewBindings.findChildViewById(rootView, id);
      if (tvUsername == null) {
        break missingId;
      }

      return new FragmentProfileBinding((ConstraintLayout) rootView, aboutButton, clProfile,
          contactButton, guideline, historyButton, ivProfile, settingsButton, signOutButton,
          tvEmail, tvUsername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
