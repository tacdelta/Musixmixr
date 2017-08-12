import javax.sound.midi.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

/**
 * Created by td on 04.08.2017.
 */

public class Musxmixr {
    static JFrame f = new JFrame ( "1st music clip" );
    static DrawPanel m1;

    public static void main(String[] args) {
        Musxmixr mini = new Musxmixr ();
        mini.go ();
    }

    public void setUpGui() {
        m1 = new DrawPanel ();
        f.setContentPane ( m1 );
        f.setBounds ( 30, 30, 300, 300 );
        f.setVisible ( true );
    }

    public void go() {
        setUpGui ();

        try {
            Sequencer sequencer = MidiSystem.getSequencer ();
            sequencer.open ();
            sequencer.addControllerEventListener ( m1, new int[]{127} );
            Sequence seq = new Sequence ( Sequence.PPQ, 4 );
            Track track = seq.createTrack ();

            int r = 0;
            for (int i = 0; i < 60; i += 4) {
                r = (int) ((Math.random () * 50) + 1);
                track.add ( makeEvent ( 144, 1, r, 100, i ) );
                track.add ( makeEvent (176,1,127,0,i) );
                track.add ( makeEvent (128,1,r,100,i+2) );
            }

            sequencer.setSequence ( seq );
            sequencer.start ();
            sequencer.setTempoInBPM ( 120 );
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage ();
            a.setMessage ( comd, chan, one, two );
            event = new MidiEvent ( a, tick );
        }catch (Exception exc) {}
        return event;
    }

    class DrawPanel extends JPanel implements ControllerEventListener {
        boolean msg = false;

        public void controlChange (ShortMessage event) {
            msg = true;
            repaint ( );
        }

        public void paintComponent(Graphics g) {
            if(msg) {
                Graphics2D g2 = (Graphics2D) g;
                int red = (int) (Math.random () * 250);
                int green = (int) (Math.random () * 250);
                int blue = (int) (Math.random ()* 250);

                g.setColor (new Color( red,green,blue) );

                int height = (int) ((Math.random () * 120)+10);
                int width = (int) ((Math.random ()* 120)+10);

                int x=(int) ((Math.random () *40) +10);
                int y=(int) ((Math.random () *40) +10);

                g.fillRect ( x,y, height,width );
                msg = false;
            }
        }
    }
}

