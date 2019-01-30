package eatyourbeets.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import eatyourbeets.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patches.AbstractCardEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AnimatorCard extends CustomCard 
{
    protected static final Logger logger = LogManager.getLogger(AnimatorCard.class.getName());
    private static AnimatorCard previousCard = null;
    private static AnimatorCard lastCardPlayed = null;

    private String upgradedDescription = null;
    private List<TooltipInfo> customTooltips = new ArrayList<>();
    private ArrayList<String> synergies = new ArrayList<>();
    private boolean lastHovered = false;

    public boolean isSecondaryValueModified = false;
    public boolean upgradedSecondaryValue = false;
    public int baseSecondaryValue = 0;
    public int secondaryValue = 0;

    public static String CreateFullID(String cardID) 
    {
        return "animator_" + cardID;
    }

    public static void SetLastCardPlayed(AbstractCard card)
    {
        if (card == null)
        {
            previousCard = null;
            lastCardPlayed = null;
        }
        else
        {
            previousCard = lastCardPlayed;
            lastCardPlayed = Utilities.SafeCast(card, AnimatorCard.class);
        }
    }

    public Boolean HasSynergy()
    {
        if (this == lastCardPlayed)
        {
            return previousCard != null && previousCard.HasSynergy(synergies);
        }
        else
        {
            return lastCardPlayed != null && lastCardPlayed.HasSynergy(synergies);
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return customTooltips;
    }

    @Override
    public boolean isHoveredInHand(float scale)
    {
        boolean hovered = super.isHoveredInHand(scale);

        if (hovered && !lastHovered)
        {
            logger.info("Hovered: " + name);
            ArrayList<AbstractCard> hand = AbstractDungeon.player.hand.group;
            for (AbstractCard c : hand)
            {
                AnimatorCard card = Utilities.SafeCast(c, AnimatorCard.class);
                if (c != this && (card != null && card.HasSynergy(synergies)))
                {
                    c.targetDrawScale = 0.9f;
                }
            }
        }

        lastHovered = hovered;

        return hovered;
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c)
    {
        super.triggerOnOtherCardPlayed(c);

        if (HasSynergy())
        {
            this.targetDrawScale = 0.9f;
        }

        //logger.info(String.format("(%s) Card Played: %s | HasSynergy: %s", this.name, c.name, HasSynergy() ? "yes" : "no"));
    }

    @Override
    public void render(SpriteBatch sb)
    {
        super.render(sb);
        RenderSynergy(sb);
    }

    @Override
    public void renderInLibrary(SpriteBatch sb)
    {
        super.renderInLibrary(sb);
        RenderSynergy(sb);
    }

    private void RenderSynergy(SpriteBatch sb)
    {
        if (this.synergies.size() > 0)
        {
            float originalScale = FontHelper.cardTitleFont_small_N.getData().scaleX;
            FontHelper.cardTitleFont_small_N.getData().setScale(this.drawScale * 0.8f);

            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont_small_N, this.synergies.get(0),
                    this.current_x, this.current_y, 0.0F, 400.0F * Settings.scale * this.drawScale / 2.0F,
                    this.angle, true, Settings.CREAM_COLOR.cpy());

            FontHelper.cardTitleFont_small_N.getData().setScale(originalScale);
        }
    }

    @Override
    public AbstractCard makeCopy() 
    {
        try 
        {
            return getClass().newInstance();
        } 
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy()
    {
        AbstractCard result = super.makeStatEquivalentCopy();
        AnimatorCard copy = Utilities.SafeCast(result, AnimatorCard.class);
        if (copy != null)
        {
            copy.baseSecondaryValue = this.baseSecondaryValue;
        }

        return result;
    }

    public void Initialize(int baseDamage, int baseBlock)
    {
        Initialize(baseDamage, baseBlock, -1);
    }

    public void Initialize(int baseDamage, int baseBlock, int baseMagicNumber)
    {
        this.baseDamage = baseDamage;
        this.baseBlock = baseBlock;
        this.baseMagicNumber = baseMagicNumber;
        this.magicNumber = baseMagicNumber;
    }

    public Boolean TryUpgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();

            if (StringUtils.isNotEmpty(upgradedDescription))
            {
                this.rawDescription = upgradedDescription;
                this.initializeDescription();
            }

            return true;
        }

        return false;
    }

    public boolean HasSynergy(AbstractCard card)
    {
        return card instanceof AnimatorCard && ((AnimatorCard)card).HasSynergy(synergies);
    }

    protected void upgradeSecondaryValue(int amount)
    {
        this.baseSecondaryValue += amount;
        this.secondaryValue = this.baseSecondaryValue;
        this.upgradedSecondaryValue = true;
    }

    protected void AddSynergy(String synergy)
    {
        this.synergies.add(synergy);
        customTooltips.add(new TooltipInfo("Synergies:", synergy));
    }

    protected void AddSynergies(String... synergies)
    {
        Collections.addAll(this.synergies, synergies);
        customTooltips.add(new TooltipInfo("Synergies:", StringUtils.join(synergies, ", ")));
    }

    protected void AddTooltip(TooltipInfo tooltip)
    {
        customTooltips.add(tooltip);
    }

    private Boolean HasSynergy(ArrayList<String> synergies)
    {
        for (String s1 : this.synergies)
        {
            if (s1.equals(Synergies.ANY) && synergies.size() > 0)
            {
                return true;
            }

            for (String s2 : synergies)
            {
                if (s2.equals(Synergies.ANY) || s1.equals(s2))
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected AnimatorCard(String id, int cost, CardType type, CardRarity rarity, CardTarget target)
    {
        this(id, cost, type, AbstractCardEnum.THE_ANIMATOR, rarity, target);
    }

    protected AnimatorCard(String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        this(CardCrawlGame.languagePack.getCardStrings(id), id, cost, type, color, rarity, target);
    }

    protected AnimatorCard(CardStrings strings, String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, strings.NAME, "images/cards/" + id + ".png", cost, strings.DESCRIPTION, type, color, rarity, target);

        if (StringUtils.isNotEmpty(strings.UPGRADE_DESCRIPTION))
        {
            this.upgradedDescription = strings.UPGRADE_DESCRIPTION;
        }
    } 
}