/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ambilight.domain;

//imports for the screencolor calculation
import java.awt.Color;
import java.awt.image.BufferedImage;

//Test module
import java.awt.Graphics;
import static java.lang.Thread.sleep;
import javax.swing.JFrame;

/**
 *
 * @author Martin
 */
public class AmbilightProcessor {
    
    private int interval;
    private long lastUpdated;
    private int[] numberOfLeds;
    private Color[][] averageColors;
    private ScreenCapture screenCapture;
    
    /**
     * 
     * @param interval
     * @param numberOfLeds 
     */
    public AmbilightProcessor(int interval, int[] numberOfLeds)
    {
        this.interval = interval;
        this.numberOfLeds = numberOfLeds;
        this.averageColors = new Color[this.numberOfLeds[0]][this.numberOfLeds[1]];
        
        this.screenCapture =  new ScreenCapture();
        
        this.test();
        this.update();
    }
    
    /**
     * 
     * @return 
     */
    public int getInterval()
    {
        return this.interval;
    }
    
    /**
     * 
     * @param value 
     */
    public void setInterval(int value)
    {
        this.interval = value;
    }
    
    /**
     * 
     * @param image 
     */
    private void calculateAverageColors(BufferedImage image)
    {
        this.averageColors = new Color[this.numberOfLeds[0]][this.numberOfLeds[1]];
        int r, g, b;
        int pixelsScanned;
        int blockWidth = (int) Math.floor((double)image.getWidth() / this.numberOfLeds[0]);
        int blockHeight = (int) Math.floor((double)image.getHeight() / this.numberOfLeds[1]);
        for(int i = 0; i < this.numberOfLeds[0]; i++)
        {
            for(int j = 0; j < this.numberOfLeds[1]; j++)
            {
                if(i != 0 && i != this.numberOfLeds[0] - 1 &&
                   j != 0 && j != this.numberOfLeds[1] - 1)
                    continue;
                
                r = g = b = 0;
                pixelsScanned = 0;
                for(int y = i * blockHeight; y < i * blockHeight + blockHeight; y++)
                {
                    for(int x = j * blockWidth; x < j * blockWidth + blockWidth; x++)
                    {
                        int color = image.getRGB(x, y);
                        r += 0xff & (color>>16);
                        g += 0xff & (color>>8);
                        b += 0xff &  color;
                        pixelsScanned++;
                    }
                }
                averageColors[i][j] = new Color(
                    r / pixelsScanned,
                    g / pixelsScanned,
                    b / pixelsScanned
                );
            }
        }
    }
    
    /**
     * 
     */
    public void update()
    {
        try
        {
            lastUpdated = System.currentTimeMillis();

            calculateAverageColors(screenCapture.grabScreen());
            paintTestFrame(testFrame.getGraphics());

            long time = System.currentTimeMillis()-lastUpdated;
            if(time < interval)
                sleep(time);

            update();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     */
    private JFrame testFrame;
    public void test()
    {
        testFrame = new JFrame();
        testFrame.setSize(1920, 1080);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setVisible(true);
    }
    
    /**
     * 
     * @param g 
     */
    private void paintTestFrame(Graphics g)
    {
        int blockWidth = (int) Math.floor((double)1920 / this.numberOfLeds[0]);
        int blockHeight = (int) Math.floor((double)1080 / this.numberOfLeds[1]);
        for(int i = 0; i < this.averageColors.length; i++)
        {
            for(int j = 0; j < this.averageColors[i].length; j++)
            {
                Color averageColor = this.averageColors[i][j];
                if(averageColor == null)
                    averageColor = new Color(45,45,45);
                
                g.setColor(averageColor);
                g.fillRect(j*blockWidth, i*blockHeight, blockWidth, blockHeight);
            }
        }
    }
}
