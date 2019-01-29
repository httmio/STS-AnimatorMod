package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import eatyourbeets.Utilities;

import java.util.ArrayList;

public class DolaRikuAction extends AbstractGameAction
{
    //protected static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(DolaRikuAction.class.getName());

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT;
    private AbstractPlayer player;
    private int addCost;

    public DolaRikuAction(AbstractCreature target, int addCost)
    {
        this.target = target;
        this.addCost = addCost;
        this.player = (AbstractPlayer)target;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.player.hand.size() == 0)
            {
                this.isDone = true;
            }
            else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                this.tickDuration();
            }

            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard selectedCard = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            player.hand.moveToExhaustPile(selectedCard);

            boolean status = selectedCard.type == AbstractCard.CardType.STATUS;
            boolean curse = selectedCard.type == AbstractCard.CardType.CURSE;
            ArrayList<AbstractCard> sameRarity = new ArrayList<>();
            ArrayList<AbstractCard> allCards = CardLibrary.getAllCards();
            for (AbstractCard c : allCards)
            {
                if (c.color == AbstractCard.CardColor.COLORLESS || c.color == AbstractCard.CardColor.CURSE || c.color == selectedCard.color)
                {
                    if (!c.originalName.equals(selectedCard.originalName))
                    {
                        if (c.type == AbstractCard.CardType.CURSE)
                        {
                            if (curse)
                            {
                                sameRarity.add(c.makeCopy());
                            }
                        }
                        else if (c.type == AbstractCard.CardType.STATUS)
                        {
                            if (status)
                            {
                                sameRarity.add(c.makeCopy());
                            }
                        }
                        else if (c.rarity == selectedCard.rarity)
                        {
                            AbstractCard toAdd = c.makeCopy();
                            if (selectedCard.upgraded && toAdd.canUpgrade())
                            {
                                toAdd.upgrade();
                            }
                            sameRarity.add(toAdd);
                        }
                    }
                }
            }

            AbstractCard randomCard = Utilities.GetRandomElement(sameRarity);
            if (randomCard != null)
            {
                randomCard.modifyCostForCombat(addCost);
                player.hand.addToTop(randomCard);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

            this.isDone = true;
        }

        this.tickDuration();
    }
}
