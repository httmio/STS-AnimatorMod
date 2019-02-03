package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import eatyourbeets.cards.animator.Gilgamesh;

import java.util.ArrayList;

@SpirePatch(clz= MetricData.class, method="addRelicObtainData")
public class MetricDataPatch
{
    @SpirePrefixPatch
    public static void Prefix(MetricData __instance)
    {
        Gilgamesh.OnRelicReceived();
    }
}