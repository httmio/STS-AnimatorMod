package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Mitsurugi extends AnimatorCard
{
    public static final String ID = CreateFullID(Mitsurugi.class.getSimpleName());

    public Mitsurugi()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);

        Initialize(14, 0, 1);

        SetSynergy(Synergies.Konosuba);
    }

    @Override
    public void triggerOnExhaust()
    {
        super.triggerOnExhaust();
        AbstractPlayer player = AbstractDungeon.player;
        GameActionsHelper.ApplyPower(player, player, new StrengthPower(player, this.magicNumber), this.magicNumber);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        return m != null && super.canUse(p, m) && (m.intent == AbstractMonster.Intent.ATTACK || m.intent == AbstractMonster.Intent.ATTACK_BUFF
                || m.intent == AbstractMonster.Intent.ATTACK_DEFEND || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        GameActionsHelper.DamageTarget(p, m, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeDamage(6);
            upgradeMagicNumber(1);
        }
    }
}