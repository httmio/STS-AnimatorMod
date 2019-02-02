package eatyourbeets.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import eatyourbeets.Utilities;
import eatyourbeets.cards.Synergies;
import eatyourbeets.rewards.SynergyCardsReward;

import java.util.ArrayList;

public class TheMissingPiece extends AnimatorRelic
{
    public static final String ID = "Animator_TheMissingPiece";

    private static final int TIMER = 5;

    public TheMissingPiece()
    {
        super(ID, new Texture("images/relics/animator_theMissingPiece.png"), RelicTier.STARTER, LandingSound.FLAT);
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
        if (room.rewardAllowed && !(room instanceof MonsterRoomBoss))
        {
            this.counter += 1;
        }
    }

    public void receiveRewards(ArrayList<RewardItem> rewards)
    {
        for (int i = 0; i < rewards.size(); i++)
        {
            RewardItem reward = rewards.get(i);
            if (reward.type == RewardItem.RewardType.CARD)
            {
                if (counter > 0 && counter < TIMER)
                {
                    return;
                }

                counter = 0;
                this.flash();

                ArrayList<String> synergies = new ArrayList<>();
                synergies.add(Synergies.Gate);
                synergies.add(Synergies.Overlord);
                synergies.add(Synergies.NoGameNoLife);
                //synergies.add(Synergies.Chaika);
                synergies.add(Synergies.Katanagatari);
                synergies.add(Synergies.Fate);
                synergies.add(Synergies.Konosuba);
                synergies.add(Synergies.OwariNoSeraph);

                String synergy = Utilities.GetRandomElement(synergies, AbstractDungeon.cardRng);
                synergies.remove(synergy);

                rewards.set(i, new SynergyCardsReward(synergy));

                synergy = Utilities.GetRandomElement(synergies, AbstractDungeon.cardRng);
                synergies.remove(synergy);

                rewards.add(i, new SynergyCardsReward(synergy));

                synergy = Utilities.GetRandomElement(synergies, AbstractDungeon.cardRng);
                synergies.remove(synergy);

                rewards.add(i, new SynergyCardsReward(synergy));

                return;
            }
        }
    }
}