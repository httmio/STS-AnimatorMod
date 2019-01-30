package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Ara extends AnimatorCard
{
    public static final String ID = CreateFullID(Ara.class.getSimpleName());

    public Ara()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);

        Initialize(4,0, 1);

        AddSynergy(Synergies.Elsword);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        if (m.currentBlock > 0 && m.currentBlock <= (this.damage * 2))
        {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {          
            //upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }
}