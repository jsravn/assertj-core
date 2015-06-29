package org.assertj.core.internal.progress;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class AssertionProgressImplTest {

  private AssertionProgressImpl progress = new AssertionProgressImpl();

  @Test
  public void should_fail_if_started_before_in_progress() {
    progress.startAssertion();

    try {
      progress.startAssertion();
      fail("should have failed due to dangling assert");
    } catch (IllegalStateException e) {
      // expected
    }
  }

  @Test
  public void should_not_fail_if_started_while_in_progress() {
    progress.startAssertion();
    progress.assertionInProgress();
    progress.startAssertion();
  }

  @Test
  public void should_show_line_number_of_dangling_assert_in_stacktrace() {
    int thisLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();
    progress.startAssertion();

    try {
      progress.startAssertion();
    } catch (IllegalStateException e) {
      StackTraceElement firstElement = e.getStackTrace()[0];
      assertThat(firstElement.getClassName()).isEqualTo(getClass().getName());
      assertThat(firstElement.getLineNumber()).isEqualTo(thisLineNumber + 1);
    }
  }
}