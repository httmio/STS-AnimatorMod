package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class YunYun extends AnimatorCard
{
    public static final String ID = CreateFullID(YunYun.class.getSimpleName());

    public YunYun()
    {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        Initialize(10, 0);

        this.isMultiDamage = true;

        SetSynergy(Synergies.Konosuba);
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();
        int newCost = AbstractDungeon.player.hand.getAttacks().size();
        if (AbstractDungeon.player.hand.contains(this))
        {
            newCost = Math.max(0, newCost - 1);
        }

        this.setCostForTurn(newCost);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        GameActionsHelper.DamageAllEnemies(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeDamage(4);
        }
    }
}