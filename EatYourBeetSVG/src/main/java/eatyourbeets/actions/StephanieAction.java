package eatyourbeets.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;

import java.util.ArrayList;
import java.util.function.Predicate;

public class StephanieAction extends AnimatorAction
{
    private AbstractPlayer player;
    private int cardDraw;

    public StephanieAction(AbstractCreature target, int cardDraw)
    {
        this.target = target;
        this.cardDraw = cardDraw;
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
                AbstractDungeon.handCardSelectScreen.open("Fetch cards with the same synergy.", 1, false, false);
                this.tickDuration();
            }

            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard card = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            AbstractDungeon.player.hand.addToTop(card);

            AnimatorCard selectedCard = Utilities.SafeCast(card, AnimatorCard.class);
            if (selectedCard == null)
            {
                this.isDone = true;
                return;
            }

            AbstractDungeon.actionManager.addToBottom(new FetchAction(player.drawPile, selectedCard::HasSynergy, cardDraw));
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }
}
