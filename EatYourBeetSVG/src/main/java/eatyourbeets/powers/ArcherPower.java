package eatyourbeets.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;

public class ArcherPower extends AnimatorPower
{
    private int baseDamage;

    public ArcherPower(AbstractCreature owner, int damage)
    {
        super(owner, "Archer");
        this.baseDamage = damage;
        this.amount = damage + GetPlayerStrength();

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        this.baseDamage += stackAmount;
        this.amount = baseDamage + GetPlayerStrength();

        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        super.onApplyPower(power, target, source);

        if (target.isPlayer)
        {
            this.amount = baseDamage + GetPlayerStrength();

            updateDescription();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        super.atEndOfTurn(isPlayer);

        if (isPlayer)
        {
            this.amount = baseDamage + GetPlayerStrength();

            updateDescription();

            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (!m.isDead && !m.isDying && !m.escaped)
                {
                    for (AbstractPower p : m.powers)
                    {
                        if (p.type == PowerType.DEBUFF)
                        {
                            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.THORNS)));
                        }
                    }
                }
            }
        }
    }

    private int GetPlayerStrength()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasPower(StrengthPower.POWER_ID))
        {
            return player.getPower(StrengthPower.POWER_ID).amount;
        }
        return 0;
    }
}
