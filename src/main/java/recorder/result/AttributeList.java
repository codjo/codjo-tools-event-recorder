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
 * Represents attribute list of a tag.
 */
public class AttributeList {
    public static final AttributeList EMPTY_LIST = new AttributeList();
    private List<Attribute> list = new ArrayList<Attribute>();


    public void put(String name, Object value) {
        Attribute newAttribute = new Attribute(name, value);

        removeAttribute(newAttribute.getName());

        list.add(newAttribute);
    }


    public Iterator<Attribute> iterator() {
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
        //noinspection SimplifiableIfStatement
        if (!(obj instanceof AttributeList)) {
            return false;
        }
        return toMap().equals(((AttributeList)obj).toMap());
    }


    public int hashCode() {
        return toMap().hashCode();
    }


    private Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Attribute attribute : list) {
            map.put(attribute.getName(), attribute.getValue());
        }
        return map;
    }


    private void removeAttribute(String name) {
        for (Iterator<Attribute> iterator = list.iterator(); iterator.hasNext(); ) {
            Attribute attribute = iterator.next();
            if (attribute.getName().equals(name)) {
                iterator.remove();
            }
        }
    }
}
