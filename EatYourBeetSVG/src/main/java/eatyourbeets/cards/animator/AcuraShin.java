package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;
import eatyourbeets.powers.PlayerStatistics;
import eatyourbeets.powers.PoisonAffinityPower;

public class AcuraShin extends AnimatorCard
{
    public static final String ID = CreateFullID(AcuraShin.class.getSimpleName());

    public AcuraShin()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);

        Initialize(1,0, 3);

        this.secondaryValue = this.baseSecondaryValue = 1;
        this.isMultiDamage = true;

        AddExtendedDescription();

        SetSynergy(Synergies.Chaika);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        GameActionsHelper.DamageAllEnemies(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.POISON);
        for (AbstractMonster m1 : PlayerStatistics.GetCurrentEnemies(true))
        {
            GameActionsHelper.ApplyPower(p, m1, new PoisonPower(m1, p, this.magicNumber), this.magicNumber);
        }

        GameActionsHelper.ApplyPower(p, p, new PoisonAffinityPower(p, this.secondaryValue), this.secondaryValue);
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(3);
        }
    }
}