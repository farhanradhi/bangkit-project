package com.capstone.sampahin.ui.scan;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.capstone.sampahin.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ScanFragmentDirections {
  private ScanFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationScanToNavigationTopics() {
    return new ActionOnlyNavDirections(R.id.action_navigation_scan_to_navigation_topics);
  }

  @NonNull
  public static ActionNavigationScanToNavigationChat actionNavigationScanToNavigationChat(
      @NonNull String topicTitle) {
    return new ActionNavigationScanToNavigationChat(topicTitle);
  }

  @NonNull
  public static NavDirections actionNavigationScanToNavigationHome() {
    return new ActionOnlyNavDirections(R.id.action_navigation_scan_to_navigation_home);
  }

  public static class ActionNavigationScanToNavigationChat implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionNavigationScanToNavigationChat(@NonNull String topicTitle) {
      if (topicTitle == null) {
        throw new IllegalArgumentException("Argument \"topicTitle\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("topicTitle", topicTitle);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavigationScanToNavigationChat setTopicTitle(@NonNull String topicTitle) {
      if (topicTitle == null) {
        throw new IllegalArgumentException("Argument \"topicTitle\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("topicTitle", topicTitle);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("topicTitle")) {
        String topicTitle = (String) arguments.get("topicTitle");
        __result.putString("topicTitle", topicTitle);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_navigation_scan_to_navigation_chat;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getTopicTitle() {
      return (String) arguments.get("topicTitle");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavigationScanToNavigationChat that = (ActionNavigationScanToNavigationChat) object;
      if (arguments.containsKey("topicTitle") != that.arguments.containsKey("topicTitle")) {
        return false;
      }
      if (getTopicTitle() != null ? !getTopicTitle().equals(that.getTopicTitle()) : that.getTopicTitle() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getTopicTitle() != null ? getTopicTitle().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavigationScanToNavigationChat(actionId=" + getActionId() + "){"
          + "topicTitle=" + getTopicTitle()
          + "}";
    }
  }
}
