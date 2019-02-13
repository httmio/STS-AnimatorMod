package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import eatyourbeets.ui.CharacterSelectScreenPatch;

@SpirePatch(clz= CharacterSelectScreen.class, method="initialize")
public class CharacterSelectScreenPatch_Initialize
{
    @SpirePostfixPatch
    public static void Initialize(CharacterSelectScreen __instance)
    {
        CharacterSelectScreenPatch.Initialize(__instance);
    }
}