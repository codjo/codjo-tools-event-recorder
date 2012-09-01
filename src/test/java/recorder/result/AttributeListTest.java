/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**

 */
public class AttributeListTest {
    private AttributeList list;


    @Test
    public void test_singleton() throws Exception {
        list = AttributeList.singleton("arg", "value");

        Iterator iter = list.iterator();

        Assert.assertTrue("Un element", iter.hasNext());
        Attribute attribute = (Attribute)iter.next();
        Assert.assertEquals("arg", attribute.getName());
        Assert.assertEquals("value", attribute.getValue());

        Assert.assertFalse("Pas d'autre element", iter.hasNext());
    }


    @Test
    public void test_equals() throws Exception {
        list.put("key1", "val1");
        AttributeList sameList = AttributeList.singleton("key1", "val1");

        Assert.assertTrue("Même objet", list.equals(list));
        Assert.assertTrue("Egalité", list.equals(sameList));
        Assert.assertEquals("hashCode", list.hashCode(), sameList.hashCode());

        Assert.assertFalse("Value différent",
                           list.equals(AttributeList.singleton("key1", "different")));
        Assert.assertFalse("Tag différent",
                           list.equals(AttributeList.singleton("different", "val1")));
    }


    @Test
    public void test_equals_badclass() throws Exception {
        list.put("key1", "val1");

        Assert.assertFalse("Même objet", list.equals("Bad classs"));
    }


    @Test
    public void test_equals_plusieurs() throws Exception {
        list.put("a", "a");
        list.put("b", "b");

        AttributeList otherList = new AttributeList();
        otherList.put("b", "b");
        otherList.put("a", "a");

        Assert.assertTrue("Egalité", list.equals(otherList));

        otherList.put("c", "c");

        Assert.assertFalse("Different", list.equals(otherList));
    }


    @Test
    public void test_equals_override() throws Exception {
        list.put("a", "a1");
        list.put("a", "last");

        Iterator iter = list.iterator();

        Assert.assertTrue("Un element 'a'", iter.hasNext());
        iter.next();
        Assert.assertFalse("Un seul element", iter.hasNext());
    }


    @Before
    public void setUp() throws Exception {
        list = new AttributeList();
    }
}
