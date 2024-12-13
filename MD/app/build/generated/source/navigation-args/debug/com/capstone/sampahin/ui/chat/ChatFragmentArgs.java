package com.capstone.sampahin.ui.chat;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ChatFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ChatFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private ChatFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChatFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ChatFragmentArgs __result = new ChatFragmentArgs();
    bundle.setClassLoader(ChatFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("topicTitle")) {
      String topicTitle;
      topicTitle = bundle.getString("topicTitle");
      if (topicTitle == null) {
        throw new IllegalArgumentException("Argument \"topicTitle\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("topicTitle", topicTitle);
    } else {
      throw new IllegalArgumentException("Required argument \"topicTitle\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChatFragmentArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    ChatFragmentArgs __result = new ChatFragmentArgs();
    if (savedStateHandle.contains("topicTitle")) {
      String topicTitle;
      topicTitle = savedStateHandle.get("topicTitle");
      if (topicTitle == null) {
        throw new IllegalArgumentException("Argument \"topicTitle\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("topicTitle", topicTitle);
    } else {
      throw new IllegalArgumentException("Required argument \"topicTitle\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getTopicTitle() {
    return (String) arguments.get("topicTitle");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("topicTitle")) {
      String topicTitle = (String) arguments.get("topicTitle");
      __result.putString("topicTitle", topicTitle);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("topicTitle")) {
      String topicTitle = (String) arguments.get("topicTitle");
      __result.set("topicTitle", topicTitle);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    ChatFragmentArgs that = (ChatFragmentArgs) object;
    if (arguments.containsKey("topicTitle") != that.arguments.containsKey("topicTitle")) {
      return false;
    }
    if (getTopicTitle() != null ? !getTopicTitle().equals(that.getTopicTitle()) : that.getTopicTitle() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getTopicTitle() != null ? getTopicTitle().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ChatFragmentArgs{"
        + "topicTitle=" + getTopicTitle()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ChatFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String topicTitle) {
      if (topicTitle == null) {
        throw new IllegalArgumentException("Argument \"topicTitle\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("topicTitle", topicTitle);
    }

    @NonNull
    public ChatFragmentArgs build() {
      ChatFragmentArgs result = new ChatFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setTopicTitle(@NonNull String topicTitle) {
      if (topicTitle == null) {
        throw new IllegalArgumentException("Argument \"topicTitle\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("topicTitle", topicTitle);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getTopicTitle() {
      return (String) arguments.get("topicTitle");
    }
  }
}
