package eatyourbeets.misc.SoraEffects;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.StrengthPower;
import eatyourbeets.GameActionsHelper;

public class SoraEffect_GainStrength extends SoraEffect
{
    public SoraEffect_GainStrength(int descriptionIndex, int nameIndex)
    {
        super(descriptionIndex,nameIndex);
        sora.baseMagicNumber = sora.magicNumber = 1;
    }

    @Override
    public void EnqueueAction(AbstractPlayer player)
    {
        GameActionsHelper.ApplyPower(player, player, new StrengthPower(player, sora.magicNumber));
    }
}