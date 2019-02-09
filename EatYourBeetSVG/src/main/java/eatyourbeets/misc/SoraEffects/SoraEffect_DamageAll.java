package eatyourbeets.misc.SoraEffects;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import eatyourbeets.GameActionsHelper;


public class SoraEffect_DamageAll extends SoraEffect
{
    public SoraEffect_DamageAll(int descriptionIndex, int nameIndex)
    {
        super(descriptionIndex,nameIndex);
        sora.baseDamage = sora.damage = 6;
        sora.SetMultiDamage(true);
    }

    @Override
    public void EnqueueAction(AbstractPlayer player)
    {
        GameActionsHelper.DamageAllEnemies(player, sora.multiDamage, sora.damageTypeForTurn, AbstractGameAction.AttackEffect.SHIELD);
    }
}