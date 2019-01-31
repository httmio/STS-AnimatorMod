package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.ShimakazeAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Shimakaze extends AnimatorCard
{
    public static final String ID = CreateFullID(Shimakaze.class.getSimpleName());

    public Shimakaze()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);

        Initialize(3,0, 3);

        SetSynergy(Synergies.Kancolle);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        int size = AbstractDungeon.player.drawPile.size();
        for (int i = 0; i < this.magicNumber; i++)
        {
            if (size == i)
            {
                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
            }

            AbstractDungeon.actionManager.addToBottom(new ShimakazeAction(p, m, this.damage));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        }
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