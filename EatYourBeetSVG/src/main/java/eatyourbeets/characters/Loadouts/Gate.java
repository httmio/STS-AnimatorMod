package eatyourbeets.characters.Loadouts;

import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.Kuribayashi;
import eatyourbeets.cards.animator.Tyuule;
import eatyourbeets.characters.AnimatorCustomLoadout;

import java.util.ArrayList;

public class Gate extends AnimatorCustomLoadout
{
    public Gate()
    {
        Synergy s = Synergies.Gate;

        this.ID = s.ID;
        this.Name = s.NAME;
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        res.add(Tyuule.ID);
        res.add(Kuribayashi.ID);

        return res;
    }
}
