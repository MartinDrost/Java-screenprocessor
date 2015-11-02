    package ambilight.domain;

    import java.awt.Dimension;
    import java.awt.Toolkit;
    import java.awt.image.BufferedImage;
    import org.bytedeco.javacv.FFmpegFrameGrabber;
    import org.bytedeco.javacv.Java2DFrameConverter;

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
        FFmpegFrameGrabber frameGrabber;
        BufferedImage bufferedImage;
        Java2DFrameConverter frameConverter;
        private Dimension screenSize;

        public ScreenCapture()
        {
            try
            {
                this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frameConverter = new Java2DFrameConverter();
                bufferedImage = null;

                this.frameGrabber = new FFmpegFrameGrabber(":0.0+0,0");
                this.frameGrabber.setFrameRate(30);
                this.frameGrabber.setFormat("x11grab");
                this.frameGrabber.setImageWidth(screenSize.width);
                this.frameGrabber.setImageHeight(screenSize.height);
                this.frameGrabber.start();
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
                bufferedImage = frameConverter.convert(this.frameGrabber.grabFrame(true, false, false));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return bufferedImage;
        }
    }
