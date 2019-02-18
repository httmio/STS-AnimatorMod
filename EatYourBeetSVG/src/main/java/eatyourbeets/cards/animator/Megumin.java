package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Megumin extends AnimatorCard//_SavableInteger implements CustomSavable<Integer>
{
    public static final String ID = CreateFullID(Megumin.class.getSimpleName());
    private static final int ORIGINAL_DAMAGE = 14;

    public Megumin()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        Initialize(ORIGINAL_DAMAGE, 0);

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
            for (AbstractCard c : GetAllInstances())
            {
                Megumin megumin = Utilities.SafeCast(c, Megumin.class);
                if (megumin != null)
                {
                    megumin.upgrade();
//                    megumin.secondaryValue += this.magicNumber;
//                    megumin.baseSecondaryValue = megumin.secondaryValue;
//                    megumin.applyPowers();
                }
            }
        }
    }

//    public void applyPowers()
//    {
//        this.baseDamage = ORIGINAL_DAMAGE + this.secondaryValue;
//        super.applyPowers();
//        initializeDescription();
//    }

    @Override
    public boolean canUpgrade()
    {
        return true;
    }

    @Override
    public void upgrade()
    {
        this.timesUpgraded += 1;
        this.baseDamage = ORIGINAL_DAMAGE + (timesUpgraded * 2) + (timesUpgraded / 2);
        this.upgradedDamage = true;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

//    @Override
//    protected void SetValue(Integer integer)
//    {
//        super.SetValue(integer);
//        this.baseDamage = ORIGINAL_DAMAGE + this.secondaryValue;
//    }
}