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
/**
 * Represents a tag with attributes.
 */
public class DefaultStatement implements Statement {
    private String tagName;
    private AttributeList attributes;


    public DefaultStatement(String tagName, AttributeList attributes) {
        if (tagName == null || attributes == null) {
            throw new IllegalArgumentException();
        }
        this.tagName = tagName;
        this.attributes = attributes;
    }


    public String toXml() {
        return "<" + tagName + buildAttributes() + "/>";
    }


    public boolean isEquivalentTo(Statement stmt) {
        if (this == stmt) {
            return true;
        }
        if (!(stmt instanceof DefaultStatement)) {
            return false;
        }

        final DefaultStatement defaultStatement = (DefaultStatement)stmt;

        if (!attributes.equals(defaultStatement.attributes)) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (!tagName.equals(defaultStatement.tagName)) {
            return false;
        }

        return true;
    }


    private String buildAttributes() {
        StringBuilder buffer = new StringBuilder();

        for (Iterator iterator = attributes.iterator(); iterator.hasNext(); ) {
            Attribute entry = (Attribute)iterator.next();
            buffer.append(" ").append(entry.getName()).append("=\"")
                  .append(convertToXml(entry.getValue().toString())).append("\"");
        }

        return buffer.toString();
    }


    String convertToXml(String query) {
        StringBuffer sb = new StringBuffer(query);
        replace(sb, "&", "&amp;");
        replace(sb, "\"", "&qote;");
        replace(sb, "<", "&lt;");
        replace(sb, ">", "&gt;");
        replace(sb, "'", "&apos;");
        return sb.toString();
    }


    void replace(StringBuffer sb, String oldStr, String newStr) {
        int startIndex = sb.toString().indexOf(oldStr);
        while (startIndex != -1) {
            sb.replace(startIndex, startIndex + oldStr.length(), newStr);
            startIndex = sb.toString().indexOf(oldStr, startIndex + 1);
        }
    }
}
