import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.Module.Module;

public class Main {
    private static ApplicationContext applicationContext;

    public static void main(String[] args){
       applicationContext = new AnnotationConfigApplicationContext(App.class);
       App.modules.put(1, (Module)applicationContext.getBean("directoryModule"));
       App.modules.put(2, (Module)applicationContext.getBean("imageModule"));
       App.modules.put(3, (Module)applicationContext.getBean("mp3Module"));
       App.modules.put(4, (Module)applicationContext.getBean("textModule"));
      /*  for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }*/

        App.Start();
    }


}
