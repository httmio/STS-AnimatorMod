package eatyourbeets.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class PlayerStatistics extends AnimatorPower implements InvisiblePower, OnCardDrawPower
{
    private static int cardsDrawnThisTurn = 0;

    public PlayerStatistics(AbstractPlayer owner)
    {
        super(owner, "PlayerStatistics");
    }

    public static int getCardsDrawnThisTurn()
    {
        return cardsDrawnThisTurn;
    }

    @Override
    public void updateDescription()
    {
        this.description = "";
    }

    @Override
    public void atStartOfTurn()
    {
        super.atStartOfTurn();
        cardsDrawnThisTurn = 0;
    }

    @Override
    public void onCardDraw(AbstractCard abstractCard)
    {
        super.onDrawOrDiscard();
        cardsDrawnThisTurn += 1;
    }

    @Override
    public void atEndOfRound()
    {
        super.atEndOfRound();
        cardsDrawnThisTurn = 0;
    }
}
