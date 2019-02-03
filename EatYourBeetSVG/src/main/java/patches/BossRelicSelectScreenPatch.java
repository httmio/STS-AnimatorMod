package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import eatyourbeets.cards.animator.Gilgamesh;

@SpirePatch(clz= BossRelicSelectScreen.class, method="relicObtainLogic")
public class BossRelicSelectScreenPatch
{
    @SpirePrefixPatch
    public static void Prefix(BossRelicSelectScreen __instance)
    {
        Gilgamesh.OnRelicReceived();
    }
}
