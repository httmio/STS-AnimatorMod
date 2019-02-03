package patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

@SpirePatch(clz= CharacterSelectScreen.class, method="render")
public class CharacterSelectScreenPatch_Render
{
    @SpirePostfixPatch
    public static void Postfix(CharacterSelectScreen __instance, SpriteBatch sb)
    {
        CharacterSelectScreenPatch.Render(__instance, sb);
    }
}