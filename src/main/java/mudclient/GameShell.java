package mudclient;

import java.io.IOException;
import java.util.Arrays;

import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.KeyboardEvent;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;
//import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.TextRectangle;

public class GameShell {
    public static GameFrame gameFrame = null;
    private static String charMap = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
    public int mouseActionTimeout;
    public int loadingStep;
    public String logoHeaderText;
    public boolean keyLeft;
    public boolean keyRight;
    public boolean keyUp;
    public boolean keyDown;
    public boolean keySpace;
    public int threadSleep;
    public int mouseX;
    public int mouseY;
    public int mouseButtonDown;
    public int lastMouseButtonDown;
    public boolean interlace;
    public String inputTextCurrent;
    public String inputTextFinal;
    public String inputPmCurrent;
    public String inputPmFinal;
    public int appletWidth;
    public int appletHeight;
    private int targetFps;
    private int maxDrawTime;
    private long timings[];
    private int stopTimeout;
    private int interlaceTimer;
    private int loadingProgressPercent;
    private String loadingProgessText;
    protected int fps;

    public HTMLCanvasElement canvas;
    public CanvasRenderingContext2D context;
    public ImageData imageData;

    public GameShell() {
        appletWidth = 512;
        appletHeight = 344;
        targetFps = 20;
        maxDrawTime = 1000;
        timings = new long[10];
        loadingStep = 1;
        loadingProgessText = "Loading";
        keyLeft = false;
        keyRight = false;
        keyUp = false;
        keyDown = false;
        keySpace = false;
        threadSleep = 1;
        interlace = false;
        inputTextCurrent = "";
        inputTextFinal = "";
        inputPmCurrent = "";
        inputPmFinal = "";
    }

    private void setMousePosition(MouseEvent event) {
        TextRectangle boundingRect = canvas.getBoundingClientRect();
        double scaleX = canvas.getWidth() / boundingRect.getWidth();
        double scaleY = canvas.getHeight() / boundingRect.getHeight();

        this.mouseX = (int) ((event.getClientX() - boundingRect.getLeft()) * scaleX);
        this.mouseY = (int) ((event.getClientY() - boundingRect.getTop()) * scaleY);
    }

    protected void startGame() {
    }

    protected void handleInputs() {
    }

    protected void onClosing() {
    }

    protected void draw() {
    }

    protected void startApplication(int width, int height, String title, boolean resizeable) {
        System.out.println("Started application");
        appletWidth = width;
        appletHeight = height;
        gameFrame = new GameFrame(this, width, height, title, resizeable, false);
        loadingStep = 1;

        this.canvas =
            (HTMLCanvasElement) HTMLDocument.current().createElement("canvas");

        this.canvas.setAttribute("tabindex", "-1");
        this.canvas.setWidth(appletWidth);
        this.canvas.setHeight(appletHeight);

        this.context = (CanvasRenderingContext2D) this.canvas.getContext("2d");

        this.imageData =
            (ImageData) this.context.createImageData(appletWidth, appletHeight);

        HTMLDocument.current().getBody().appendChild(this.canvas);

        this.canvas.addEventListener("mousedown", new EventListener<MouseEvent>(){
            public void handleEvent(MouseEvent event) {
                setMousePosition(event);
                mousePressed(event.getButton() == 2 ? 2 : 1);
            }
        });

        this.canvas.addEventListener("mouseup", new EventListener<MouseEvent>(){
            public void handleEvent(MouseEvent event) {
                setMousePosition(event);
                mouseReleased();
            }
        });

        this.canvas.addEventListener("mousemove", new EventListener<MouseEvent>(){
            public void handleEvent(MouseEvent event) {
                setMousePosition(event);
                mouseMoved();
            }
        });

        this.canvas.addEventListener("contextmenu", new EventListener<MouseEvent>(){
            public void handleEvent(MouseEvent event) {
                event.preventDefault();
            }
        });

        this.canvas.addEventListener("keydown", new EventListener<KeyboardEvent>(){
            public void handleEvent(KeyboardEvent event) {
                int code = event.getKeyCode();

                char charCode =
                    event.getKey().length() == 1 ? event.getKey().charAt(0) : 65535;

                if (code == 8 || code == 13 || code == 10 || code == 9) {
                    charCode = (char) code;
                }

                keyPressed(charCode, code);
            }
        });

        this.canvas.addEventListener("keyup", new EventListener<KeyboardEvent>(){
            public void handleEvent(KeyboardEvent event) {
                int code = event.getKeyCode();
                keyReleased(code);
            }
        });

        start();
        run();
    }

    protected void setTargetFps(int i) {
        targetFps = 1000 / i;
    }

    protected void resetTimings() {
        for (int i = 0; i < 10; i++) {
            timings[i] = 0L;
        }
    }

    public void keyPressed(char chr, int code) {
        handleKeyPress(chr);
        mouseActionTimeout = 0;

        if (code == KeyEvent.VK_LEFT) {
            keyLeft = true;
        } else if (code == KeyEvent.VK_RIGHT) {
            keyRight = true;
        } else if (code == KeyEvent.VK_UP) {
            keyUp = true;
        } else if (code == KeyEvent.VK_DOWN) {
            keyDown = true;
        } else if (code == KeyEvent.VK_SPACE) {
            keySpace = true;
        } else if (code == KeyEvent.VK_F1) {
            interlace = !interlace;
        } else {
            boolean foundText = false;

            for (int i = 0; i < charMap.length(); i++) {
                if (charMap.charAt(i) == chr) {
                    foundText = true;
                    break;
                }
            }

            if (foundText) {
                if (inputTextCurrent.length() < 20) {
                    inputTextCurrent += chr;
                }
                if (inputPmCurrent.length() < 80) {
                    inputPmCurrent += chr;
                }
            }
        }
        if (code == KeyEvent.VK_BACK_SPACE) {
            if (inputTextCurrent.length() > 0) {
                inputTextCurrent = inputTextCurrent.substring(0, inputTextCurrent.length() - 1);
            }
            if (inputPmCurrent.length() > 0) {
                inputPmCurrent = inputPmCurrent.substring(0, inputPmCurrent.length() - 1);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            inputTextFinal = inputTextCurrent;
            inputPmFinal = inputPmCurrent;
        }
    }

    protected void handleKeyPress(int i) {
    }


    public void keyReleased(int code) {
        if (code == KeyEvent.VK_LEFT) {
            keyLeft = false;
        } else if (code == KeyEvent.VK_RIGHT) {
            keyRight = false;
        } else if (code == KeyEvent.VK_UP) {
            keyUp = false;
        } else if (code == KeyEvent.VK_DOWN) {
            keyDown = false;
        } else if (code == KeyEvent.VK_SPACE) {
            keySpace = false;
        }
    }

    public void mouseMoved() {
        mouseButtonDown = 0;
        mouseActionTimeout = 0;
    }

    public void mouseReleased() {
        mouseButtonDown = 0;
    }

    public void mousePressed(int button) {
        mouseButtonDown = button;
        lastMouseButtonDown = mouseButtonDown;
        mouseActionTimeout = 0;
        handleMouseDown(mouseButtonDown, this.mouseX, this.mouseY);
    }

    public void mouseDragged(int x, int y, int button) {
        mouseX = x;
        mouseY = y;
        mouseButtonDown = button;
    }

    protected void handleMouseDown(int i, int j, int k) {
    }

    public void start() {
        if (stopTimeout >= 0)
            stopTimeout = 0;
    }

    public void stop() {
        if (stopTimeout >= 0)
            stopTimeout = 4000 / targetFps;
    }

    public void destroy() {
        stopTimeout = -1;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        if (stopTimeout == -1) {
            System.out.println("5 seconds expired, forcing kill");
            closeProgram();
        }
    }

    private void closeProgram() {
        stopTimeout = -2;
        System.out.println("Closing program");
        onClosing();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        //System.exit(0);
    }

    public void run() {
        if (loadingStep == 1) {
            loadingStep = 2;
            loadJagex();
            drawLoadingScreen(0, "Loading...");
            startGame();
            loadingStep = 0;
        }

        int i = 0;
        int j = 256;
        int sleep = 1;
        int i1 = 0;

        for (int j1 = 0; j1 < 10; j1++) {
            timings[j1] = System.currentTimeMillis();
        }

        while (stopTimeout >= 0) {
            if (stopTimeout > 0) {
                stopTimeout--;
                if (stopTimeout == 0) {
                    closeProgram();
                    return;
                }
            }
            int k1 = j;
            int lastSleep = sleep;
            j = 300;
            sleep = 1;
            long time = System.currentTimeMillis();
            if (timings[i] == 0L) {
                j = k1;
                sleep = lastSleep;
            } else if (time > timings[i])
                j = (int) ((long) (2560 * targetFps) / (time - timings[i]));
            if (j < 25)
                j = 25;
            if (j > 256) {
                j = 256;
                sleep = (int) ((long) targetFps - (time - timings[i]) / 10L);
                if (sleep < threadSleep)
                    sleep = threadSleep;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
            }
            timings[i] = time;
            i = (i + 1) % 10;
            if (sleep > 1) {
                for (int j2 = 0; j2 < 10; j2++)
                    if (timings[j2] != 0L)
                        timings[j2] += sleep;

            }
            int k2 = 0;
            while (i1 < 256) {
                handleInputs();
                i1 += j;
                if (++k2 > maxDrawTime) {
                    i1 = 0;
                    interlaceTimer += 6;
                    if (interlaceTimer > 25) {
                        interlaceTimer = 0;
                        interlace = true;
                    }
                    break;
                }
            }
            interlaceTimer--;
            i1 &= 0xff;
            draw();

            this.fps = (1000 * j) / (this.targetFps * 256);
        }

        if (stopTimeout == -1)
            closeProgram();
    }

    private void loadJagex() {
        //graphics.setColor(Color.black);
        //graphics.fillRect(0, 0, appletWidth, appletHeight);

        byte buff[] = readDataFile("jagex.jag", "Jagex library", 0);

        if (buff != null) {
            byte logo[] = Utility.loadData("logo.tga", 0, buff);
            //imageLogo = createImage(logo);
        }

        buff = readDataFile("fonts" + Version.FONTS + ".jag", "Game fonts", 5);

        if (buff != null) {
            Surface.createFont(Utility.loadData("h11p.jf", 0, buff), 0);
            Surface.createFont(Utility.loadData("h12b.jf", 0, buff), 1);
            Surface.createFont(Utility.loadData("h12p.jf", 0, buff), 2);
            Surface.createFont(Utility.loadData("h13b.jf", 0, buff), 3);
            Surface.createFont(Utility.loadData("h14b.jf", 0, buff), 4);
            Surface.createFont(Utility.loadData("h16b.jf", 0, buff), 5);
            Surface.createFont(Utility.loadData("h20b.jf", 0, buff), 6);
            Surface.createFont(Utility.loadData("h24b.jf", 0, buff), 7);
        }
    }

    private void drawLoadingScreen(int percent, String text) {
    }

    protected void showLoadingProgress(int i, String s) {}

    protected byte[] readDataFile(String file, String description, int percent) {
        file = "./data204/" + file;
        int archiveSize = 0;
        int archiveSizeCompressed = 0;
        byte archiveData[] = null;

        try {
            showLoadingProgress(percent, "Loading " + description + " - 0%");
            FileDownloadStream datainputstream = new FileDownloadStream(file);
            byte header[] = new byte[6];
            datainputstream.readFully(header, 0, 6);
            archiveSize = ((header[0] & 0xff) << 16) + ((header[1] & 0xff) << 8) + (header[2] & 0xff);
            archiveSizeCompressed = ((header[3] & 0xff) << 16) + ((header[4] & 0xff) << 8) + (header[5] & 0xff);
            showLoadingProgress(percent, "Loading " + description + " - 5%");
            int read = 0;
            archiveData = new byte[archiveSizeCompressed];
            while (read < archiveSizeCompressed) {
                int length = archiveSizeCompressed - read;
                if (length > 1000)
                    length = 1000;
                datainputstream.readFully(archiveData, read, length);
                read += length;
                showLoadingProgress(percent, "Loading " + description + " - " + (5 + (read * 95) / archiveSizeCompressed) + "%");
            }
            //datainputstream.close();
        } catch (IOException ignored) {
        }

        showLoadingProgress(percent, "Unpacking " + description);

        if (archiveSizeCompressed != archiveSize) {
            byte decompressed[] = new byte[archiveSize];
            BZLib.decompress(decompressed, archiveSize, archiveData, archiveSizeCompressed, 0);
            return decompressed;
        } else {
            return archiveData;
        }
    }
}
