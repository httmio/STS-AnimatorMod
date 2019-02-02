package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicButton;
import eatyourbeets.relics.TheMissingPiece;
import javassist.CtBehavior;

@SpirePatch(clz= CombatRewardScreen.class, method="setupItemReward")
public class CombatRewardScreenPatch
{
    @SpireInsertPatch(locator=Locator.class)
    public static void Insert(CombatRewardScreen __instance)
    {
        TheMissingPiece relic = (TheMissingPiece) AbstractDungeon.player.getRelic(TheMissingPiece.ID);
        if (relic != null)
        {
            relic.receiveRewards(__instance.rewards);
            //relic.onTrigger();
            // Re-setup the rewards screen to our changed rewards
            //AbstractDungeon.combatRewardScreen.setupItemReward();
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            // Insert here to be after the game is saved
            // Avoids weird save/load issues
            Matcher finalMatcher = new Matcher.MethodCallMatcher(DynamicButton.class, "hide");
            int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{found[0]};
        }
    }
}