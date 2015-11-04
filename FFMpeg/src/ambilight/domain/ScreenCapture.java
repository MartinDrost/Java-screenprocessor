package ambilight.domain;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 *
 * @author Martin
 */
public class ScreenCapture {
    /**
     * Eerst maken we een field binnen de klasse voor de FFmpegFrameGrabber.
     * Omdat je waarschijnlijk maar 1 instantie van de klasse gaat gebruiken 
     * kan je hem het beste hier cachen en eenmaal instantiëren in de constructor
     */
    FFmpegFrameGrabber frameGrabber;
    
    /**
     * De framegrabber geeft een instantie van de Frame klasse van JavaCV terug.
     * Om deze om te kunnen zetten naar een BufferedImage kunnen we gebruik maken
     * van de Java2DFrameConverter.
     */
    Java2DFrameConverter frameConverter;
    
    /**
     * In dit attribuut gaan we de schermopname opslaan. 
     */
    private BufferedImage image;
    
    
    /**
     * In de constructor vullen we de hierboven gedefinieerde attributen.
     */
    public ScreenCapture()
    {
        try {
            
            this.frameConverter = new Java2DFrameConverter();
            this.image = null;

            /**
             * Hier definieren we de instellingen voor de frameGrabber.
             */
            
            /**
             * de FFmpegFrameGrabber krijgt als parameters een string met ":0.0+0,0".
             * Dit doen we om aan te geven dat we het 1e aangesloten beeldscherm willen
             * gebruiken als input apparaat. Hier kunnen andere strings worden 
             * ingevoerd om bijvoorbeeld een ander beeldscherm te selecteren, 
             * een media apparaat of een videobestand.
             * Bij setFormat we het formaat van de videodrivers. 
             * 
             * Deze instellingen zijn voor voor elk OS
             * anders, hieronder een lijstje met de verschillende parameters:
             * Linux: 
                * FFmpegFrameGrabber(":0.0+0,0")
                * setFormat("x11grab")
             * 
             * Windows: 
                * FFmpegFrameGrabber("desktop")
                * setFormat("gdigrab")
             * als deze niet werkt:
                * FFmpegFrameGrabber("video=screen-capture-recorder")
                * setFormat("dshow")
             * 
             */
            this.frameGrabber = new FFmpegFrameGrabber("desktop");
            this.frameGrabber.setFormat("gdigrab");
            
            /**
             * Hier stellen we de breedte en de hoogte van de schermopname in.
             */
            this.frameGrabber.setImageWidth(Toolkit.getDefaultToolkit().getScreenSize().width);
            this.frameGrabber.setImageHeight(Toolkit.getDefaultToolkit().getScreenSize().height);
            
            /**
             * En vervolgens starten we de schermopname.
             */
            this.frameGrabber.start();
        } catch (FrameGrabber.Exception ex) {
            Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, ex);
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
             * Hier roepen we de grabFrame methode van de frameGrabber aan en zetten
             * deze om naar een BufferedImage m.b.v. de frameConverter.convert() methode.
             */ 
            image = frameConverter.convert(this.frameGrabber.grabFrame(true, false, false));
        }
        catch(Exception e)
        {
            image = null;
            e.printStackTrace();
        }
        
        return image;
    }
    
}
