package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.ModifyDamagePermanentlyAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Megumin extends AnimatorCard
{
    public static final String ID = CreateFullID(Megumin.class.getSimpleName());
    private static final int ORIGINAL_DAMAGE = 14;

    public Megumin()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);

        this.misc = ORIGINAL_DAMAGE;
        Initialize(ORIGINAL_DAMAGE, 0, 2);

        this.isMultiDamage = true;
        this.exhaust = true;
        
        SetSynergy(Synergies.Konosuba);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));

        if (HasActiveSynergy())
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyDamagePermanentlyAction(this.uuid, this.magicNumber));
        }
    }

    public void applyPowers() 
    {
        this.baseDamage = this.misc;
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {          
            this.initializeDescription();
            upgradeDamage(4);
            upgradeMagicNumber(1);
        }
    }
}