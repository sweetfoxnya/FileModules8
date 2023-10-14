package com.example.Module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

@Component("textModule")
public class TextModule implements Module{

    private int d;
    @Value(value = "#{${textModuleTitle}}")
    private String moduleName;
    @Value(value = "#{${textModuleExtensions}}")
    private List<String> extensions;
    @Value(value = "#{${textModuleMethods}}")
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
            linesCount(file);
        }else if(d == 2){
            symbolFrequency(file);
        }else if(d == 3){
            wordsCount(file);
        }
    }
    public void linesCount(File file) {
        try(Scanner scanner = new Scanner(file)){
            int lines = 0;
            while (scanner.hasNextLine()) {
                lines++;
                scanner.nextLine();
            }
            System.out.println("Number of lines: " + lines);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void symbolFrequency(File file) {
        try(FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader)){

            Map<Character, Integer> result = new TreeMap<>();
            int c;
            while((c = (bufferedReader.read()) )!= -1) {

                if(c<14 || c == 32){
                    continue;
                }
                if(!result.containsKey((char)c)) {
                    result.put((char)c, 1);
                } else {
                    int number = result.get((char)c);
                    result.put((char)c, number + 1);
                }
            }

            for(Map.Entry<Character, Integer> item : result.entrySet()) {
                System.out.println(item.getKey() + " " + item.getValue());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void wordsCount(File file){
        try(Scanner scanner = new Scanner(file)) {
            int words = 0;

            while (scanner.hasNextLine()) {
                String[] array = scanner.nextLine().split(" ");
                words = words + array.length;
            }
            System.out.println("Number of words: " + words);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getD(){
        return d;
    }
}
