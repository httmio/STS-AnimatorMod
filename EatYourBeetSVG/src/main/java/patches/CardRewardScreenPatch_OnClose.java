package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import eatyourbeets.ui.CardRewardScreenPatch;

import java.util.ArrayList;

@SpirePatch(clz= CardRewardScreen.class, method="onClose")
public class CardRewardScreenPatch_OnClose
{
    @SpirePostfixPatch
    public static void Postfix(CardRewardScreen __instance)
    {
        CardRewardScreenPatch.OnClose(__instance);
    }
}