package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.metrics.MetricData;
import eatyourbeets.cards.animator.Gilgamesh;

@SpirePatch(clz= MetricData.class, method="addRelicObtainData")
public class MetricDataPatch
{
    @SpirePrefixPatch
    public static void Prefix(MetricData __instance)
    {
        Gilgamesh.OnRelicReceived();
    }
}