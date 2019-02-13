package eatyourbeets.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class FeridBathoryPower extends AnimatorPower
{
    public static final String POWER_ID = CreateFullID(FeridBathoryPower.class.getSimpleName());
    private int charges;

    public FeridBathoryPower(AbstractCreature owner, int amount)
    {
        super(owner, POWER_ID);
        this.amount = amount;
        this.charges = 2;

        updateDescription();
    }

    @Override
    public void updateDescription()
    {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1] + (amount * 2) + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn()
    {
        super.atStartOfTurn();
        this.charges = 2;
    }

    @Override
    public void onExhaust(AbstractCard card)
    {
        super.onExhaust(card);

        if (this.charges > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new StrengthPower(owner, this.amount), this.amount));
            AbstractDungeon.actionManager.addToBottom(new HealAction(owner, owner, this.amount * 2));

            this.flash();
            this.charges -= 1;
        }
    }
}