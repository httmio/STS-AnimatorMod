package eatyourbeets;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import eatyourbeets.cards.AnimatorCard;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomAbstractDungeon extends AbstractDungeon
{
    public CustomAbstractDungeon(String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList)
    {
        super(name, levelId, p, newSpecialOneTimeEventList);
    }

    public CustomAbstractDungeon(String name, AbstractPlayer p, SaveFile saveFile)
    {
        super(name, p, saveFile);
    }

    @Override
    protected void initializeLevelSpecificChances() { }
    @Override
    protected ArrayList<String> generateExclusions() { return null; }
    @Override
    protected void generateMonsters() { }
    @Override
    protected void generateWeakEnemies(int i) { }
    @Override
    protected void generateStrongEnemies(int i) { }
    @Override
    protected void generateElites(int i) { }
    @Override
    protected void initializeBoss() { }
    @Override
    protected void initializeEventList() { }
    @Override
    protected void initializeEventImg() { }
    @Override
    protected void initializeShrineList() { }


    public static ArrayList<AbstractCard> getRewardCards(String synergy)
    {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        int numCards = 3;

        if (player.hasRelic("Question Card"))
        {
            ++numCards;
        }

        if (player.hasRelic("Busted Crown"))
        {
            numCards -= 2;
        }

        if (ModHelper.isModEnabled("Binary"))
        {
            --numCards;
        }

        ArrayList<AbstractCard> animatorCards = new ArrayList<>();
        AbstractDungeon.player.getCardPool(animatorCards);

        ArrayList<AnimatorCard> common = new ArrayList<>();
        ArrayList<AnimatorCard> uncommon = new ArrayList<>();
        ArrayList<AnimatorCard> rare = new ArrayList<>();
        for (AbstractCard card : animatorCards)
        {
            AnimatorCard ac = Utilities.SafeCast(card, AnimatorCard.class);
            if (ac != null && ac.HasExactSynergy(synergy))
            {
                switch (ac.rarity)
                {
                    case COMMON:
                        common.add(ac);
                        break;

                    case UNCOMMON:
                        uncommon.add(ac);
                        break;

                    case RARE:
                        rare.add(ac);
                        break;
                }
            }
        }

        AbstractCard c;
        for(int i = 0; i < numCards; ++i)
        {
            AbstractCard.CardRarity rarity = rollRarity();
            c = null;
            switch(rarity)
            {
                case COMMON:
                    cardBlizzRandomizer -= cardBlizzGrowth;
                    if (cardBlizzRandomizer <= cardBlizzMaxOffset)
                    {
                        cardBlizzRandomizer = cardBlizzMaxOffset;
                    }
                case UNCOMMON:
                    break;
                case RARE:
                    cardBlizzRandomizer = cardBlizzStartOffset;
                    break;
                default:
                    logger.info("WTF?");
            }

            if (rarity == AbstractCard.CardRarity.COMMON && common.size() == 0)
            {
                if (uncommon.size() > 0)
                {
                    rarity = AbstractCard.CardRarity.UNCOMMON;
                }
                else
                {
                    rarity = AbstractCard.CardRarity.RARE;
                }
            }
            else if (rarity == AbstractCard.CardRarity.UNCOMMON && uncommon.size() == 0)
            {
                if (common.size() > 0)
                {
                    rarity = AbstractCard.CardRarity.COMMON;
                }
                else
                {
                    rarity = AbstractCard.CardRarity.RARE;
                }
            }
            else if (rarity == AbstractCard.CardRarity.RARE && rare.size() == 0)
            {
                if (uncommon.size() > 0)
                {
                    rarity = AbstractCard.CardRarity.UNCOMMON;
                }
                else
                {
                    rarity = AbstractCard.CardRarity.COMMON;
                }
            }

            switch (rarity)
            {
                case COMMON:
                    c = Utilities.GetRandomElement(common, cardRng);
                    common.remove(c);
                    break;

                case UNCOMMON:
                    c = Utilities.GetRandomElement(uncommon, cardRng);
                    uncommon.remove(c);
                    break;

                case RARE:
                    c = Utilities.GetRandomElement(rare, cardRng);
                    rare.remove(c);
                    break;
            }

            if (c != null)
            {
                AbstractCard copy = c.makeCopy();
                if (copy.rarity != AbstractCard.CardRarity.RARE && cardRng.randomBoolean(cardUpgradedChance) && copy.canUpgrade())
                {
                    copy.upgrade();
                }
                else if (copy.type == AbstractCard.CardType.ATTACK && player.hasRelic("Molten Egg 2"))
                {
                    copy.upgrade();
                }
                else if (copy.type == AbstractCard.CardType.SKILL && player.hasRelic("Toxic Egg 2"))
                {
                    copy.upgrade();
                }
                else if (copy.type == AbstractCard.CardType.POWER && player.hasRelic("Frozen Egg 2"))
                {
                    copy.upgrade();
                }

                retVal.add(copy);
            }
        }

        return retVal;
    }
}
