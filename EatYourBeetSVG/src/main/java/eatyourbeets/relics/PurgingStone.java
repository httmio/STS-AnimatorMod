package eatyourbeets.relics;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import eatyourbeets.Utilities;
import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.rewards.SynergyCardsReward;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;

public class PurgingStone extends AnimatorRelic implements CustomSavable<String>
{
    public static final String ID = CreateFullID(PurgingStone.class.getSimpleName());

    private static final int CHARGES = 4;

    private final ArrayList<String> bannedCards;

    public PurgingStone()
    {
        super(ID, RelicTier.STARTER, LandingSound.CLINK);

        bannedCards = new ArrayList<>();
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new PurgingStone();
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip()
    {
        super.onEquip();
        this.counter = CHARGES;
    }

    @Override
    public void onVictory()
    {
        super.onVictory();

        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room.rewardAllowed && room instanceof MonsterRoomBoss)
        {
            this.counter += CHARGES;
        }
    }

    public static PurgingStone GetInstance()
    {
        if (AbstractDungeon.player == null || AbstractDungeon.player.relics == null)
        {
            return null;
        }

        for (AbstractRelic r : AbstractDungeon.player.relics)
        {
            if (r instanceof PurgingStone)
            {
                return (PurgingStone)r;
            }
        }

        return null;
    }

    public boolean CanActivate(RewardItem rewardItem)
    {
        return (rewardItem != null && rewardItem.type == RewardItem.RewardType.CARD);
    }

    public boolean CanBan(AbstractCard card)
    {
        return counter > 0 && !bannedCards.contains(card.cardID);
    }

    public void Ban(AbstractCard card)
    {
        bannedCards.add(card.cardID);
        counter -= 1;
        this.flash();
        //logger.info(bannedCards.size() + ": " + onSave());
    }

    @Override
    public String onSave()
    {
        return String.join("\u001F", bannedCards);
    }

    @Override
    public void onLoad(String value)
    {
        Collections.addAll(bannedCards, value.split("\u001F"));
    }

    public void receiveRewards(ArrayList<RewardItem> rewards)
    {
        logger.info("Received Rewards");
        for (RewardItem r : rewards)
        {
            if (r.type == RewardItem.RewardType.CARD)
            {
//                StringJoiner sj = new StringJoiner(",");
//                for (AbstractCard c : r.cards)
//                {
//                    sj.add(c.cardID);
//                }
//                logger.info(sj.toString());

                int cycles = 300;
                AbstractCard toRemove;
                do
                {
                    toRemove = null;
                    for (AbstractCard c : r.cards)
                    {
                        if (bannedCards.contains(c.cardID))
                        {
                            toRemove = c;
                            break;
                        }
                    }

                    if (toRemove != null)
                    {
                        logger.info("Removing " + toRemove.cardID);
                        r.cards.remove(toRemove);

                        AbstractCard card = null;
                        int cycles2 = 60;
                        while (card == null && cycles2-- > 0)
                        {
                            card = AbstractDungeon.returnRandomCard();
                            if (card.cardID.equals(toRemove.cardID))
                            {
                                card = null;
                            }
                        }

                        if (card != null)
                        {
                            r.cards.add(card);
                        }
                    }
                }
                while (toRemove != null && cycles-- > 0);
            }
        }
    }
}