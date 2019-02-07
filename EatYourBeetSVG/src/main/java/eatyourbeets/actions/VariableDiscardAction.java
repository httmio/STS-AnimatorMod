package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eatyourbeets.AnimatorResources;

import java.util.function.BiConsumer;

public class VariableDiscardAction extends AnimatorAction
{
    private final Object state;
    private final BiConsumer<Object, Integer> onDiscard;

    public VariableDiscardAction(AbstractPlayer player, int discard, Object state, BiConsumer<Object, Integer> onDiscard)
    {
        this.state = state;
        this.onDiscard = onDiscard;
        this.target = player;
        this.amount = discard;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DISCARD;
    }

    public void update()
    {
        AbstractPlayer p = (AbstractPlayer)target;
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (p.hand.size() == 0)
            {
                this.isDone = true;
            }
            else
            {
                String discardMessage = AnimatorResources.GetUIStrings(AnimatorResources.UIStringType.Actions).TEXT[2];
                AbstractDungeon.handCardSelectScreen.open(discardMessage, this.amount, true,true);
                this.tickDuration();
            }

            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            int discarded = 0;
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                p.hand.moveToDiscardPile(card);
                card.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
                AbstractDungeon.player.hand.applyPowers();
                discarded += 1;
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

            onDiscard.accept(state, discarded);
        }

        this.tickDuration();
    }
}
