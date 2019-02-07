package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.actions.ModifyMagicNumberPermanentlyAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Chung extends AnimatorCard
{
    public static final String ID = CreateFullID(Chung.class.getSimpleName());

    private static final int COOLDOWN = 3;

    public Chung()
    {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL);

        Initialize(16, 5, COOLDOWN);

        this.misc = COOLDOWN;
        this.isMultiDamage = true;
        SetSynergy(Synergies.Elsword);
    }

    @Override
    public void applyPowers()
    {
        this.magicNumber = this.misc;
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        if (ProgressCooldown())
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SHIELD));
        }
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeDamage(4);
            upgradeBlock(3);
        }
    }

    private boolean ProgressCooldown()
    {
        if (this.baseMagicNumber <= 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberPermanentlyAction(this.uuid, COOLDOWN));

            return true;
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberPermanentlyAction(this.uuid, -1));

            return false;
        }
    }
}