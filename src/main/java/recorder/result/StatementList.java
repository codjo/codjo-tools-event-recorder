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
package recorder.result;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class StatementList implements Statement {
    private List<Statement> list = new ArrayList<Statement>();


    public boolean isEquivalentTo(Statement obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatementList)) {
            return false;
        }
        StatementList other = (StatementList)obj;
        if (list.size() != other.list.size()) {
            return false;
        }

        for (int idx = 0; idx < list.size() && idx < other.list.size(); idx++) {
            Statement stmt = list.get(idx);
            Statement otherStmt = other.list.get(idx);
            if (!stmt.isEquivalentTo(otherStmt)) {
                return false;
            }
        }
        return true;
    }


    public String toXml() {
        StringBuilder buffer = new StringBuilder();
        for (Iterator<Statement> iterator = list.iterator(); iterator.hasNext(); ) {
            Statement result = iterator.next();
            buffer.append(result.toXml());
            if (iterator.hasNext()) {
                buffer.append('\n');
            }
        }
        return buffer.toString();
    }


    public void add(Statement result) {
        list.add(result);
    }


    public Statement lastResult() {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }


    public void clear() {
        list.clear();
    }


    public void removeLastResult() {
        list.remove(list.size() - 1);
    }


    public int size() {
        return list.size();
    }


    public Statement get(int index) {
        return list.get(index);
    }
}
