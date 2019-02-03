package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(clz= CharacterSelectScreen.class, method="update")
public class CharacterSelectScreenPatch_Update
{
    @SpirePostfixPatch
    public static void Postfix(CharacterSelectScreen __instance)
    {
        CharacterSelectScreenPatch.Update(__instance);
    }
}