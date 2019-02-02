package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicButton;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import eatyourbeets.cards.animator.Gilgamesh;
import eatyourbeets.relics.TheMissingPiece;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

@SpirePatch(clz= MetricData.class, method="addRelicObtainData")
public class MetricDataPatch
{
    //private static final Logger logger = LogManager.getLogger(MetricDataPatch.class.getName());

    @SpirePrefixPatch
    public static void Prefix(MetricData __instance)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (player != null && player.masterDeck != null)
        {
            ArrayList<AbstractCard> deck = player.masterDeck.group;
            if (deck != null && deck.size() > 0)
            {
                boolean effectPlayed = false;
                for (AbstractCard c : deck)
                {
                    if (c.cardID.equals(Gilgamesh.ID))
                    {
                        c.upgrade();
                        AbstractDungeon.player.bottledCardUpgradeCheck(c);
                        if (!effectPlayed)
                        {
                            effectPlayed = true;
                            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 4.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), (float) Settings.WIDTH / 4.0F, (float) Settings.HEIGHT / 2.0F));
                        }
                        AbstractDungeon.player.gainGold(Gilgamesh.GOLD_REWARD);
                    }
                }
            }
        }
    }
}