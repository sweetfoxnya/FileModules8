package com.example.Module;

import java.io.File;
import java.util.List;

public interface Module {

  /*  public String functionDescription();

    public boolean isSupportedFormat(File file);

    public void function(File file);*/

    String getModuleName();

    List<String> getMethods();

    boolean isSuitableExtension(String extension);
    void getAllowableMethods();
    void doNeededMethod(int d, File file);
    int getD();
}
