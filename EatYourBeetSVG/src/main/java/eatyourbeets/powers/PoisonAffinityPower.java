package eatyourbeets.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import eatyourbeets.Utilities;

public class PoisonAffinityPower extends AnimatorPower
{
    public static final String POWER_ID = CreateFullID(PoisonAffinityPower.class.getSimpleName());

    public PoisonAffinityPower(AbstractCreature owner, int stacks)
    {
        super(owner, POWER_ID);

        this.amount = stacks;
        this.type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if (source != null && source.isPlayer)
        {
            if (power.ID.equals(PoisonPower.POWER_ID))
            {
                ApplyPowerAction applyPowerAction = Utilities.SafeCast(AbstractDungeon.actionManager.currentAction, ApplyPowerAction.class);
                if (applyPowerAction != null)
                {
                    applyPowerAction.amount += this.amount;
                }
                power.amount += this.amount;
            }
        }

        super.onApplyPower(power, target, source);
    }
}
