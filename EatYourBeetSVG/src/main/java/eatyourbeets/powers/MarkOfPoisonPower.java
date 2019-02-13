package eatyourbeets.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class MarkOfPoisonPower extends AnimatorPower
{
    public static final String POWER_ID = CreateFullID(MarkOfPoisonPower.class.getSimpleName());

    public MarkOfPoisonPower(AbstractCreature owner, int stacks)
    {
        super(owner, POWER_ID);

        this.amount = stacks;
        this.type = PowerType.DEBUFF;
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if (info.type != DamageInfo.DamageType.THORNS && damageAmount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new PoisonPower(owner, owner, this.amount), this.amount, AbstractGameAction.AttackEffect.POISON));
        }

        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));

        super.atEndOfTurn(isPlayer);
    }
}
