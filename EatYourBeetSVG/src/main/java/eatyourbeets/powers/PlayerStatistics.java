package eatyourbeets.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.subscribers.*;

public class PlayerStatistics extends AnimatorPower implements InvisiblePower, OnCardDrawPower
{
    public static final String POWER_ID = "PlayerStatistics";

    public static final PlayerStatistics Instance = new PlayerStatistics();

    public static final GameEvent<OnApplyPowerSubscriber> onApplyPower = new GameEvent<>();
    public static final GameEvent<OnBattleStartSubscriber> onBattleStart = new GameEvent<>();
    public static final GameEvent<OnEndOfTurnSubscriber> onEndOfTurn = new GameEvent<>();
    public static final GameEvent<OnLoseHpSubscriber> onLoseHp = new GameEvent<>();

    private static int turnCount = 0;
    private static int cardsDrawnThisTurn = 0;

    protected PlayerStatistics()
    {
        super(null, POWER_ID);
    }

    private static void ClearStats()
    {
        logger.info("Clearing Player Stats");

        AnimatorCard.SetLastCardPlayed(null);
        cardsDrawnThisTurn = 0;
        turnCount = 0;
        onLoseHp.Clear();
        onLoseHp.Clear();
        onEndOfTurn.Clear();
        onApplyPower.Clear();
    }

    public static void EnsurePowerIsApplied()
    {
        if (!AbstractDungeon.player.powers.contains(Instance))
        {
            logger.info("Applied PlayerStatistics");

            AbstractDungeon.player.powers.add(Instance);
        }
    }

    public void OnBattleStart()
    {
        ClearStats();
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            OnBattleStartSubscriber s = Utilities.SafeCast(c, OnBattleStartSubscriber.class);
            if (s != null)
            {
                onBattleStart.Subscribe(s);
                s.OnBattleStart();
            }
        }
    }

    public static AbstractRoom CurrentRoom()
    {
        MapRoomNode mapNode = AbstractDungeon.currMapNode;
        if (mapNode == null)
        {
            return null;
        }
        else
        {
            return mapNode.getRoom();
        }
    }

    public static boolean InBattle()
    {
        AbstractRoom room = CurrentRoom();

        return room != null && !room.isBattleOver && room.monsters != null;
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
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        super.onApplyPower(power, target, source);

        for (OnApplyPowerSubscriber p : onApplyPower.GetSubscribers())
        {
            p.OnApplyPower(power, target, source);
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        super.onAfterUseCard(card, action);

        AnimatorCard.SetLastCardPlayed(card);
    }

    @Override
    public void updateDescription()
    {
        this.description = "";
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        super.atEndOfTurn(isPlayer);

        cardsDrawnThisTurn = 0;
        turnCount += 1;
        AnimatorCard.SetLastCardPlayed(null);
    }

    @Override
    public void onCardDraw(AbstractCard abstractCard)
    {
        cardsDrawnThisTurn += 1;
    }

    //@Override
    //public void onRemove()
    //{
    //    super.onRemove();
    //    if (AbstractDungeon.getCurrRoom() != null && !AbstractDungeon.getCurrRoom().isBattleOver)
    //    {
    //        if (!AbstractDungeon.player.powers.contains(this))
    //        {
    //            AbstractDungeon.player.powers.add(this);
    //        }
    //    }
    //}

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
        if (onLoseHp.Count() > 0)
        {
            for (OnLoseHpSubscriber s : onLoseHp.GetSubscribers())
            {
                damage = s.OnLoseHp(damage);
            }
        }

        return super.onLoseHp(damage);
    }
}
