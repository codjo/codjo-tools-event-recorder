/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Liste de résultat de gesture.
 */
public class StatementList implements Statement {
    private List list = new ArrayList();

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
            Statement stmt = (Statement)list.get(idx);
            Statement otherStmt = (Statement)other.list.get(idx);
            if (!stmt.isEquivalentTo(otherStmt)) {
                return false;
            }
        }
        return true;
    }


    public String toXml() {
        StringBuffer buffer = new StringBuffer();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Statement result = (Statement)iter.next();
            buffer.append(result.toXml());
            if (iter.hasNext()) {
                buffer.append('\n');
            }
        }
        return buffer.toString();
    }


    public void add(Statement result) {
        list.add(result);
    }


    public Statement lastResult() {
        if (list.size() == 0) {
            return null;
        }
        return (Statement)list.get(list.size() - 1);
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
        return (Statement)list.get(index);
    }
}
