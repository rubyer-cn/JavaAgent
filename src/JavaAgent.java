package auto.java.agent;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.io.PrintStream;
import javax.swing.JButton;

public class JavaAgent
{
  public static long next_millis = 2100L;
  public static long stop_millis = 2100L;
  public static String title = "KTRAP(V1.23)";
  public static long exec_count = 0;
  public static boolean send = false;

  public static long cur_count = 0;

  public static void agentmain(String paramString) {
    System.out.println("java agent run ...");
    try
    {
      String[] arrayOfString = paramString.split(";");
      stop_millis = Long.parseLong(arrayOfString[0]);

      if (arrayOfString.length > 1)
      {
        exec_count = Long.parseLong(arrayOfString[1]);
      }

      if (arrayOfString.length > 2)
      {
        title = arrayOfString[2];
      }
    }
    catch (Exception localException) {
      System.out.println(localException);
    }

    System.out.println("stop_millis:" + String.valueOf(stop_millis));
    System.out.println("exec_count:" + String.valueOf(exec_count));
    System.out.println("title:" + title);

    new Thread(new Runnable() {
      public void run() {
        Frame localFrame = JavaAgent.getFrame(JavaAgent.title);

        if (localFrame != null) {
          JButton localJButton1 = JavaAgent.getJButton(localFrame, "·¢");

          if (localJButton1 == null) {
            System.out.println("get send button is null.");
          }
          else
            while (true)
            {
              if (exec_count > 0)
              {
                if (cur_count >= exec_count)
                {
                  System.out.println("exec finish.");
                  cur_count = 0;

                  return;
                }
              }

              if (localJButton1.isEnabled())
              {
                localJButton1.doClick();
                JavaAgent.send = true;

                cur_count += 1;
              }

              if (JavaAgent.send)
              {
                try {
                  Thread.sleep(JavaAgent.next_millis);
                } catch (Exception localException1) {
                }

                Dialog localDialog = JavaAgent.getDialog("¾¯¸æ");
                if (localDialog != null) {
                  JButton localJButton2 = JavaAgent.getJButton(localDialog, "ÊÇ(Y)");

                  if (localJButton2 != null) {
                    localJButton2.doClick();
                    JavaAgent.send = false;
                  } else {
                    System.out.println("get yes button is null.");
                  }
                }
              }

              try
              {
                Thread.sleep(JavaAgent.stop_millis);
              } catch (Exception localException2) {
              }
            }
        }
        else {
          System.out.println("get frame is null.");
        }
      }
    }).start();
  }

  public static Frame getFrame(String paramString)
  {
    Frame[] arrayOfFrame = Frame.getFrames();

    for (int i = 0; i < arrayOfFrame.length; i++) {
      if ((arrayOfFrame[i] != null) && (arrayOfFrame[i].getTitle().equals(paramString))) {
        return arrayOfFrame[i];
      }
    }

    return null;
  }

  public static Dialog getDialog(String paramString) {
    Window[] arrayOfWindow = Window.getWindows();

    for (int i = 0; i < arrayOfWindow.length; i++) {
      if ((arrayOfWindow[i] instanceof Dialog)) {
        Dialog localDialog = (Dialog)arrayOfWindow[i];

        if ((localDialog != null) && (localDialog.isShowing()) && (localDialog.getTitle().equals(paramString))) {
          return localDialog;
        }
      }
    }

    return null;
  }

  public static JButton getJButton(Container paramContainer, String paramString) {
    if (paramContainer == null) {
      return null;
    }

    Component[] arrayOfComponent = paramContainer.getComponents();
    for (int i = 0; i < arrayOfComponent.length; i++) {
      if ((arrayOfComponent[i] instanceof JButton)) {
        if (((JButton)arrayOfComponent[i]).getText().equals(paramString)) {
          return (JButton)arrayOfComponent[i];
        }

      }
      else if ((arrayOfComponent[i] instanceof Container)) {
        JButton localJButton = getJButton((Container)arrayOfComponent[i], paramString);

        if (localJButton != null) {
          return localJButton;
        }
      }
    }

    return null;
  }
}