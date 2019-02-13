package eatyourbeets.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eatyourbeets.AnimatorResources;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;

public class StephanieAction extends AnimatorAction
{
    private final AbstractPlayer player;
    private final int cardDraw;

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
                String fetchMessage = AnimatorResources.GetUIStrings(AnimatorResources.UIStringType.Actions).TEXT[0];
                AbstractDungeon.handCardSelectScreen.open(fetchMessage, 1, false, false);
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
