/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
import org.junit.Assert;
import org.junit.Test;
/**

 */
public class StatementListTest {
    @Test
    public void test_isEquivalentTo() throws Exception {
        StatementList listA = new StatementList();
        StatementList listB = new StatementList();

        Assert.assertTrue("Vide est equivalent � vide", listA.isEquivalentTo(listB));
        Assert.assertTrue("listA==listA", listA.isEquivalentTo(listA));

        listA.add(newStatement("tag1"));
        Assert.assertFalse("Pas le m�me nombre d'�l�ment", listA.isEquivalentTo(listB));

        listB.add(newStatement("tag1"));
        Assert.assertTrue("Liste �quivalente", listA.isEquivalentTo(listB));

        listB.add(newStatement("tag2"));
        listA.add(newStatement("tag2other"));
        Assert.assertFalse("Pas les m�me �l�ments", listA.isEquivalentTo(listB));
    }


    @Test
    public void test_isEquivalentTo_badcase() throws Exception {
        StatementList listA = new StatementList();
        Assert.assertFalse("Pas le m�me nombre d'�l�ment",
                           listA.isEquivalentTo(newStatement("tag1")));
    }


    private DefaultStatement newStatement(String tagName) {
        return new DefaultStatement(tagName, AttributeList.EMPTY_LIST);
    }
}
