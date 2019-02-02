package eatyourbeets.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eatyourbeets.Utilities;

import java.util.ArrayList;

public class AishaAction extends AnimatorAction
{
    private final int costReduction;
    private final AbstractPlayer p;

    public AishaAction(int costReduction)
    {
        this.costReduction = costReduction;
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE)
        {
            this.p.createHandIsFullDialog();
        }
        else
        {
            CardGroup skills = this.p.drawPile.getSkills();
            if (skills.size() > 0)
            {
                AbstractCard card;
                ArrayList<AbstractCard> priorityCards = Utilities.Where(skills.group, (c) -> (c.cost > 0));
                if (priorityCards.size() > 0)
                {
                    card = Utilities.GetRandomElement(priorityCards);
                }
                else
                {
                    card = skills.getRandomCard(AbstractDungeon.cardRandomRng);
                }

                if (card == null)
                {
                    this.isDone=true;
                    return;
                }

                AbstractDungeon.actionManager.addToBottom(new ReduceCostAction(card.uuid, costReduction));
                AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardAction(card));

                //this.p.hand.addToHand(card);
                //card.lighten(false);
                //this.p.drawPile.removeCard(card);
                //this.p.hand.refreshHandLayout();
            }
        }

        this.isDone = true;
    }
}
