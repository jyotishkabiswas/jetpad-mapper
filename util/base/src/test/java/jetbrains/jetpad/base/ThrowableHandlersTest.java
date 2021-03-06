/*
 * Copyright 2012-2015 JetBrains s.r.o
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrains.jetpad.base;

import jetbrains.jetpad.test.BaseTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ThrowableHandlersTest extends BaseTestCase {
  private static Registration ourHandlerRegistration;

  @BeforeClass
  public static void addHandler() {
    ourHandlerRegistration = ThrowableHandlers.addHandler(new Handler<Throwable>() {
      @Override
      public void handle(Throwable event) {
        throw new IllegalStateException();
      }
    });
  }

  @AfterClass
  public static void removeHandler() {
    ourHandlerRegistration.remove();
  }

  @Test(expected = IllegalStateException.class)
  public void addThrowingThrowableHandler() {
    ThrowableHandlers.asInProduction(new Runnable() {
      @Override
      public void run() {
        ThrowableHandlers.handle(new IllegalArgumentException());
      }
    });
  }


}