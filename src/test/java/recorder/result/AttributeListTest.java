/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
import java.util.Iterator;
import junit.framework.TestCase;
/**
 * Classe de test de {@link AttributeList}.
 */
public class AttributeListTest extends TestCase {
    private AttributeList list;

    public void test_singleton() throws Exception {
        list = AttributeList.singleton("arg", "value");

        Iterator iter = list.iterator();

        assertTrue("Un element", iter.hasNext());
        Attribute attribute = (Attribute)iter.next();
        assertEquals("arg", attribute.getName());
        assertEquals("value", attribute.getValue());

        assertFalse("Pas d'autre element", iter.hasNext());
    }


    public void test_equals() throws Exception {
        list.put("key1", "val1");
        AttributeList sameList = AttributeList.singleton("key1", "val1");

        assertTrue("Même objet", list.equals(list));
        assertTrue("Egalité", list.equals(sameList));
        assertEquals("hashCode", list.hashCode(), sameList.hashCode());

        assertFalse("Value différent",
            list.equals(AttributeList.singleton("key1", "different")));
        assertFalse("Tag différent",
            list.equals(AttributeList.singleton("different", "val1")));
    }


    public void test_equals_badclass() throws Exception {
        list.put("key1", "val1");

        assertFalse("Même objet", list.equals("Bad classs"));
    }


    public void test_equals_plusieurs() throws Exception {
        list.put("a", "a");
        list.put("b", "b");

        AttributeList otherList = new AttributeList();
        otherList.put("b", "b");
        otherList.put("a", "a");

        assertTrue("Egalité", list.equals(otherList));

        otherList.put("c", "c");

        assertFalse("Different", list.equals(otherList));
    }


    public void test_equals_override() throws Exception {
        list.put("a", "a1");
        list.put("a", "last");

        Iterator iter = list.iterator();

        assertTrue("Un element 'a'", iter.hasNext());
        iter.next();
        assertFalse("Un seul element", iter.hasNext());
    }


    protected void setUp() throws Exception {
        list = new AttributeList();
    }
}
