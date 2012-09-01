/*
 * codjo (Prototype)
 * =================
 *
 *    Copyright (C) 2005, 2012 by codjo.net
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied. See the License for the specific language governing permissions
 *    and limitations under the License.
 */
package recorder.component;
import javax.accessibility.AccessibleContext;
import javax.swing.*;
/**
 * Search strategy using the swing accessible context. Can find a component by the {@link javax.accessibility.AccessibleContext} name.
 */
class FindByAccessibleContext implements FindStrategy {
    public boolean canFound(JComponent swingComponent) {
        AccessibleContext accessibleContext = swingComponent.getAccessibleContext();
        return accessibleContext != null && accessibleContext.getAccessibleName() != null
               && accessibleContext.getAccessibleName().length() > 0;
    }


    public FindStrategyId getStrategyId() {
        return FindStrategyId.BY_ACCESSIBLE_CONTEXT;
    }
}
