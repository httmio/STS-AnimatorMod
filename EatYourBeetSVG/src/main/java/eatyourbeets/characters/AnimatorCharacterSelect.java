package eatyourbeets.characters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import eatyourbeets.AnimatorResources;
import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.*;
import patches.AbstractEnums;

import java.util.ArrayList;
import java.util.Arrays;

public class AnimatorCharacterSelect
{
    private static int index;
    private static final ArrayList<StartingDeck> startingDecks;

    protected static final String[] uiText = AnimatorResources.GetUIStrings(AnimatorResources.UIStringType.CharacterSelect).TEXT;

    public static void Refresh()
    {
        int currentLevel = UnlockTracker.getUnlockLevel(AbstractEnums.Characters.THE_ANIMATOR);
        for (StartingDeck deck : startingDecks)
        {
            deck.Refresh(currentLevel);
        }
    }

    public static StartingDeck GetDeckInfo()
    {
        return startingDecks.get(index);
    }

    public static void NextDeck()
    {
        index += 1;
        if (index >= startingDecks.size())
        {
            index = 0;
        }
    }

    public static void PreviousDeck()
    {
        index -= 1;
        if (index < 0)
        {
            index = startingDecks.size() - 1;
        }
    }

    public static void PrepareCharacterDeck(ArrayList<String> deck)
    {
        deck.addAll(GetDeckInfo().CardNames);
    }

    private static void AddStartingDeck(Synergy synergy, int level, String extraInfo, String... cards)
    {
        AddStartingDeck(synergy.NAME, synergy.ID, level, extraInfo, cards);
    }

    private static void AddStartingDeck(String name, int id, int level, String extraInfo, String... cards)
    {
        StartingDeck info = new StartingDeck(name, id, extraInfo, level);

        for (int i = 0; i < 4; i++)
        {
            info.CardNames.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++)
        {
            info.CardNames.add(Defend.ID);
        }
        info.CardNames.addAll(Arrays.asList(cards));

        startingDecks.add(info);
    }

    static
    {
        index = 0;
        startingDecks = new ArrayList<>();

        String recommended = uiText[5];
        String balanced = uiText[6];
        String unbalanced = uiText[7];
        String veryUnbalanced = uiText[8];

        AddStartingDeck(Synergies.Konosuba, 0, recommended, Kazuma.ID, Aqua.ID);
        AddStartingDeck(Synergies.Gate, 1, balanced, Tyuule.ID, Kuribayashi.ID);
        AddStartingDeck(Synergies.Elsword, 1, balanced, Chung.ID, Elsword.ID);
        AddStartingDeck(Synergies.NoGameNoLife, 1, balanced, Jibril.ID, DolaCouronne.ID);
        AddStartingDeck(Synergies.OwariNoSeraph, 2, unbalanced, Yuuichirou.ID, Shinoa.ID);
        AddStartingDeck(Synergies.GoblinSlayer, 2, unbalanced, Priestess.ID, DwarfShaman.ID);
        AddStartingDeck(Synergies.Katanagatari, 2, unbalanced, Emonzaemon.ID, Nanami.ID);
        AddStartingDeck(Synergies.Fate, 3, veryUnbalanced, RinTohsaka.ID, Lancer.ID);
        AddStartingDeck(Synergies.Overlord, 3, veryUnbalanced, Cocytus.ID, NarberalGamma.ID);
        AddStartingDeck(Synergies.Chaika, 3, veryUnbalanced, AcuraTooru.ID, AcuraAkari.ID);
    }

    public static class StartingDeck
    {
        private final ArrayList<AbstractCard> cardsCache = new ArrayList<>();

        public final int ID;
        public final String Name;
        public final ArrayList<String> CardNames;
        public boolean Locked;

        private String lockedDescription;
        private final String description;
        private final int unlockLevel;

        public void SetCards(ArrayList<AbstractCard> cards)
        {
            if (cardsCache.size() == 0)
            {
                for (String s : CardNames)
                {
                    cardsCache.add(CardLibrary.getCard(s).makeCopy());
                }
            }

            for (AbstractCard c : cardsCache)
            {
                c.current_x = Settings.WIDTH;
                c.current_y = Settings.HEIGHT;
                c.isFlipped = Locked;
                cards.add(c);
            }
        }

        public void Refresh(int currentLevel)
        {
            Locked = unlockLevel > currentLevel;
            if (Locked)
            {
                lockedDescription =
                        AnimatorCharacterSelect.uiText[2] + unlockLevel +
                        AnimatorCharacterSelect.uiText[3] + currentLevel +
                        AnimatorCharacterSelect.uiText[4];
            }
        }

        public String GetDescription()
        {
            if (Locked)
            {
                return lockedDescription;
            }
            else
            {
                return description;
            }
        }

        protected StartingDeck(String name, int id, String description, int unlockLevel)
        {
            this.Name = name;
            this.ID = id;
            this.description = description;
            this.unlockLevel = unlockLevel;
            this.CardNames = new ArrayList<>();
        }
    }
}