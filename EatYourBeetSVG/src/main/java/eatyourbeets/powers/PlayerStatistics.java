package eatyourbeets.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import eatyourbeets.Utilities;
import eatyourbeets.subscribers.OnBattleStartSubscriber;
import eatyourbeets.subscribers.OnEndOfTurnSubscriber;
import eatyourbeets.subscribers.OnLoseHpSubscriber;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ListIterator;

public class PlayerStatistics extends AnimatorPower implements InvisiblePower, OnCardDrawPower
{
    public static ArrayList<OnBattleStartSubscriber> onBattleStartSubscribers = new ArrayList<>();
    public static ArrayList<OnEndOfTurnSubscriber> onEndOfTurnSubscribers = new ArrayList<>();
    public static ArrayList<OnLoseHpSubscriber> onLoseHpSubscribers = new ArrayList<>();

    private static int turnCount = 0;
    private static int cardsDrawnThisTurn = 0;

    public PlayerStatistics(AbstractPlayer owner)
    {
        super(owner, "PlayerStatistics");
    }

    public void OnBattleStart()
    {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            OnBattleStartSubscriber s = Utilities.SafeCast(c, OnBattleStartSubscriber.class);
            if (s != null)
            {
                onBattleStartSubscribers.add(s);
                s.OnBattleStart();
            }
        }
    }

    public static boolean InBattle()
    {
        MapRoomNode mapNode = AbstractDungeon.currMapNode;
        if (mapNode == null)
        {
            return false;
        }
        else
        {
            AbstractRoom room = mapNode.getRoom();

            return room != null && !room.isBattleOver && room.monsters != null;
        }

    }

    public static int getCardsDrawnThisTurn()
    {
        if(!InBattle() && cardsDrawnThisTurn != 0)
        {
            ClearStats();
        }

        return cardsDrawnThisTurn;
    }

    public static int getTurnCount()
    {
        if(!InBattle() && turnCount != 0)
        {
            ClearStats();
        }

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
    public void onDeath()
    {
        super.onDeath();
        ClearStats();
    }

    @Override
    public void onVictory()
    {
        super.onVictory();
        ClearStats();
    }

    @Override
    public int onLoseHp(int damageAmount)
    {
        int damage = damageAmount;
        if (onLoseHpSubscribers.size() > 0)
        {
            ArrayList<OnLoseHpSubscriber> temp = new ArrayList<>(onLoseHpSubscribers);
            for (OnLoseHpSubscriber s : temp)
            {
                damage = s.OnLoseHp(damage);
            }
        }

        return super.onLoseHp(damage);
    }

    private static void ClearStats()
    {
        logger.info("Clearing Player Stats");

        cardsDrawnThisTurn = 0;
        turnCount = 0;
        onLoseHpSubscribers.clear();
        onLoseHpSubscribers.clear();
        onEndOfTurnSubscribers.clear();
    }
}
