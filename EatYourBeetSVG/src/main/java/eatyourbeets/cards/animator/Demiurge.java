package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Demiurge extends AnimatorCard
{
    public static final String ID = CreateFullID(Demiurge.class.getSimpleName());

    private boolean damagePlayer = false;

    public Demiurge()
    {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);

        Initialize(0,0,6);

        SetSynergy(Synergies.Overlord);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(upgraded ? 3 : 2));
        damagePlayer = true;
    }

    @Override
    public void triggerWhenDrawn()
    {
        super.triggerWhenDrawn();

        if (damagePlayer)
        {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.NORMAL)));
            damagePlayer = false;
        }
    }

    @Override
    public void upgrade() 
    {
        TryUpgrade();
        //if (TryUpgrade())
        //{
        //    upgradeMagicNumber(-2);
        //}
    }
}