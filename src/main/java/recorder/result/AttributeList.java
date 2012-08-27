/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * Représente une liste d'attribut d'un tag.
 */
public class AttributeList {
    public static final AttributeList EMPTY_LIST = new AttributeList();
    private List list = new ArrayList();

    public void put(String name, Object value) {
        Attribute newAttribute = new Attribute(name, value);

        removeAttribute(newAttribute.getName());

        list.add(newAttribute);
    }


    public Iterator iterator() {
        return Collections.unmodifiableList(list).iterator();
    }


    public static AttributeList singleton(String name, Object value) {
        AttributeList list = new AttributeList();
        list.put(name, value);
        return list;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AttributeList)) {
            return false;
        }
        return toMap().equals(((AttributeList)obj).toMap());
    }


    public int hashCode() {
        return toMap().hashCode();
    }


    private Map toMap() {
        Map map = new HashMap();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Attribute attribute = (Attribute)iter.next();
            map.put(attribute.getName(), attribute.getValue());
        }
        return map;
    }


    private void removeAttribute(String name) {
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Attribute attribute = (Attribute)iter.next();
            if (attribute.getName() == name) {
                iter.remove();
            }
        }
    }
}
