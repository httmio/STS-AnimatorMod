package eatyourbeets.actions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.animator.KrulTepes;

public class KrulTepesAction extends AnimatorAction
{
    //private final DamageInfo info;
    private final KrulTepes krul;

    public KrulTepesAction(AbstractCreature target, KrulTepes krul)
    {
        //this.info = info;
        //this.setValues(target, info);
        this.target = target;
        this.krul = krul;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
    }

    public void update()
    {
        if (krul.CanGetReward())
        {
            AbstractMonster monster = ((AbstractMonster)this.target);
            if ((monster.type == AbstractMonster.EnemyType.ELITE || monster.type == AbstractMonster.EnemyType.BOSS))
            {
                krul.ObtainReward();
            }
        }

        this.isDone = true;

//        if (this.duration == 0.1F && this.target != null)
//        {
//            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
//            AbstractMonster monster = ((AbstractMonster)this.target);
//
//            monster.damage(this.info);
//
//            if (krul.CanGetReward())
//            {
//                if ((monster.type == AbstractMonster.EnemyType.ELITE || monster.type == AbstractMonster.EnemyType.BOSS)
//                        && ((monster.isDying || monster.currentHealth <= 0) && !monster.halfDead))
//                {
//                    krul.ObtainReward();
//                }
//            }
//        }
//
//        this.tickDuration();
    }
}
