/*
 * codjo.net
 *
 * Common Apache License 2.0
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
