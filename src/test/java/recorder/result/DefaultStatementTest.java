/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
import junit.framework.TestCase;
/**

 */
public class DefaultStatementTest extends TestCase {
    public void test_order() throws Exception {
        AttributeList attributes = new AttributeList();
        attributes.put("arg1", "a1");
        attributes.put("arg2", "a2");
        DefaultStatement result = new DefaultStatement("tag", attributes);
        assertEquals("<tag arg1=\"a1\" arg2=\"a2\"/>", result.toXml());
    }


    public void test_xmlEncoding() throws Exception {
        AttributeList attributes = new AttributeList();
        attributes.put("arg", "a \" ' & < >");
        DefaultStatement result = new DefaultStatement("tag", attributes);
        assertEquals("<tag arg=\"a &qote; &apos; &amp; &lt; &gt;\"/>", result.toXml());
    }


    public void test_isEquivalentTo() throws Exception {
        AttributeList attributes = AttributeList.singleton("key", "value");
        DefaultStatement aDefault = new DefaultStatement("ee", attributes);
        DefaultStatement sameDefault = new DefaultStatement("ee", attributes);

        assertTrue("Même objet", aDefault.isEquivalentTo(aDefault));
        assertTrue("Egalité", aDefault.isEquivalentTo(sameDefault));

        assertFalse("Tag différent",
                    aDefault.isEquivalentTo(new DefaultStatement("different", attributes)));

        assertFalse("Attribut différent",
                    aDefault.isEquivalentTo(new DefaultStatement("ee", AttributeList.EMPTY_LIST)));

        assertFalse("Objet null", aDefault.isEquivalentTo(null));
    }


    public void test_constructor() throws Exception {
        try {
            new DefaultStatement(null, AttributeList.singleton("key", "value"));
            fail("Tag obligatoire !");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }
        try {
            new DefaultStatement("ee", null);
            fail("Attributes est obligatoire obligatoire !");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }
    }
}
