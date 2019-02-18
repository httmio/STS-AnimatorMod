package eatyourbeets.characters.Loadouts;

import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.DolaCouronne;
import eatyourbeets.cards.animator.Jibril;
import eatyourbeets.characters.AnimatorCustomLoadout;

import java.util.ArrayList;

public class NoGameNoLife extends AnimatorCustomLoadout
{
    public NoGameNoLife()
    {
        Synergy s = Synergies.NoGameNoLife;

        this.ID = s.ID;
        this.Name = s.NAME;
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        res.add(Jibril.ID);
        res.add(DolaCouronne.ID);

        return res;
    }
}
