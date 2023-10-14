package com.example.Module;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

@Component("imageModule")
public class ImageModule implements  Module{

    int d;
    @Value(value = "#{${imageModuleTitle}}")
    private String moduleName;
    @Value(value = "#{${imageModuleExtensions}}")
    private List<String> extensions;
    @Value(value = "#{${imageModuleMethods}}")
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
            getImageSize(file);
        }else if(d == 2){
            getImageEXIF(file);
        }else if(d == 3){
            getImagePixelsCount(file);
        }
    }
    public void getImageSize(File file){
        try(InputStream fileReader = Files.newInputStream(file.toPath())){
            BufferedImage image = ImageIO.read(fileReader);
            int height = image.getHeight();
            int width = image.getWidth();
            System.out.println("Height : "+ height);
            System.out.println("Width : "+ width);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getImageEXIF(File file){
        try(InputStream fileReader = Files.newInputStream(file.toPath())){
            Metadata metadata = ImageMetadataReader.readMetadata(fileReader);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if(directory.getName().contains("Exif")){
                        System.out.format("%s = %s",
                                tag.getTagName(), tag.getDescription() + "\n");
                    }
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }

            }
        }catch (ImageProcessingException | IOException e){
            e.printStackTrace();
        }
    }

    public void getImagePixelsCount(File file){
        try(InputStream fileReader = Files.newInputStream(file.toPath())){
            BufferedImage image = ImageIO.read(fileReader);
            int pixels = image.getHeight() * image.getWidth();
            System.out.println("Pixels count : "+ pixels);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int getD(){
        return d;
    }
}
