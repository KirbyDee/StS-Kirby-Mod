package theSorcerer.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

public class DarkLightningEffect extends AbstractGameEffect {

    private final float x;
    private final float y;
    private TextureAtlas.AtlasRegion img = null;

    public DarkLightningEffect(float x, float y) {
        this.img = ImageMaster.vfxAtlas.findRegion("combat/lightning");

        this.x = x - (float)this.img.packedWidth / 2.0F;
        this.y = y;
        this.color = Color.VIOLET.cpy();
        this.duration = 0.5F;
        this.startingDuration = 0.5F;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, true);

            for (int i = 0; i < 50; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(new ImpactSparkEffect(this.x + MathUtils.random(-40.0F, 40.0F) * Settings.scale + 200.0F * Settings.scale, this.y + MathUtils.random(-40.0F, 40.0F) * Settings.scale));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.color.a = Interpolation.bounceIn.apply(this.duration * 5.0F);
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}