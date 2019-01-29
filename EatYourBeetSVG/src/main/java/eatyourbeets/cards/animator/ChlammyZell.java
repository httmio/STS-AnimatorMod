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
    private int cardsDrawn = 0;

    public ChlammyZell()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        Initialize(0,0,1);

        AddSynergy(Synergies.NoGameNoLife);
    }

    @Override
    public void update()
    {
        int c = PlayerStatistics.getCardsDrawnThisTurn();
        if (cardsDrawn != c)
        {
            this.baseDamage = Math.max(0, c) * this.baseMagicNumber;
            this.isDamageModified = baseDamage > 0;
            cardsDrawn = c;
        }

        super.update();
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