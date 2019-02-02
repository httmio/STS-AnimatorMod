package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;
import eatyourbeets.powers.PlayerStatistics;

public class ChlammyZell extends AnimatorCard
{
    public static final String ID = CreateFullID(ChlammyZell.class.getSimpleName());

    public ChlammyZell()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        Initialize(0,0,1);

        secondaryValue = baseSecondaryValue = 0;

        SetSynergy(Synergies.NoGameNoLife);
    }

    @Override
    public void update()
    {
        int cardsDrawn = PlayerStatistics.getCardsDrawnThisTurn();
        if (secondaryValue != cardsDrawn)
        {
            secondaryValue = cardsDrawn;
            isSecondaryValueModified = true;
        }

        super.update();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        secondaryValue = PlayerStatistics.getCardsDrawnThisTurn();
        this.baseDamage = secondaryValue * this.magicNumber;

        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(1);
        }
    }
}