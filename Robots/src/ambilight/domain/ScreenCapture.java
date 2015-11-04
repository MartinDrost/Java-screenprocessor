package ambilight.domain;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

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
    /**
     * Eerst maken we een field binnen de klasse voor de Robot.
     * Omdat je waarschijnlijk maar 1 instantie van de klasse gaat gebruiken 
     * kan je hem het beste hier cachen en eenmaal instantiëren in de constructor
     */
    private Robot robot;
    
    /**
     * Hier gaan we de hoogte en de breedte van ons scherm in opslaan.
     * We gaan ook dit attribuut vullen in de constructor.
     */
    private Rectangle screenSize;
    
    /**
     * In dit attribuut gaan we de schermopname opslaan. 
     */
    private BufferedImage image;
    
    
    /**
     * In de constructor vullen we de hierboven gedefinieerde attributen.
     */
    public ScreenCapture()
    {
        try
        {
            this.robot = new Robot();
            this.screenSize = new Rectangle(0, 0, 
                    Toolkit.getDefaultToolkit().getScreenSize().width,
                    Toolkit.getDefaultToolkit().getScreenSize().height);
            this.image = null;
        }
        catch(AWTException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * De grabScreen() methode maakt een schermopname van het huidige beeld op 
     * scherm. Dit beeld wordt teruggegeven in een BufferedImage, het gebruik
     * van dit object wordt later beschreven in de tutorial.
     * @return 
     */
    public BufferedImage grabScreen()
    {
        try
        {
            /**
             * Hier roepen we de .createScreenCapture() methode aan van de Robot
             * klasse. Deze methode verwacht een Rectangle object met de coordinaten,
             * breedte en hoogte van het gebied waar je een opname van wilt maken.
             * Deze rectangle hebben we al gemaakt en gevuld in de constructor en 
             * hebben we screenSize genoemd.
             */ 
            image = robot.createScreenCapture(this.screenSize);
        }
        catch(Exception e)
        {
            image = null;
            e.printStackTrace();
        }
        
        return image;
    }
    
}
