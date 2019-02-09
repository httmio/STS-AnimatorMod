package eatyourbeets.characters;

import com.megacrit.cardcrawl.unlock.UnlockTracker;
import eatyourbeets.AnimatorResources;
import eatyourbeets.cards.Synergies;
import eatyourbeets.cards.Synergy;
import eatyourbeets.cards.animator.*;
import patches.AbstractClassEnum;

import java.util.ArrayList;
import java.util.Arrays;

public class AnimatorCharacterSelect
{
    private static int index;
    private static final ArrayList<SynergyInfo> possibleSynergies;

    protected static final String[] uiText = AnimatorResources.GetUIStrings(AnimatorResources.UIStringType.CharacterSelect).TEXT;

    public static SynergyInfo GetSynergyInfo()
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
        deck.addAll(GetSynergyInfo().Cards);
    }


    private static void AddSynergyInfo(Synergy synergy, int level, String extraInfo, String... cards)
    {
        AddSynergyInfo(synergy.NAME, level, extraInfo, cards);
    }

    private static void AddSynergyInfo(String name, int level, String extraInfo, String... cards)
    {
        SynergyInfo info = new SynergyInfo(name, extraInfo, level);
        info.Cards.addAll(Arrays.asList(cards));
        possibleSynergies.add(info);
    }

    static
    {
        index = 0;
        possibleSynergies = new ArrayList<>();

        String recommended = uiText[5];
        String balanced = uiText[6];
        String unbalanced = uiText[7];
        String veryUnbalanced = uiText[8];

        AddSynergyInfo(Synergies.Konosuba, 0, recommended, Kazuma.ID, Aqua.ID);
        AddSynergyInfo(Synergies.Gate, 1, balanced, Tyuule.ID, Kuribayashi.ID);
        AddSynergyInfo(Synergies.Elsword, 1, balanced, Ara.ID, Elsword.ID);
        AddSynergyInfo(Synergies.NoGameNoLife, 1, balanced, Jibril.ID, DolaCouronne.ID);
        AddSynergyInfo(Synergies.OwariNoSeraph, 2, unbalanced, Yuuichirou.ID, Shinoa.ID);
        AddSynergyInfo(Synergies.Katanagatari, 2, unbalanced, Emonzaemon.ID, Nanami.ID);
        AddSynergyInfo(Synergies.Fate, 2, unbalanced, RinTohsaka.ID, Lancer.ID);
        AddSynergyInfo(Synergies.Overlord, 3, veryUnbalanced, Cocytus.ID, Shalltear.ID);
        AddSynergyInfo(Synergies.Chaika, 3, veryUnbalanced, AcuraTooru.ID, AcuraAkari.ID);
    }

    public static class SynergyInfo
    {
        public final String Name;
        public final ArrayList<String> Cards;
        public boolean Locked;

        private final String description;
        private final int unlockLevel;

        public String GetDescription()
        {
            int currentLevel = UnlockTracker.getUnlockLevel(AbstractClassEnum.THE_ANIMATOR);
            Locked = unlockLevel > currentLevel;
            if (Locked)
            {
                return AnimatorCharacterSelect.uiText[2] + unlockLevel +
                        AnimatorCharacterSelect.uiText[3] + currentLevel +
                        AnimatorCharacterSelect.uiText[4];
            }
            else
            {
                return description;
            }
        }

        protected SynergyInfo(String name, String description, int unlockLevel)
        {
            this.unlockLevel = unlockLevel;
            this.Name = name;
            this.description = description;
            this.Cards = new ArrayList<>();
        }
    }
}