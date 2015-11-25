package mainProg.testingArea;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ScollCompositeControl {

  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());

    // this button is always 400 x 400. Scrollbars appear if the window is
    // resized to be
    // too small to show part of the button
    ScrolledComposite c1 = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    Button b1 = new Button(c1, SWT.PUSH);
    b1.setText("fixed size button");
    b1.setSize(400, 400);
    c1.setContent(b1);

    shell.setSize(600, 300);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }

}