import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.example.Module.Module;
import java.io.File;
import java.util.*;

@Configuration
@PropertySource("modules.properties")
@ComponentScan(basePackages = "com.example.Module")
public class App {

    public static Map<Integer, Module> modules = new HashMap<>();
    static Module neededModule;
    static String pathName;
    static File file;

    public static void Start(){

        boolean fileIsGotten = false;
        while(!fileIsGotten){
            fileIsGotten = getExistingFile();
        }

        getNeededModule(file);
        getNeededMethod(file);
    }

    public static void getNeededMethod(File file){
        System.out.print("\nВведите номер желаемого метода: ");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if(n <= neededModule.getD()){
            neededModule.doNeededMethod(n, file);
        }else{
            System.out.println("Метод не существует");
        }
    }
    public static void getNeededModule(File file){
        getAllowableModules(file);
        System.out.print("\nВведите номер желаемого модуля: ");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if(n <= modules.size()){
            modules.get(n).getAllowableMethods();
            neededModule = modules.get(n);
        }else{
            System.out.println("Модуль не существует");
        }
    }
    public static void getAllowableModules(File file){

        String ext = getExtension(file);
        System.out.print("Для файла доступны модули: ");
        for (Map.Entry<Integer, Module> entry : modules.entrySet()) {
            if (entry.getValue().isSuitableExtension(ext)) {
                System.out.printf(entry.getValue().getModuleName() + "(%d)\n", entry.getKey());
            }
        }
    }

    public static String getExtension(File file){
        String fileName = file.getName();

        if(file.isDirectory()){
            return "DIRECTORY";
        }else {
            // если в имени файла есть точка и она не является первым символом в названии файла
            if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
                return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            }
        }
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        return "";
    }
    public static boolean getExistingFile(){
        System.out.print("\nВведите имя файла: ");
        Scanner scanner = new Scanner(System.in);
        pathName = scanner.nextLine();
        file = new File(pathName);
        if(file.exists()){
            return true;
        }else{
            System.out.println("Файл не существует");
            return false;
        }
    }
}
