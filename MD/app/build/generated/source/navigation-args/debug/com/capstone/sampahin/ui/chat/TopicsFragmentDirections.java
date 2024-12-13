package com.capstone.sampahin.ui.chat;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.capstone.sampahin.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class TopicsFragmentDirections {
  private TopicsFragmentDirections() {
  }

  @NonNull
  public static ActionTopicsFragmentToChatFragment actionTopicsFragmentToChatFragment(
      @NonNull String topicTitle) {
    return new ActionTopicsFragmentToChatFragment(topicTitle);
  }

  public static class ActionTopicsFragmentToChatFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionTopicsFragmentToChatFragment(@NonNull String topicTitle) {
      if (topicTitle == null) {
        throw new IllegalArgumentException("Argument \"topicTitle\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("topicTitle", topicTitle);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionTopicsFragmentToChatFragment setTopicTitle(@NonNull String topicTitle) {
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
      return R.id.action_topicsFragment_to_chatFragment;
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
      ActionTopicsFragmentToChatFragment that = (ActionTopicsFragmentToChatFragment) object;
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
      return "ActionTopicsFragmentToChatFragment(actionId=" + getActionId() + "){"
          + "topicTitle=" + getTopicTitle()
          + "}";
    }
  }
}
