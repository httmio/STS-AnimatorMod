package eatyourbeets.characters.Loadouts;

import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.Aqua;
import eatyourbeets.cards.animator.Kazuma;
import eatyourbeets.characters.AnimatorCustomLoadout;

import java.util.ArrayList;

public class Konosuba extends AnimatorCustomLoadout
{
    public Konosuba()
    {
        Synergy s = Synergies.Konosuba;

        this.ID = s.ID;
        this.Name = s.NAME;
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        res.add(Kazuma.ID);
        res.add(Aqua.ID);

        return res;
    }
}
