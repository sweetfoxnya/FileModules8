package com.example.Module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component("mp3Module")
public class mp3Module implements Module{
    int d;
    @Value(value = "#{${audioModuleTitle}}")
    private String moduleName;

    @Value(value = "#{${audioModuleExtensions}}")
    private List<String> extensions;
    @Value(value = "#{${audioModuleMethods}}")
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
            getName(file);
        }else if(d == 2){
            getDuration(file);
        }else if(d == 3){
            getSize(file);
        }
    }
    public void getName(File file){
        System.out.println("Name: " + extractTag(file, "title"));
    }

    public void getDuration(File file){
        System.out.println("Duration: " + extractTag(file, "duration"));
    }

    public void getSize(File file){
        System.out.println("Size: " + extractTag(file, "size"));
    }

    public String extractTag(File file, String tag) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "ffprobe -v error -of flat -show_format " + "\"" + file.getAbsolutePath() + "\"")
                .directory(new File("C:\\ffmpeg\\bin"));

        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                if (line.contains(tag)) {
                    return line.split("=")[1].replace("\"", "");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public int getD(){
        return d;
    }
}
/*
 * cmd.exe — запуск командной оболочки
 * /c — параметр заставляет cmd запускать одну программу и выходить
 * ffprobe — утилита для получения информации о
 * -show_entries — установить список записей для просмотра
 * format_tags — формат тега
 * -of compact — установка компактного формата вывода
 * -v 0 — установить уровень ведения журнала, показывать только неустранимые ошибки
 * */
