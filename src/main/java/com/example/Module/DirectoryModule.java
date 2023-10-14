package com.example.Module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component("directoryModule")
public class DirectoryModule implements Module{

    int d;
    @Value(value = "#{${directoryModuleTitle}}")
    private String moduleName;
    @Value(value = "#{${directoryModuleExtensions}}")
    private List<String> extensions;

    @Value(value = "#{${directoryModuleMethods}}")
    private List<String> methods;


    @Override
    public String getModuleName(){
        return moduleName;
    }
    @Override
    public List<String> getMethods(){
        return methods;
    }
    @Override
    public boolean isSuitableExtension(String extension){
        return extensions.contains(extension);
    }

    @Override
    public void getAllowableMethods() {
        d = 1;
        System.out.println("Доступные функции:");
        for (String method : methods) {
            System.out.printf(method + "(%d)\n", d);
            d++;
        }
    }

    @Override
    public void doNeededMethod(int d, File file) {
        if(d == 1){
            getDirectoryFiles(file);
        }else if(d == 2){
            getFilesSize(file);
        }else if(d == 3){
            getSubDirCount(file);
        }
    }
    public void getDirectoryFiles(File file){

        if(file.isDirectory()){
            File[] files = file.listFiles();
            System.out.println(file.getName() + " has files:");
            for(int i = 0; i < files.length; i++){
                if(files[i].isFile()){
                    System.out.println(files[i].getName());
                }
            }
        }
    }

    public void getFilesSize(File file){
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            int sum = 0;
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    sum += files[i].length();
                }
            }
            System.out.println("Size of all files in directory: " + sum + " B");
        }
    }

    public void getSubDirCount(File file){
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            int n = 0;
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    n ++;
                }
            }
            System.out.println(file.getName() + " has " + n + " subdirectories");
        }
    }
    public int getD(){
        return d;
    }
}
