package eatyourbeets.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import eatyourbeets.Utilities;
import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.rewards.SynergyCardsReward;

import java.util.ArrayList;

public class TheMissingPiece extends AnimatorRelic
{
    public static final String ID = CreateFullID(TheMissingPiece.class.getSimpleName());

    private static final int TIMER = 5;

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
    public void onVictory()
    {
        super.onVictory();

        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room.rewardAllowed)
        {
            this.counter += 1;
        }
    }

    public void receiveRewards(ArrayList<RewardItem> rewards)
    {
        if (counter > 0 && counter < TIMER)
        {
            return;
        }
        else
        {
            counter = 0;
            this.flash();
        }

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
    }

    private void addSynergyRewards(ArrayList<RewardItem> rewards, int startingIndex)
    {
        ArrayList<Synergy> synergies = new ArrayList<>();
        synergies.add(Synergies.Gate);
        synergies.add(Synergies.Overlord);
        synergies.add(Synergies.NoGameNoLife);
        //synergies.add(Synergies.Chaika);
        synergies.add(Synergies.Katanagatari);
        synergies.add(Synergies.Fate);
        synergies.add(Synergies.Konosuba);
        synergies.add(Synergies.OwariNoSeraph);

        for (int i = 0; i < 3; i++)
        {
            Synergy synergy = Utilities.GetRandomElement(synergies, AbstractDungeon.cardRng);
            if (synergy != null)
            {
                synergies.remove(synergy);
                rewards.add(startingIndex + i, new SynergyCardsReward(synergy));
            }
        }
    }
}