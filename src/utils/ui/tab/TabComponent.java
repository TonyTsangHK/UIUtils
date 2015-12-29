package utils.ui.tab;

import java.awt.*;
import java.util.*;

public interface TabComponent {
     public Component getComponent();
     public void addTabListener(TabListener listener);
     public void removeTabListener(TabListener listener);
     public void removeTabListener(int i);
     public TabListener getTabListener(int i);
     public ArrayList<TabListener> getTabListeners();
     public boolean fireTabEvent(TabEvent.Type eventType);
}