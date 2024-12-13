package com.capstone.sampahin.ui.home;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.capstone.sampahin.R;

public class HomeFragmentDirections {
  private HomeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationHomeToNavigationMaps() {
    return new ActionOnlyNavDirections(R.id.action_navigation_home_to_navigation_maps);
  }

  @NonNull
  public static NavDirections actionNavigationHomeToNavigationMaps2() {
    return new ActionOnlyNavDirections(R.id.action_navigation_home_to_navigation_maps2);
  }
}
