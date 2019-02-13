package eatyourbeets.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.misc.WeightedList;
import eatyourbeets.rewards.SynergyCardsReward;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TheMissingPiece extends AnimatorRelic
{
    public static final String ID = CreateFullID(TheMissingPiece.class.getSimpleName());

    private static final ArrayList<Synergy> possibleRewards = new ArrayList<>();
    private static final int TIMER = 5;

    private boolean skipReward;

    public TheMissingPiece()
    {
        super(ID, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new TheMissingPiece();
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + TIMER + DESCRIPTIONS[1];
    }

    @Override
    public void onEquip()
    {
        super.onEquip();
        this.counter = 0;
    }

    @Override
    public void atBattleStart()
    {
        super.atBattleStart();

        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room.rewardAllowed)
        {
            this.counter += 1;
            this.skipReward = false;
        }
    }

    public void receiveRewards(ArrayList<RewardItem> rewards)
    {
        if (counter == 0)
        {
            if (skipReward)
            {
                return;
            }

            skipReward = true;
        }
        else if (counter < TIMER)
        {
            return;
        }

        counter = 0;
        this.flash();

        int startingIndex = -1;
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
        {
            startingIndex = rewards.size();
        }
        else
        {
            for (int i = 0; i < rewards.size(); i++)
            {
                RewardItem reward = rewards.get(i);
                if (reward.type == RewardItem.RewardType.CARD)
                {
                    startingIndex = i;
                    rewards.remove(startingIndex);
                    break;
                }
            }
        }

        if (startingIndex >= 0)
        {
            addSynergyRewards(rewards, startingIndex);
        }

        if (counter == 0)
        {
            this.skipReward = true;
        }
    }

    private void addSynergyRewards(ArrayList<RewardItem> rewards, int startingIndex)
    {
        WeightedList<Synergy> synergies = CreateWeightedList();

        for (int i = 0; i < 3; i++)
        {
            Synergy synergy = synergies.Retrieve(AbstractDungeon.cardRng);
            if (synergy != null)
            {
                rewards.add(startingIndex + i, new SynergyCardsReward(synergy));
            }
        }
    }

    private WeightedList<Synergy> CreateWeightedList()
    {
        WeightedList<Synergy> list = new WeightedList<>();
        Map<Synergy, List<AbstractCard>> synergyListMap = AbstractDungeon.player.masterDeck.group.stream().collect(Collectors.groupingBy(this::Group));

        for (Synergy s : possibleRewards)
        {
            int weight = 8;
            if (synergyListMap.containsKey(s))
            {
                int size = synergyListMap.get(s).size();

                weight += 2 + Math.min(18, size * 3);
            }

            logger.info(s.NAME + " : " + weight);
            list.Add(s, weight);
        }

        return list;
    }

    private Synergy Group(AbstractCard card)
    {
        AnimatorCard c = Utilities.SafeCast(card, AnimatorCard.class);

        Synergy synergy = null;
        if (c != null)
        {
            synergy = c.GetSynergy();
        }

        return synergy != null ? synergy : Synergies.ANY;
    }

    static
    {
        possibleRewards.add(Synergies.Gate);
        possibleRewards.add(Synergies.Overlord);
        possibleRewards.add(Synergies.NoGameNoLife);
        possibleRewards.add(Synergies.Chaika);
        possibleRewards.add(Synergies.Katanagatari);
        possibleRewards.add(Synergies.Fate);
        possibleRewards.add(Synergies.Elsword);
        possibleRewards.add(Synergies.Konosuba);
        possibleRewards.add(Synergies.OwariNoSeraph);
        possibleRewards.add(Synergies.GoblinSlayer);
    }
}