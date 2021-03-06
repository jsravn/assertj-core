/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.core.internal;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;

import java.io.IOException;

import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.test.TestData.someInfo;
import static org.mockito.Mockito.spy;

/**
 * Base test class for {@link java.net.URI} tests.
 *
 * @author Alexander Bischof
 */
public abstract class UrisBaseTest {

  @Rule
  public ExpectedException thrown = none();
  protected Failures failures;
  protected Uris uris;
  protected AssertionInfo info;


  @Before
  public void setUp() throws IOException {
	failures = spy(new Failures());
	uris = new Uris();
	uris.failures = failures;
	info = someInfo();
  }
}