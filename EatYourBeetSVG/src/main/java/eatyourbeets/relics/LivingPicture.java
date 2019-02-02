package eatyourbeets.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;

public class LivingPicture extends AnimatorRelic
{
    public static final String ID = "Animator_LivingPicture";

    private Boolean active = true;

    public LivingPicture()
    {
        super(ID, new Texture("images/relics/animator_livingPicture.png"), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new LivingPicture();
    }

    //@Override
    //public void onVictory()
    //{
    //    RewardItem reward1 = new RewardItem(AbstractDungeon.player.getCardColor());
    //    //RewardItem reward2 = new RewardItem(AbstractDungeon.player.getCardColor());
    //    //reward1.relicLink = reward2;
    //    //reward2.relicLink = reward1;
    //    AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(reward1, RewardItem.RewardType.CARD));
    //    super.onVictory();
    //}

    @Override
    public void atTurnStart()
    {
        super.atTurnStart();
        active = true;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m)
    {
        super.onPlayCard(c, m);

        AnimatorCard card = Utilities.SafeCast(c, AnimatorCard.class);
        if (active && card != null && card.HasActiveSynergy())
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            active = false;
            this.flash();
        }
    }
}