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
