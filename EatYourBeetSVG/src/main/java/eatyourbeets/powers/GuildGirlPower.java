package eatyourbeets.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import eatyourbeets.GameActionsHelper;

public class GuildGirlPower extends AnimatorPower
{
    public static final String POWER_ID = CreateFullID(GuildGirlPower.class.getSimpleName());

    public GuildGirlPower(AbstractCreature owner, int amount)
    {
        super(owner, POWER_ID);
        this.amount = amount;

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        super.atStartOfTurnPostDraw();

        GameActionsHelper.CycleCardAction(this.amount);
    }
}
