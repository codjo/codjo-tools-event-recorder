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
import org.junit.Assert;
import org.junit.Test;
/**

 */
public class DefaultStatementTest {
    @Test
    public void test_order() throws Exception {
        AttributeList attributes = new AttributeList();
        attributes.put("arg1", "a1");
        attributes.put("arg2", "a2");
        DefaultStatement result = new DefaultStatement("tag", attributes);
        Assert.assertEquals("<tag arg1=\"a1\" arg2=\"a2\"/>", result.toXml());
    }


    @Test
    public void test_xmlEncoding() throws Exception {
        AttributeList attributes = new AttributeList();
        attributes.put("arg", "a \" ' & < >");
        DefaultStatement result = new DefaultStatement("tag", attributes);
        Assert.assertEquals("<tag arg=\"a &qote; &apos; &amp; &lt; &gt;\"/>", result.toXml());
    }


    @Test
    public void test_isEquivalentTo() throws Exception {
        AttributeList attributes = AttributeList.singleton("key", "value");
        DefaultStatement aDefault = new DefaultStatement("ee", attributes);
        DefaultStatement sameDefault = new DefaultStatement("ee", attributes);

        Assert.assertTrue("Même objet", aDefault.isEquivalentTo(aDefault));
        Assert.assertTrue("Egalité", aDefault.isEquivalentTo(sameDefault));

        Assert.assertFalse("Tag différent",
                           aDefault.isEquivalentTo(new DefaultStatement("different", attributes)));

        Assert.assertFalse("Attribut différent",
                           aDefault.isEquivalentTo(new DefaultStatement("ee", AttributeList.EMPTY_LIST)));

        Assert.assertFalse("Objet null", aDefault.isEquivalentTo(null));
    }


    @Test
    public void test_constructor() throws Exception {
        try {
            new DefaultStatement(null, AttributeList.singleton("key", "value"));
            Assert.fail("Tag obligatoire !");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }
        try {
            new DefaultStatement("ee", null);
            Assert.fail("Attributes est obligatoire obligatoire !");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }
    }
}
