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
package recorder.gui.assertion;
import org.junit.Assert;
import org.junit.Test;
import recorder.Recorder;
import recorder.component.GuiComponentFactory;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.Statement;
/**

 */
public class AssertContextTest {
    @Test
    public void test_constructor() throws Exception {
        try {
            new AssertContext(null);
            Assert.fail("The recorder must be defined");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }
    }


    @Test
    public void test_postAssert() throws Exception {
        Recorder recorder = new Recorder(new GuiComponentFactory());
        AssertContext context = new AssertContext(recorder);
        Statement assertStmt =
              new DefaultStatement("assertion", AttributeList.EMPTY_LIST);

        context.postAssert(assertStmt);

        Assert.assertEquals(assertStmt, recorder.getGestureResultList().lastResult());
    }
}
