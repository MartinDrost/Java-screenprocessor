package ambilight.domain;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Martin
 */
public class ScreenCapture {
    //frameGrabber
    Robot robot;
    BufferedImage bufferedImage;
    
    //Variable to store the screen dimensions
    private Dimension screenSize;
    
    public ScreenCapture()
    {
        try
        {
            this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            robot = new Robot();
            bufferedImage = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public BufferedImage grabScreen()
    {
        bufferedImage = null;
        try
        {
            Rectangle area = new Rectangle(0, 0, this.screenSize.width, this.screenSize.height);
            bufferedImage = robot.createScreenCapture(area);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return bufferedImage;
    }
    
}
