package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Konayuki extends AnimatorCard
{
    public static final String ID = CreateFullID(Konayuki.class.getSimpleName());
    private long lastUpdate = 0;

    public Konayuki()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);

        Initialize(30,0, 2);

        AddSynergy(Synergies.Katanagatari);
    }

    @Override
    public boolean canPlay(AbstractCard card)
    {
        boolean canPlay = super.canPlay(card);

        if (canPlay)
        {
            long currentTime = System.currentTimeMillis();
            if ((lastUpdate + 600) < currentTime)
            {
                lastUpdate = currentTime;
                if (EnoughStrength())
                {
                    this.target = CardTarget.ENEMY;
                }
                else
                {
                    this.target = CardTarget.SELF;
                }
            }
        }

        return canPlay;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        if (EnoughStrength())
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        else
        {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {          
            upgradeDamage(10);
            upgradeMagicNumber(1);
        }
    }

    private boolean EnoughStrength()
    {
        StrengthPower strength = Utilities.SafeCast(AbstractDungeon.player.getPower(StrengthPower.POWER_ID), StrengthPower.class);

        return (strength != null && strength.amount >= 10);
    }
}