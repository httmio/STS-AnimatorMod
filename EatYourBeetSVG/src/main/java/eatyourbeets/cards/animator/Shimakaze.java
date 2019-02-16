package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

import java.util.ArrayList;

public class Shimakaze extends AnimatorCard
{
    public static final String ID = CreateFullID(Shimakaze.class.getSimpleName());

    public Shimakaze()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        Initialize(3,0, 3);

        SetSynergy(Synergies.Kancolle);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        GameActionsHelper.DrawCard(p, this.magicNumber, this::OnCardDrawn, m);

//        int size = AbstractDungeon.player.drawPile.size();
//        for (int i = 0; i < this.magicNumber; i++)
//        {
//            if (size == i)
//            {
//                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
//            }
//
//            AbstractDungeon.actionManager.addToBottom(new ShimakazeAction(p, m, this.damage));
//            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
//        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(1);
        }
    }

    private void OnCardDrawn(Object context, ArrayList<AbstractCard> cards)
    {
        AbstractMonster m = Utilities.SafeCast(context, AbstractMonster.class);

        if (m != null && cards.size() > 0)
        {
            AbstractPlayer p = AbstractDungeon.player;
            for (AbstractCard c : cards)
            {
                if (c.type == CardType.ATTACK)
                {
                    GameActionsHelper.DamageTarget(p, m, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                }
            }
        }
    }
}