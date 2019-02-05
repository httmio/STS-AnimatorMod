package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Excalibur extends AnimatorCard
{
    public static final String ID = CreateFullID(Excalibur.class.getSimpleName());

    public Excalibur()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);

        Initialize(12,0);

        this.isMultiDamage = true;
        SetSynergy(Synergies.Fate);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));

        int damageUpgrade = 0;
        for (AbstractCard c : p.hand.group)
        {
            if (c.type == CardType.ATTACK && c != this)
            {
                damageUpgrade += c.baseDamage;
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand, true));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(this.uuid, damageUpgrade));
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeDamage(4);
            upgradeBaseCost(1);
        }
    }
}