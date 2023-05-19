package theSorcerer.audio;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CustomSoundMaster {

    private static final Logger LOG = LogManager.getLogger(CustomSoundMaster.class.getName());

    private static final String SFX_DIR = "theSorcererResources/audio/sound/";

    private final HashMap<String, Sfx> map = new HashMap<>();
    private final ArrayList<SoundInfo> fadeOutList = new ArrayList<>();

    public CustomSoundMaster() {
        long startTime = System.currentTimeMillis();
        Settings.SOUND_VOLUME = Settings.soundPref.getFloat("Sound Volume", 0.5F);
        this.map.put("SHEEP", this.load("STS_SFX_Sheep.ogg"));
        this.map.put("CAVE_DRIPPING", this.load("STS_SFX_CaveDripping.ogg"));
        LOG.info("Sound Effect Volume: " + Settings.SOUND_VOLUME);
        LOG.info("Loaded " + this.map.size() + " Sound Effects");
        LOG.info("SFX load time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private Sfx load(final String filename) {
        return this.load(filename, false);
    }

    private Sfx load(final String filename, boolean preload) {
        return new Sfx(SFX_DIR + filename, preload);
    }

    public void update() {
        Iterator<SoundInfo> i = this.fadeOutList.iterator();

        while(i.hasNext()) {
            SoundInfo e = i.next();
            e.update();
            Sfx sfx = this.map.get(e.name);
            if (sfx != null) {
                if (e.isDone) {
                    sfx.stop(e.id);
                    i.remove();
                } else {
                    sfx.setVolume(e.id, Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * e.volumeMultiplier);
                }
            }
        }
    }

    public void preload(final String key) {
        if (this.map.containsKey(key)) {
            LOG.info("Preloading: " + key);
            long id = this.map.get(key).play(0.0F);
            this.map.get(key).stop(id);
        }
        else {
            LOG.info("Missing: " + key);
        }

    }

    public long play(final String key, final boolean useBgmVolume) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded) {
            return 0L;
        }
        else if (this.map.containsKey(key)) {
            return useBgmVolume ? this.map.get(key).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME) : ((Sfx)this.map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
        }
        else {
            LOG.info("Missing: " + key);
            return 0L;
        }
    }

    public long play(final String key) {
        return CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded ? 0L : this.play(key, false);
    }

    public long play(final String key, float pitchVariation) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded) {
            return 0L;
        }
        else if (this.map.containsKey(key)) {
            return ((Sfx)this.map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0F + MathUtils.random(-pitchVariation, pitchVariation), 0.0F);
        }
        else {
            LOG.info("Missing: " + key);
            return 0L;
        }
    }

    public long playA(final String key, final float pitchAdjust) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded) {
            return 0L;
        }
        else if (this.map.containsKey(key)) {
            return this.map.get(key).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0F + pitchAdjust, 0.0F);
        }
        else {
            LOG.info("Missing: " + key);
            return 0L;
        }
    }

    public long playV(final String key, final float volumeMod) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded) {
            return 0L;
        }
        else if (this.map.containsKey(key)) {
            return this.map.get(key).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * volumeMod, 1.0F, 0.0F);
        }
        else {
            LOG.info("Missing: " + key);
            return 0L;
        }
    }

    public long playAV(final String key, final float pitchAdjust, final float volumeMod) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded) {
            return 0L;
        }
        else if (this.map.containsKey(key)) {
            return this.map.get(key).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * volumeMod, 1.0F + pitchAdjust, 0.0F);
        }
        else {
            LOG.info("Missing: " + key);
            return 0L;
        }
    }

    public long playAndLoop(final String key) {
        if (this.map.containsKey(key)) {
            return this.map.get(key).loop(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
        }
        else {
            LOG.info("Missing: " + key);
            return 0L;
        }
    }

    public long playAndLoop(final String key, final float volume) {
        if (this.map.containsKey(key)) {
            return this.map.get(key).loop(volume);
        }
        else {
            LOG.info("Missing: " + key);
            return 0L;
        }
    }

    public void adjustVolume(final String key, final long id, final float volume) {
        this.map.get(key).setVolume(id, volume);
    }

    public void adjustVolume(final String key, final long id) {
        this.map.get(key).setVolume(id, Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
    }

    public void fadeOut(final String key, final long id) {
        this.fadeOutList.add(new SoundInfo(key, id));
    }

    public void stop(final String key, final long id) {
        this.map.get(key).stop(id);
    }

    public void stop(final String key) {
        if (this.map.get(key) != null) {
            this.map.get(key).stop();
        }

    }
}
