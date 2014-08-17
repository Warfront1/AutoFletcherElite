package scripts;

import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;

import scripts.userinterface.Paint;

@ScriptManifest(authors = { "Warfront1" }, category = "Fletching", name = "Auto Fletcher Elite V2")
public class TribotFrontEnd extends Script implements Painting, MouseActions{

	@Override
	public void run() {
        AutoFletcherEliteV2.runFrontEndViaJavaFX();
	}
	
	@Override
	public void onPaint(final Graphics g) {
		AutoFletcherEliteV2.onPaint(g);
	}

	@Override
	public void mouseClicked(Point arg0, int arg1, boolean arg2) {
		Paint.PaintToggleHandeling(General.getRealMousePos());
	}

	@Override
	public void mouseDragged(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(Point arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

}
