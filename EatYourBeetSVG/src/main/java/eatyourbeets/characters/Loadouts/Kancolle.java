package eatyourbeets.characters.Loadouts;

import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.Shimakaze;
import eatyourbeets.characters.AnimatorCustomLoadout;

import java.util.ArrayList;

public class Kancolle extends AnimatorCustomLoadout
{
    public Kancolle()
    {
        Synergy s = Synergies.Kancolle;

        this.ID = s.ID;
        this.Name = s.NAME;
        this.StartingGold = 249;
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        res.add(Shimakaze.ID);

        return res;
    }
}
