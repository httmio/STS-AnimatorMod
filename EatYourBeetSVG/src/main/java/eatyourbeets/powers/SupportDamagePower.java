package eatyourbeets.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import eatyourbeets.GameActionsHelper;

import java.util.ArrayList;

public class SupportDamagePower extends AnimatorPower
{
    public static final String POWER_ID = CreateFullID(SupportDamagePower.class.getSimpleName());

    public SupportDamagePower(AbstractCreature owner, int stacks)
    {
        super(owner, POWER_ID);

        this.amount = stacks;
        this.type = PowerType.BUFF;

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        super.atEndOfTurn(isPlayer);
        ArrayList<AbstractMonster> enemies = PlayerStatistics.GetCurrentEnemies(true);

        AbstractMonster target = null;
        int minHealth = Integer.MAX_VALUE;
        for (AbstractMonster m : enemies)
        {
            if (m.currentHealth < minHealth)
            {
                minHealth = m.currentHealth;
                target = m;
            }
        }

        if (target != null)
        {
            //AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            //AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DieDieDieEffect()));// new IronWaveEffect(owner.hb.cX, owner.hb.cY, target.hb.cX), 0.3F));

            GameActionsHelper.DamageTarget(owner, target, this.amount, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        }
    }
}
