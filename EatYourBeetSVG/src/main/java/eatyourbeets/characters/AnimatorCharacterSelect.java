package eatyourbeets.characters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.localization.KeywordStrings;
import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.*;

import java.util.ArrayList;

public class AnimatorCharacterSelect
{
    private static int index;
    private static ArrayList<Synergy> possibleSynergies;

    public static Synergy GetSynergy()
    {
        return possibleSynergies.get(index);
    }

    public static void NextSynergy()
    {
        index += 1;
        if (index >= possibleSynergies.size())
        {
            index = 0;
        }
    }

    public static void PreviousSynergy()
    {
        index -= 1;
        if (index < 0)
        {
            index = possibleSynergies.size() - 1;
        }
    }

    public static void PrepareCharacterDeck(ArrayList<String> deck)
    {
        int id = GetSynergy().ID;

        if (id == Synergies.Konosuba.ID)
        {
            deck.add(Kazuma.ID);
            deck.add(Aqua.ID);
        }
        else if (id == Synergies.Fate.ID)
        {
            deck.add(Lancer.ID);
            deck.add(RinTohsaka.ID);
        }
        else if (id == Synergies.OwariNoSeraph.ID)
        {
            deck.add(Mikaela.ID);
            deck.add(Yuuichirou.ID);
        }
        else if (id == Synergies.Overlord.ID)
        {
            deck.add(Shalltear.ID);
            deck.add(Cocytus.ID);
        }
        else if (id == Synergies.Gate.ID)
        {
            deck.add(TukaLunaMarceau.ID);
            deck.add(YaoHaDucy.ID);
        }
        else if (id == Synergies.Katanagatari.ID)
        {
            deck.add(Emonzaemon.ID);
            deck.add(Nanami.ID);
        }
    }

    static
    {
        index = 0;
        possibleSynergies = new ArrayList<>();

        possibleSynergies.add(Synergies.Konosuba);
        possibleSynergies.add(Synergies.Fate);
        possibleSynergies.add(Synergies.OwariNoSeraph);
        possibleSynergies.add(Synergies.Overlord);
        possibleSynergies.add(Synergies.Gate);
        possibleSynergies.add(Synergies.Katanagatari);
    }
}


//    int id = GetSynergy().ID;
//
//        if (id == Synergies.Konosuba.ID)
//                {
//                deck.add(new Kazuma());
//                deck.add(new Aqua());
//                }
//                else if (id == Synergies.Fate.ID)
//                {
//                deck.add(new Lancer());
//                deck.add(new RinTohsaka());
//                }
//                else if (id == Synergies.OwariNoSeraph.ID)
//                {
//                deck.add(new Mikaela());
//                deck.add(new Yuuichirou());
//                }
//                else if (id == Synergies.Overlord.ID)
//                {
//                deck.add(new Shalltear());
//                deck.add(new Cocytus());
//                }
//                else if (id == Synergies.Gate.ID)
//                {
//                deck.add(new TukaLunaMarceau());
//                deck.add(new YaoHaDucy());
//                }