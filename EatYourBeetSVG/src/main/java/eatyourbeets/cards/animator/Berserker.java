package eatyourbeets.cards.animator;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.OnTargetBlockBreakAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Berserker extends AnimatorCard
{
    public static final String ID = CreateFullID(Berserker.class.getSimpleName());

    public Berserker()
    {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);

        Initialize(24,0);

        SetSynergy(Synergies.Fate);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new OnTargetBlockBreakAction(m,new ApplyPowerAction(m,p,new StunMonsterPower(m))));
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {          
            upgradeDamage(6);
        }
    }
}