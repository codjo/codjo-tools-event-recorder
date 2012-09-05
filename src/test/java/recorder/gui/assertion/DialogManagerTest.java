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
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**

 */
public class DialogManagerTest {
    private DialogManager manager;


    @Test
    public void test_d() throws Exception {
        JPopupMenu popupMenu = manager.newPopupMenu();
        Assert.assertNotNull(popupMenu);
        Assert.assertNotSame(popupMenu, manager.newPopupMenu());
    }


    @Before
    public void setUp() throws Exception {
        manager = new DialogManager();
    }
}
