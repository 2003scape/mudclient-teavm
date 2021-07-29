package mudclient;
public class GameFrame {
    int windowWidth;
    int windowHeight;
    int translationMode;
    int windowYTranslation;
    GameShell gameShell;

    public GameFrame(GameShell game, int width, int height, String title, boolean resizable, boolean flag1) {
        windowYTranslation = 28;
        windowWidth = width;
        windowHeight = height;
        this.gameShell = game;

        if (flag1)
            windowYTranslation = 48;
        else
            windowYTranslation = 28;
    }
}
