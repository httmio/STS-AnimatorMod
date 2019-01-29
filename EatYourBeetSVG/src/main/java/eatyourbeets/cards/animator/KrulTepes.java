package eatyourbeets.cards.animator;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BloodVial;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import eatyourbeets.actions.KrulTepesAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class KrulTepes extends AnimatorCard
{
    public static final String ID = CreateFullID(KrulTepes.class.getSimpleName());

    private static final AbstractRelic relicReward = new BloodVial();

    public KrulTepes()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);

        Initialize(16,0);

        AddTooltip(new TooltipInfo(relicReward.name, relicReward.description));
        AddTooltip(new TooltipInfo("Relic Limit", "You will only receive 1 relic in multi-elite combats"));
        AddSynergy(Synergies.OwariNoSeraph);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        if (m != null)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
        }

        AbstractDungeon.actionManager.addToBottom(new KrulTepesAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this));
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {          
            upgradeDamage(6);
        }
    }

    public boolean CanGetReward()
    {
        for (RewardItem r : AbstractDungeon.getCurrRoom().rewards)
        {
            if (r.relic != null && r.relic.relicId.equals(relicReward.relicId))
            {
                return false;
            }
        }

        return true;
    }

    public void ObtainReward()
    {
        AbstractDungeon.getCurrRoom().addRelicToRewards(relicReward.makeCopy());
    }
}