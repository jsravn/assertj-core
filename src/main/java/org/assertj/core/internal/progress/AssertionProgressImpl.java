package org.assertj.core.internal.progress;

import java.util.ArrayList;
import java.util.List;

public class AssertionProgressImpl implements AssertionProgress {

  private enum Progress {
    NOT_STARTED, STARTED, IN_PROGRESS
  }

  private Progress progress = Progress.NOT_STARTED;
  private StackTraceElement[] lastStartedStackTrace = getAssertionStackTrace();

  @Override
  public void startAssertion() {
    if (progress == Progress.STARTED) {
      RuntimeException e = new IllegalStateException("Invalid usage of assertj detected, did you use" +
              " #assertThat without asserting anything?\n Invalid usage detected here:");
      e.setStackTrace(lastStartedStackTrace);
      throw e;
    }

    progress = Progress.STARTED;
    lastStartedStackTrace = getAssertionStackTrace();
  }

  @Override
  public void assertionInProgress() {
    progress = Progress.IN_PROGRESS;
  }

  private StackTraceElement[] getAssertionStackTrace() {
    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    List<StackTraceElement> filteredElements = new ArrayList<>();

    for (StackTraceElement element : elements) {
      if (element.getClassName().startsWith("org.assertj")
              && !element.getClassName().endsWith("Test")) {
        continue;
      }

      if (element.getClassName().startsWith("java.lang.Thread")
              && element.getMethodName().startsWith("getStackTrace")) {
        continue;
      }

      filteredElements.add(element);
    }

    return filteredElements.toArray(new StackTraceElement[filteredElements.size()]);
  }
}
