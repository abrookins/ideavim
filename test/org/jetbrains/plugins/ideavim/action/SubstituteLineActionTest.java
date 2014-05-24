package org.jetbrains.plugins.ideavim.action;

import com.maddyhome.idea.vim.command.CommandState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.ideavim.VimTestCase;

import static com.maddyhome.idea.vim.command.CommandState.Mode.INSERT;
import static com.maddyhome.idea.vim.helper.StringHelper.parseKeys;

/**
 * @author abrookins
 */
public class SubstituteLineActionTest extends VimTestCase {
  // VIM-636
  public void testSubstituteLineClearsEmptyLine() {
    myFixture.configureByText("a.txt", "<caret>");
    typeText(parseKeys("<S-s>"));
    myFixture.checkResult("<caret>");
    assertMode(INSERT);
  }

  public void testSubstituteLineClearsOneLine() {
    myFixture.configureByText("a.txt", "<caret>Hello, world!");
    typeText(parseKeys("<S-s>"));
    myFixture.checkResult("");
    assertMode(INSERT);
  }

  // VIM-636
  public void testSubstituteLineClearsLineTwoLineFile() {
    myFixture.configureByText("a.txt", "<caret>Hello, world!\nAnother line\n");
    typeText(parseKeys("<S-s>"));
    myFixture.checkResult("<caret>\nAnother line\n");
    assertMode(INSERT);
  }

  // VIM-636
  public void testSubstituteLineClearsLineFourLineFile() {
    myFixture.configureByText("a.txt", "Hello, world!\n<caret>Another line\nYet Another Line\n");
    typeText(parseKeys("<S-s>"));
    myFixture.checkResult("Hello, world!\n<caret>\nYet Another Line\n");
    assertMode(INSERT);
  }

  // VIM-636
  public void testSubstituteLineClearsLineFourBlankLines() {
    myFixture.configureByText("a.txt", "\n<caret>\n\n");
    typeText(parseKeys("<S-s>"));
    myFixture.checkResult("\n<caret>\n\n");
    assertMode(INSERT);
  }

  public void assertMode(@NotNull CommandState.Mode expectedMode) {
    final CommandState.Mode mode = CommandState.getInstance(myFixture.getEditor()).getMode();
    assertEquals(expectedMode, mode);
  }
}

