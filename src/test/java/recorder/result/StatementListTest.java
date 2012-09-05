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
public class StatementListTest {
    @Test
    public void test_isEquivalentTo() throws Exception {
        StatementList listA = new StatementList();
        StatementList listB = new StatementList();

        Assert.assertTrue("Vide est equivalent à vide", listA.isEquivalentTo(listB));
        Assert.assertTrue("listA==listA", listA.isEquivalentTo(listA));

        listA.add(newStatement("tag1"));
        Assert.assertFalse("Pas le même nombre d'élément", listA.isEquivalentTo(listB));

        listB.add(newStatement("tag1"));
        Assert.assertTrue("Liste équivalente", listA.isEquivalentTo(listB));

        listB.add(newStatement("tag2"));
        listA.add(newStatement("tag2other"));
        Assert.assertFalse("Pas les même éléments", listA.isEquivalentTo(listB));
    }


    @Test
    public void test_isEquivalentTo_badcase() throws Exception {
        StatementList listA = new StatementList();
        Assert.assertFalse("Pas le même nombre d'élément",
                           listA.isEquivalentTo(newStatement("tag1")));
    }


    private DefaultStatement newStatement(String tagName) {
        return new DefaultStatement(tagName, AttributeList.EMPTY_LIST);
    }
}
