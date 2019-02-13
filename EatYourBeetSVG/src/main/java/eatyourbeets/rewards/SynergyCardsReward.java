package eatyourbeets.rewards;

import basemod.BaseMod;
import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardSave;
import eatyourbeets.AnimatorResources;
import eatyourbeets.CustomAbstractDungeon;
import eatyourbeets.Utilities;
import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import patches.AbstractEnums;

import java.util.ArrayList;

public class SynergyCardsReward extends AnimatorReward
{
    public static final String ID = CreateFullID(SynergyCardsReward.class.getSimpleName());

    public final Synergy synergy;
    private boolean skip = false;

    public SynergyCardsReward(Synergy synergy)
    {
        super(ID,"#y" + synergy.NAME.replace(" ", " #y"), AbstractEnums.Rewards.SYNERGY_CARDS);

        this.synergy = synergy;
        this.cards = CustomAbstractDungeon.getRewardCards(synergy);
    }

    @Override
    public void update()
    {
        super.update();

        if (this.hb.hovered)
        {
            TipHelper.renderGenericTip(360.0F * Settings.scale, (float) InputHelper.mY, synergy.NAME,
                    AnimatorResources.GetUIStrings(AnimatorResources.UIStringType.Rewards).TEXT[0]);
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

    public static class Serializer implements BaseMod.LoadCustomReward, BaseMod.SaveCustomReward
    {
        @Override
        public CustomReward onLoad(RewardSave rewardSave)
        {
            return new SynergyCardsReward(Synergies.GetByID(rewardSave.amount));
        }

        @Override
        public RewardSave onSave(CustomReward customReward)
        {
            return new RewardSave(customReward.type.toString(), null, ((SynergyCardsReward) customReward).synergy.ID, 0);
        }
    }
}