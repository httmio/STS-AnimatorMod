package eatyourbeets;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SuppressWarnings("UnusedReturnValue")
public class GameActionsHelper
{
    public static void Special(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static GainBlockAction GainBlock(AbstractCreature source, int amount)
    {
        GainBlockAction action = new GainBlockAction(source, source, amount);
        AbstractDungeon.actionManager.addToBottom(action);
        return action;
    }

    public static DamageAction DealDamage(AbstractCreature source, AbstractCreature target, int amount, DamageInfo.DamageType damageType)
    {
        DamageAction action = new DamageAction(target, new DamageInfo(source, amount, damageType));
        AbstractDungeon.actionManager.addToBottom(action);
        return action;
    }

    public static DrawCardAction DrawCard(AbstractCreature source, int amount)
    {
        DrawCardAction action = new DrawCardAction(source, amount);
        AbstractDungeon.actionManager.addToBottom(action);
        return action;
    }

    public static GainEnergyAction GainEnergy(int amount)
    {
        GainEnergyAction action = new GainEnergyAction(amount);
        AbstractDungeon.actionManager.addToBottom(action);
        return action;
    }

    public static ApplyPowerAction ApplyPower(AbstractCreature source, AbstractCreature target, AbstractPower power)
    {
        ApplyPowerAction action = new ApplyPowerAction(target, source, power, power.amount);
        AbstractDungeon.actionManager.addToBottom(action);
        return action;
    }

    public static ApplyPowerAction ApplyPower(AbstractCreature source, AbstractCreature target, AbstractPower power, int stacks)
    {
        ApplyPowerAction action = new ApplyPowerAction(target, source, power, stacks);
        AbstractDungeon.actionManager.addToBottom(action);
        return action;
    }
}
