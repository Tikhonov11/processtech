package ru.runa.wf.web.ftl.component;

import org.junit.Assert;
import org.junit.Test;

import static ru.runa.wf.web.ftl.component.EditUserTypeList.convertHtmlComponent;

public class EditUserTypeListTest {

    @Test
    public void shouldChangeDoubleQuotesToSingleQuote() {
        String originalText = "Some text \"";
        Assert.assertEquals(convertHtmlComponent(originalText), originalText.replaceAll("\"", "'"));
    }

    @Test
    public void shouldChangeSquareBracketsToCurlyBracket() {
        String originalText = "Some text:[]";
        Assert.assertEquals(convertHtmlComponent(originalText), originalText.replace("[]", "{}"));
    }

    @Test
    public void shouldChangeNewLineToNothing() {
        String originalText = "Some text \n";
        Assert.assertEquals(convertHtmlComponent(originalText), originalText.replaceAll("\n", ""));
    }
}
