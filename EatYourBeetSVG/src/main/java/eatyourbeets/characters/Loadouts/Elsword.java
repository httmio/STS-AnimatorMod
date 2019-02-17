package eatyourbeets.characters.Loadouts;

import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.Chung;
import eatyourbeets.characters.AnimatorCustomLoadout;

import java.util.ArrayList;

public class Elsword extends AnimatorCustomLoadout
{
    public Elsword()
    {
        Synergy s = Synergies.Elsword;

        this.ID = s.ID;
        this.Name = s.NAME;
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        res.add(Chung.ID);
        res.add(eatyourbeets.cards.animator.Elsword.ID);

        return res;
    }
}
