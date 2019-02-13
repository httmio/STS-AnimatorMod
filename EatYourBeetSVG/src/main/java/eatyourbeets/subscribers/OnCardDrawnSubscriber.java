package eatyourbeets.subscribers;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnCardDrawnSubscriber
{
    void OnCardDrawn(AbstractCard card);
}
