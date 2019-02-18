package eatyourbeets.characters.Loadouts;

import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.AcuraAkari;
import eatyourbeets.cards.animator.AcuraTooru;
import eatyourbeets.characters.AnimatorCustomLoadout;

import java.util.ArrayList;

public class Chaika extends AnimatorCustomLoadout
{
    public Chaika()
    {
        Synergy s = Synergies.Chaika;

        this.ID = s.ID;
        this.Name = s.NAME;
    }

    @Override
    public ArrayList<String> GetStartingDeck()
    {
        ArrayList<String> res = super.GetStartingDeck();
        res.add(AcuraAkari.ID);
        res.add(AcuraTooru.ID);

        return res;
    }
}
