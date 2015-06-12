/**====================================
/* ShapeBlaster!
/* ------------------------------------
/* code by Nico Poblete
/* Copyright (c) 2012, 2015 Nico Poblete
/**====================================**/
package ShapeBlaster;
import javax.swing.*;

public class Main {
  /** Load the main() class to execute ShapeBlaster! **/
  public static void main(String[] args) {
    JFrame frame = new JFrame("ShapeBlaster");
    ShapeBlasterPanel sbp = new ShapeBlasterPanel();
    
    frame.add(sbp);
    frame.pack();
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setResizable(false);
  }
}
