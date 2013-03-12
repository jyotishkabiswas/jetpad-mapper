/*
 * Copyright 2012-2013 JetBrains s.r.o
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
package jetbrains.jetpad.model.property;

import com.google.common.base.Objects;
import jetbrains.jetpad.model.event.EventHandler;
import jetbrains.jetpad.model.event.ListenerCaller;
import jetbrains.jetpad.model.event.Listeners;
import jetbrains.jetpad.model.event.Registration;

public abstract class BaseDerivedProperty<ValueT> extends BaseReadableProperty<ValueT> {
  private Listeners<EventHandler<PropertyChangeEvent<ValueT>>> myHandlers;
  private ValueT myOldValue;

  protected BaseDerivedProperty(ValueT initialValue) {
    myOldValue = initialValue;
  }

  protected abstract void doAddListeners();

  protected abstract void doRemoveListeners();

  protected void somethingChanged() {
    ValueT newValue = get();
    if (Objects.equal(myOldValue, newValue)) return;

    final PropertyChangeEvent<ValueT> event = new PropertyChangeEvent<ValueT>(myOldValue, newValue);
    myOldValue = newValue;

    if (myHandlers != null) {
      myHandlers.fire(new ListenerCaller<EventHandler<PropertyChangeEvent<ValueT>>>() {
        @Override
        public void call(EventHandler<PropertyChangeEvent<ValueT>> item) {
          item.onEvent(event);
        }
      });
    }
  }

  @Override
  public Registration addHandler(final EventHandler<PropertyChangeEvent<ValueT>> handler) {
    if (myHandlers == null) {
      myHandlers = new Listeners<EventHandler<PropertyChangeEvent<ValueT>>>();
    }
    if (myHandlers.isEmpty()) {
      myOldValue = get();
      doAddListeners();
    }
    final Registration reg = myHandlers.add(handler);
    return new Registration() {
      @Override
      public void remove() {
        reg.remove();
        if (myHandlers.isEmpty()) {
          doRemoveListeners();
        }
      }
    };
  }
}