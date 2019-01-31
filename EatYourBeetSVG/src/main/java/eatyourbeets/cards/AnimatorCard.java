package eatyourbeets.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import eatyourbeets.Utilities;
import eatyourbeets.powers.PlayerStatistics;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patches.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimatorCard extends CustomCard 
{
    protected static final Logger logger = LogManager.getLogger(AnimatorCard.class.getName());
    private static final Color SYNERGY_COLOR = new Color(0.565f, 0.933f, 0.565f, 1);

    private static AnimatorCard previousCard = null;
    private static AnimatorCard lastCardPlayed = null;

    private String upgradedDescription = null;
    private List<TooltipInfo> customTooltips = new ArrayList<>();
    private String synergy;
    private boolean anySynergy;
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

    public boolean HasActiveSynergy()
    {
        if (this == lastCardPlayed)
        {
            return previousCard != null && previousCard.HasSynergy(this);
        }
        else
        {
            return lastCardPlayed != null && lastCardPlayed.HasSynergy(this);
        }
    }

    public boolean HasSynergy(AbstractCard other)
    {
        AnimatorCard card = Utilities.SafeCast(other, AnimatorCard.class);
        if (card != null && card.synergy != null)
        {
            return this.anySynergy || card.anySynergy || card.synergy.equals(this.synergy);
        }

        return false;
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
                if (c != this && (card != null && card.HasSynergy(this)))
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

        if (HasActiveSynergy())
        {
            this.targetDrawScale = 0.9f;
        }
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
        if (this.synergy != null)
        {
            float originalScale = FontHelper.cardTitleFont_small_N.getData().scaleX;
            FontHelper.cardTitleFont_small_N.getData().setScale(this.drawScale * 0.8f);

            Color textColor;
            if (HasActiveSynergy())
            {
                textColor = SYNERGY_COLOR.cpy();
            }
            else
            {
                textColor = Settings.CREAM_COLOR.cpy();
            }

            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont_small_N, this.synergy,
                    this.current_x, this.current_y, 0.0F, 400.0F * Settings.scale * this.drawScale / 2.0F,
                    this.angle, true, textColor);

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

    protected void upgradeSecondaryValue(int amount)
    {
        this.baseSecondaryValue += amount;
        this.secondaryValue = this.baseSecondaryValue;
        this.upgradedSecondaryValue = true;
    }

    protected void SetSynergy(String synergy)
    {
        SetSynergy(synergy, false);
    }

    protected void SetSynergy(String synergy, boolean anySynergy)
    {
        this.synergy = synergy;
        this.anySynergy = anySynergy;
        if (anySynergy)
        {
            customTooltips.add(new TooltipInfo("Synergies", "Any"));
        }
        else
        {
            customTooltips.add(new TooltipInfo("Synergies", synergy));
        }
    }

    protected void AddTooltip(TooltipInfo tooltip)
    {
        customTooltips.add(tooltip);
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