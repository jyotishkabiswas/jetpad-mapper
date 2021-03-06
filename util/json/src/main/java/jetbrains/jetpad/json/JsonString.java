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
package jetbrains.jetpad.json;

import javax.annotation.Nonnull;

public class JsonString extends JsonValue {
  private String myValue;

  public JsonString(@Nonnull String value) {
    myValue = value;
  }

  public String getStringValue() {
    return myValue;
  }

  protected void toString(IndentBuilder builder) {
    builder.append("\"");
    builder.append(JsonUtil.escape(myValue));
    builder.append("\"");
  }
}