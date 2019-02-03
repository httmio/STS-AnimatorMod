package eatyourbeets.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;

public class PinaCoLadaPower extends AnimatorPower
{
    public static final String POWER_ID = "PinaCoLada";

    public PinaCoLadaPower(AbstractCreature owner, int block)
    {
        super(owner, POWER_ID);
        this.amount = block;

        updateDescription();
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard)
    {
        super.onAfterCardPlayed(usedCard);

        AnimatorCard card = Utilities.SafeCast(usedCard, AnimatorCard.class);
        if (card != null && card.HasActiveSynergy())
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
            this.flash();
        }
    }
}
