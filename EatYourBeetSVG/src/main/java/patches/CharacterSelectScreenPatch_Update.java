package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import eatyourbeets.ui.CharacterSelectScreenPatch;

@SpirePatch(clz= CharacterSelectScreen.class, method="update")
public class CharacterSelectScreenPatch_Update
{
    @SpirePostfixPatch
    public static void Postfix(CharacterSelectScreen __instance)
    {
        CharacterSelectScreenPatch.Update(__instance);
    }
}