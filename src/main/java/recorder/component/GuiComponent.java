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
import javax.swing.*;
/**
 * Represents a GUI component.
 */
public class GuiComponent {
    private static final NoFindStrategy NO_FIND_STRATEGY = new NoFindStrategy();
    private static final FindStrategy[] FIND_STRATEGY =
          new FindStrategy[]{
                new FindByName(), new FindByLabel(), new FindByAccessibleContext(),
                NO_FIND_STRATEGY
          };
    private JComponent swingComponent;


    GuiComponent(JComponent swingComponent) {
        this.swingComponent = swingComponent;
    }


    public boolean isFindable() {
        return NO_FIND_STRATEGY != determineFindStrategy();
    }


    public JComponent getSwingComponent() {
        return swingComponent;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GuiComponent)) {
            return false;
        }

        //noinspection RedundantIfStatement
        if (!swingComponent.equals(((GuiComponent)obj).swingComponent)) {
            return false;
        }

        return true;
    }


    public int hashCode() {
        return swingComponent.hashCode();
    }


    public String toString() {
        return "GuiComponent{" + "swingComponent=" + swingComponent + "}";
    }


    public String getName() {
        return getSwingComponent().getName();
    }


    public String getLabel() {
        return ((FindByLabel)getFindStrategy(FindStrategyId.BY_LABEL)).getLabelFor(getSwingComponent());
    }


    public String getAccessibleName() {
        return swingComponent.getAccessibleContext().getAccessibleName();
    }


    public boolean isA(Class aClass) {
        return aClass.isAssignableFrom(getSwingComponent().getClass());
    }


    public FindStrategyId getBestFindStrategyId() {
        FindStrategy strategy = determineFindStrategy();
        return strategy.getStrategyId();
    }


    public boolean canBeFoundWith(FindStrategyId strategyId) {
        FindStrategy findStrategy = getFindStrategy(strategyId);
        return findStrategy.canFound(swingComponent);
    }


    private FindStrategy determineFindStrategy() {
        for (FindStrategy findStrategy : FIND_STRATEGY) {
            if (findStrategy.canFound(swingComponent)) {
                return findStrategy;
            }
        }
        throw new IllegalStateException("Should not happen ! Rule 'NoFindStrategy' is always valid");
    }


    private static FindStrategy getFindStrategy(FindStrategyId strategyId) {
        for (FindStrategy findStrategy : FIND_STRATEGY) {
            if (strategyId.equals(findStrategy.getStrategyId())) {
                return findStrategy;
            }
        }
        throw new IllegalStateException("Unknown search strategy " + strategyId);
    }


    /**
     * Strat»gie de recherche representant le cas ou le composant est inretrouvable.
     */
    private static class NoFindStrategy implements FindStrategy {
        public boolean canFound(JComponent swingComponent) {
            return true;
        }


        public FindStrategyId getStrategyId() {
            return FindStrategyId.NONE;
        }
    }
}
