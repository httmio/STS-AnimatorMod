package eatyourbeets.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import eatyourbeets.CustomAbstractDungeon;
import eatyourbeets.Utilities;
import patches.RewardTypeEnum;

import java.util.ArrayList;

public class SynergyCardsReward extends CustomReward
{
    private static final Texture ICON = new Texture("images/ui/rewards/animator_synergyCardReward.png");

    public String synergy;
    private boolean skip = false;

    public SynergyCardsReward(String synergy)
    {
        super(ICON,"#y" + synergy.replace(" ", " #y"), RewardTypeEnum.SYNERGY_CARDS);

        this.synergy = synergy;
        this.cards = CustomAbstractDungeon.getRewardCards(synergy);
    }

    @Override
    public void update()
    {
        super.update();

        if (this.hb.hovered)
        {
            TipHelper.renderGenericTip(360.0F * Settings.scale, (float) InputHelper.mY, synergy,
                    "Only contains cards with this synergy. WARNING: once you click on this, the other 2 card rewards will disappear.");
        }
    }

    @Override
    public boolean claimReward()
    {
        if (skip)
        {
            return true;
        }

        ArrayList<RewardItem> rewards = AbstractDungeon.combatRewardScreen.rewards;
        int i = 0;
        while (i < rewards.size())
        {
            SynergyCardsReward other = Utilities.SafeCast(rewards.get(i), SynergyCardsReward.class);
            if (other != null && other != this)
            {
                other.isDone = true;
                other.skip = true;
            }
            i++;
        }

        if (AbstractDungeon.player.hasRelic("Question Card"))
        {
            AbstractDungeon.player.getRelic("Question Card").flash();
        }

        if (AbstractDungeon.player.hasRelic("Busted Crown"))
        {
            AbstractDungeon.player.getRelic("Busted Crown").flash();
        }

        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD)
        {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[4]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }

        this.isDone = false;

        return false;
    }
}

/*

package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicButton;
import eatyourbeets.relics.TheMissingPiece;
import eatyourbeets.rewards.SynergyCardsReward;
import javassist.CtBehavior;

@SpirePatch(clz= CardRewardScreen.class, method="takeReward")
public class CardRewardScreenPatch
{
    @SpireInsertPatch(locator=Locator.class)
    public static void Insert(CardRewardScreen __instance)
    {
        if (__instance.rItem instanceof SynergyCardsReward)
        {
            ((SynergyCardsReward)__instance.rItem).OnRewardTaken();
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            // Insert here to be after the game is saved
            // Avoids weird save/load issues
            //Matcher finalMatcher = new Matcher.MethodCallMatcher(DynamicButton.class, "hide");
            //int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{0};
        }
    }
}


*/