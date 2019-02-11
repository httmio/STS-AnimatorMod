package eatyourbeets.cards.animator;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.actions.ModifyMagicNumberPermanentlyAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Priestess extends AnimatorCard
{
    public static final String ID = CreateFullID(Priestess.class.getSimpleName());
    private static final int COOLDOWN = 3;

    public Priestess()
    {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);

        Initialize(0, 0, 3);

        this.tags.add(CardTags.HEALING);
        this.baseSecondaryValue = this.secondaryValue = 4;
        this.misc = COOLDOWN;
        SetSynergy(Synergies.GoblinSlayer);
    }

    @Override
    public void applyPowers()
    {
        this.magicNumber = this.misc;
        super.applyPowers();
        initializeDescription();
        this.isMagicNumberModified = (this.magicNumber == 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (ProgressCooldown())
        {
            GameActionsHelper.ApplyPower(p, p, new RegenPower(p, this.secondaryValue), this.secondaryValue);
        }

        if (HasActiveSynergy())
        {
            GameActionsHelper.Special(new AddTemporaryHPAction(p, p, this.secondaryValue));
        }
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeSecondaryValue(1);
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