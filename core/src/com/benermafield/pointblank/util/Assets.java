package com.benermafield.pointblank.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureAtlas uiAtlas;
    public static TextureRegion blueButtonRectangle;
    public static TextureRegion blueButtonRectanglePressed;
    public static TextureRegion blueButtonSquare;
    public static TextureRegion blueButtonSquarePressed;
    public static TextureRegion target;

    // Fonts
    public static BitmapFont uiFont;
    public static BitmapFont buttonFont;

    //public static Music music;
    public static Sound clickSound;
    public static Sound pop1Sound;
    public static Sound pop2Sound;
    public static Sound pop3Sound;

    public static void load () {
        uiAtlas = new TextureAtlas(Gdx.files.internal("ui_sheets/uiSpriteSheet.atlas"));
        blueButtonRectangle = uiAtlas.findRegion("blue_button_rectangle");
        blueButtonRectanglePressed = uiAtlas.findRegion("blue_button_rectangle_pressed");
        blueButtonSquare = uiAtlas.findRegion("blue_button_square");
        blueButtonSquarePressed = uiAtlas.findRegion("blue_button_square_pressed");
        target = uiAtlas.findRegion("target");

        uiFont = new BitmapFont(Gdx.files.internal("fonts/boxy_bold_32.fnt"), Gdx.files.internal("fonts/boxy_bold_32_0.png"), false);
        buttonFont = new BitmapFont();
        //font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), Gdx.files.internal("data/font.png"), false);

        /*music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        if (Settings.soundEnabled) music.play();*/
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
        pop1Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/pop1.ogg"));
        pop2Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/pop2.ogg"));
        pop3Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/pop3.ogg"));
    }

    public static Sound randomPop() {
        double random = Math.random() * 3;
        if (random < 1) {
            return pop1Sound;
        } else if (random < 2) {
            return pop2Sound;
        } else {
            return pop3Sound;
        }
    }

    public static void playSound (Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }
}
