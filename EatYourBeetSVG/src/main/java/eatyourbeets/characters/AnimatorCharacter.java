package eatyourbeets.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import eatyourbeets.cards.animator.Aqua;
import eatyourbeets.cards.animator.Defend;
import eatyourbeets.cards.animator.Kazuma;
import eatyourbeets.cards.animator.Strike;
import eatyourbeets.relics.LivingPicture;
import patches.AbstractCardEnum;
import patches.AbstractClassEnum;

import java.util.ArrayList;

public class AnimatorCharacter extends CustomPlayer 
{
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Animator");
    public static final Color MAIN_COLOR = CardHelper.getColor(210, 147, 106);
    public static final String[] NAMES = characterStrings.NAMES;
    public static final String[] TEXT = characterStrings.TEXT;
    public static final String NAME = NAMES[0];
    //public static Color cardRenderColor = new Color(0.0F, 0.1F, 0.0F, 1.0F);

    //private static final Logger logger = LogManager.getLogger(AnimatorCharacter.class.getName());
    //private EnergyOrbInterface energyOrb = new EnergyOrbBlue();
    //private Prefs prefs;
    //private CharStat charStat;

    private static final String orbVfx_png = "images/ui/topPanel/canvas/orbVfx.png";
    private static final String[] orbTextures = 
    {
        "images/ui/topPanel/canvas/layer1.png", "images/ui/topPanel/canvas/layer2.png", "images/ui/topPanel/canvas/layer3.png", 
        "images/ui/topPanel/canvas/layer4.png", "images/ui/topPanel/canvas/layer5.png", "images/ui/topPanel/canvas/layer6.png", 
        "images/ui/topPanel/canvas/layer1d.png", "images/ui/topPanel/canvas/layer2d.png", "images/ui/topPanel/canvas/layer3d.png", 
        "images/ui/topPanel/canvas/layer4d.png", "images/ui/topPanel/canvas/layer5d.png"
    };

    private static final String skeleton_atlas = "images/characters/animator/idle/skeleton.atlas";
    private static final String skeleton_json = "images/characters/animator/idle/skeleton.json";
    private static final String shoulder1_png = "images/characters/animator/shoulder.png";
    private static final String shoulder2_png = "images/characters/animator/shoulder2.png";
    private static final String corpse_png = "images/characters/animator/corpse.png";

    //private static final String cutscene1_png = "images/scenes/animator1.png";
    //private static final String cutscene2_png = "images/scenes/animator2.png";
    //private static final String cutscene3_png = "images/scenes/animator3.png";
    //private static final String background_png = "images/scenes/canvas_bg.png";

    public AnimatorCharacter(String name, PlayerClass playerClass) 
    {
        super(name, playerClass, orbTextures, orbVfx_png, (String)null, null);

        initializeClass(null, shoulder2_png, shoulder1_png, corpse_png, getLoadout(), 0.0F, -5.0F, 240.0F, 244.0F, new EnergyManager(3));

        //this.charStat = new CharStat(this);
        //this.drawX += 5.0F * Settings.scale;
        //this.drawY += 7.0F * Settings.scale;
        //this.dialogX = (this.drawX + 0.0F * Settings.scale);
        //this.dialogY = (this.drawY + 170.0F * Settings.scale);

        reloadAnimation();
    }

    public void reloadAnimation()
    {
        this.loadAnimation(skeleton_atlas, skeleton_json, 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.9F);
    }

    @Override
    public String getLocalizedCharacterName() 
    {
        return NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() 
    {
        return new AnimatorCharacter(this.name, AbstractClassEnum.THE_ANIMATOR);
    }

    @Override
    public String getSpireHeartText()
    {
        return com.megacrit.cardcrawl.events.beyond.SpireHeart.DESCRIPTIONS[10];
    }

    @Override
    public Color getSlashAttackColor() 
    {
        return Color.SKY;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() 
    {
        return new AbstractGameAction.AttackEffect[] 
        {
            AbstractGameAction.AttackEffect.SLASH_HEAVY,
            AbstractGameAction.AttackEffect.FIRE,
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
            AbstractGameAction.AttackEffect.SLASH_HEAVY,
            AbstractGameAction.AttackEffect.FIRE,
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        };
    }
    
    @Override
    public String getVampireText() 
    {
        return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[5];
    }

    @Override
    public Color getCardTrailColor() 
    {
        return MAIN_COLOR.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() 
    {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() 
    {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() 
    {
        CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() 
    {
        return "ATTACK_MAGIC_BEAM_SHORT";
    }

    @Override
    public ArrayList<String> getStartingDeck() 
    {
        ArrayList<String> res = new ArrayList<>();
        res.add(Strike.ID);
        res.add(Strike.ID);
        res.add(Strike.ID);
        res.add(Strike.ID);
        res.add(Defend.ID);
        res.add(Defend.ID);
        res.add(Defend.ID);
        res.add(Defend.ID);
        res.add(Kazuma.ID);
        res.add(Aqua.ID);
        return res;
    }

    @Override
    public ArrayList<String> getStartingRelics() 
    {
        if (!UnlockTracker.isRelicSeen(LivingPicture.ID))
        {
            UnlockTracker.markRelicAsSeen(LivingPicture.ID);
        }

        ArrayList<String> res = new ArrayList<>();
        res.add(LivingPicture.ID);
        return res;
    }

    @Override
    public AbstractCard getStartCardForEvent()
    {
        return new Strike();
    }

    @Override
    public CharSelectInfo getLoadout()
    {
        return new CharSelectInfo(NAMES[0], TEXT[0], 75, 75, 1, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass)
    {
        return NAME;
    }

    @Override
    public AbstractCard.CardColor getCardColor() 
    {
        return AbstractCardEnum.THE_ANIMATOR;
    }

    @Override
    public Color getCardRenderColor()
    {
        return MAIN_COLOR.cpy();
    }
}

/*

    @Override
    public String getAchievementKey() 
    {
        return "SAPPHIRE";
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool)
    {
        CardLibrary.add(new Strike());
        CardLibrary.add(new Megumin());
        CardLibrary.add(new Defend());

        CardLibrary.addBlueCards(tmpPool);

        if (ModHelper.isModEnabled("Red Cards"))
        {
            CardLibrary.addRedCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Green Cards")) 
        {
            CardLibrary.addGreenCards(tmpPool);
        }

        return tmpPool;
    }

    @Override
    public String getLeaderboardCharacterName() 
    {
        return "ANIMATOR";
    }
    
    @Override
    public Texture getEnergyImage() 
    {
        return ImageMaster.BLUE_ORB_FLASH_VFX;
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        this.energyOrb.renderOrb(sb, enabled, current_x, current_y);
    }

    @Override
    public void updateOrb(int orbCount) 
    {
        this.energyOrb.updateOrb(orbCount);
    }

    @Override
    public Prefs getPrefs() 
    {
        if (this.prefs == null) 
        {
            logger.error("prefs need to be initialized first!");
        }

        return this.prefs;
    }

    @Override
    public void loadPrefs() 
    {
        if (this.prefs == null) 
        {
            this.prefs = SaveHelper.getPrefs("STSDataAnimator");
        }
    }

    @Override
    public CharStat getCharStat() 
    {
        return this.charStat;
    }

    @Override
    public int getUnlockedCardCount() 
    {
        return UnlockTracker.unlockedBlueCardCount;
    }

    @Override
    public int getSeenCardCount() 
    {
        return CardLibrary.seenBlueCards;
    }

    @Override
    public int getCardCount() 
    {
        return CardLibrary.blueCards;
    }

    @Override
    public boolean saveFileExists() 
    {
        return SaveAndContinue.saveExistsAndNotCorrupted(this);
    }

    @Override
    public String getWinStreakKey() 
    {
        return "win_streak_animator";
    }

    @Override
    public String getLeaderboardWinStreakKey() 
    {
        return "ANIMATOR_CONSECUTIVE_WINS";
    }

    @Override
    public void renderStatScreen(SpriteBatch sb, float screenX, float renderY) 
    {
        //if (!UnlockTracker.isCharacterLocked("Defect")) 
        //{
            if (CardCrawlGame.mainMenuScreen.statsScreen.defectHb == null) 
            {
                CardCrawlGame.mainMenuScreen.statsScreen.defectHb = new Hitbox(150.0F * Settings.scale, 150.0F * Settings.scale);
            }

            StatsScreen.renderHeader(sb, StatsScreen.NAMES[4], screenX, renderY);
            this.charStat.render(sb, screenX, renderY);
        //}
    }

    @Override
    public Texture getCustomModeCharacterButtonImage() 
    {
        return ImageMaster.FILTER_DEFECT;
    }

    @Override
    public CharacterStrings getCharacterString() 
    {
        return CardCrawlGame.languagePack.getCharacterString("Animator");
    }

    @Override
    public void refreshCharStat() 
    {
        this.charStat = new CharStat(this);
    }

    @Override
    public TextureAtlas.AtlasRegion getOrb() 
    {
        return AbstractCard.orb_blue;
    }

    @Override
    public void damage(DamageInfo info) 
    {
        if ((info.owner != null) && (info.type != DamageInfo.DamageType.THORNS) && (info.output - this.currentBlock > 0)) 
        {
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTime(0.9F);
        }

        super.damage(info);
    }

*/