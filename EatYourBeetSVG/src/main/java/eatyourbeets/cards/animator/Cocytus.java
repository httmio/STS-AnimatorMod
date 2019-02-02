package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.OnTargetBlockLostAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Cocytus extends AnimatorCard
{
    public static final String ID = CreateFullID(Cocytus.class.getSimpleName());

    public Cocytus()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);

        Initialize(7,0,4);

        SetSynergy(Synergies.Overlord);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        int damage = this.damage;
        if (HasActiveSynergy())
        {
            damage += this.magicNumber;
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        AbstractDungeon.actionManager.addToBottom(new OnTargetBlockLostAction(m, new GainBlockAction(p, p, 0)));

        //if (m.currentBlock > 0)
        //{
        //    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, Math.min(m.currentBlock, damage)));
        //}
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {          
            upgradeDamage(3);
            upgradeMagicNumber(2);
        }
    }
}