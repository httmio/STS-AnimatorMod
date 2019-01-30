package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.StephanieAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

import java.util.ArrayList;

public class DolaStephanie extends AnimatorCard
{
    public static final String ID = CreateFullID(DolaStephanie.class.getSimpleName());

    public DolaStephanie()
    {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        Initialize(0,0);

        AddSynergy(Synergies.NoGameNoLife);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new StephanieAction(p, 1));
        //ArrayList<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        //for (int i = 0; i < cards.size() - 1; i++)
        //{
        //    if (cards.get(i).type == CardType.SKILL)
        //    {
        //        return;
        //    }
        //}
        //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            this.isInnate = true;
        }
    }
}