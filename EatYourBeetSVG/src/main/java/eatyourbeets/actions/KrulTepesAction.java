package eatyourbeets.actions;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import eatyourbeets.cards.animator.KrulTepes;

public class KrulTepesAction extends AnimatorAction
{
    private final DamageInfo info;
    private final KrulTepes krul;

    public KrulTepesAction(AbstractCreature target, DamageInfo info, KrulTepes krul)
    {
        this.info = info;
        this.krul = krul;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update()
    {
        if (this.duration == 0.1F && this.target != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
            AbstractMonster monster = ((AbstractMonster)this.target);

            monster.damage(this.info);

            if (krul.CanGetReward())
            {
                if ((monster.type == AbstractMonster.EnemyType.ELITE || monster.type == AbstractMonster.EnemyType.BOSS)
                        && ((monster.isDying || monster.currentHealth <= 0) && !monster.halfDead))
                {
                    krul.ObtainReward();
                }
            }
        }

        this.tickDuration();
    }
}
