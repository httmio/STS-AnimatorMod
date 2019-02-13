package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class ExhaustFromDiscardPileAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean random;

    public ExhaustFromDiscardPileAction(int amount, boolean random)
    {
        this.random = random;
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update()
    {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED)
        {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var5 = this.p.discardPile.group.iterator();

            AbstractCard card2;
            while (var5.hasNext())
            {
                card2 = (AbstractCard) var5.next();
                tmp.addToRandomSpot(card2);
            }

            if (tmp.size() == 0)
            {
                this.isDone = true;
            }
            else if (tmp.size() <= this.amount)
            {
                for (int i = 0; i < tmp.size(); ++i)
                {
                    card = tmp.getNCardFromTop(i);
                    AbstractDungeon.player.discardPile.moveToExhaustPile(card);
                }

                this.isDone = true;
            }
            else
            {
                if (this.random)
                {
                    for (int i = 0; i < this.amount; i++)
                    {
                        card = tmp.getRandomCard(true);
                        tmp.removeCard(card);

                        AbstractDungeon.player.discardPile.moveToExhaustPile(card);
                    }

                    this.p.hand.applyPowers();
                }
                else if (this.amount == 1)
                {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
                }
                else
                {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[1], false);
                }

                this.tickDuration();
            }
        }
        else
        {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
            {

                for (AbstractCard abstractCard : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    card = abstractCard;
                    card.unhover();
                    AbstractDungeon.player.discardPile.moveToExhaustPile(card);

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}
