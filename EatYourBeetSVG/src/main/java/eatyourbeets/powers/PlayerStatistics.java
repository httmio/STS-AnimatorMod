package eatyourbeets.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eatyourbeets.subscribers.OnEndOfTurnSubscriber;

import java.util.ArrayList;

public class PlayerStatistics extends AnimatorPower implements InvisiblePower, OnCardDrawPower
{
    public static ArrayList<OnEndOfTurnSubscriber> onEndOfTurnSubscribers = new ArrayList<>();

    private static int turnCount = 0;
    private static int cardsDrawnThisTurn = 0;

    public PlayerStatistics(AbstractPlayer owner)
    {
        super(owner, "PlayerStatistics");
    }

    public static int getCardsDrawnThisTurn()
    {
        return cardsDrawnThisTurn;
    }

    public static int getTurnCount()
    {
        return turnCount;
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
        turnCount += 1;
    }

    @Override
    public void onCardDraw(AbstractCard abstractCard)
    {
        super.onDrawOrDiscard();
        cardsDrawnThisTurn += 1;
    }

    @Override
    public void onRemove()
    {
        super.onRemove();

        if (AbstractDungeon.getCurrRoom()!=null && !AbstractDungeon.getCurrRoom().isBattleOver)
        {
            AbstractDungeon.player.powers.add(new PlayerStatistics(AbstractDungeon.player));
        }
    }

    @Override
    public void atEndOfRound()
    {
        super.atEndOfRound();
        cardsDrawnThisTurn = 0;
        turnCount = 0;
    }

    @Override
    public void onVictory()
    {
        super.onVictory();
        cardsDrawnThisTurn = 0;
        turnCount = 0;
    }
}
