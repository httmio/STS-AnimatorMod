package eatyourbeets.characters.Loadouts;

import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.Shinoa;
import eatyourbeets.cards.animator.Yuuichirou;
import eatyourbeets.characters.AnimatorCustomLoadout;

import java.util.ArrayList;

public class OwariNoSeraph extends AnimatorCustomLoadout
{
    public OwariNoSeraph()
    {
        Synergy s = Synergies.OwariNoSeraph;

        this.ID = s.ID;
        this.Name = s.NAME;
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        res.add(Shinoa.ID);
        res.add(Yuuichirou.ID);

        return res;
    }
}
