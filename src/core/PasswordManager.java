package core;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class PasswordManager {
	public static void main(String[] args) {
		
		//Инициализация стандартного пакета классов
		UI ui = UI.getInstance();
		Data data = new Data();
		new Logic(ui, data);
		
		//Инициализация, компиляция и загрузка дополнительных пакетов 
		String modulesPath = "modules";
        File modulesFolder = new File(modulesPath);

        if (modulesFolder.exists() && modulesFolder.isDirectory()) {
            File[] packages = modulesFolder.listFiles(File::isDirectory);

            if (packages != null && packages.length > 0) {
                for (File pkg : packages) {
                    System.out.println("Найден пакет: " + pkg.getName());

                    try {
                        compileAndLoadClasses(modulesFolder, pkg.getName(), "Main");
                    } catch (Exception e) {
                        System.out.println("Ошибка обработки пакета " + pkg.getName() + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Папка 'modules' существует, но не содержит подпакетов.");
            }
        } else {
            System.out.println("Папка 'modules' не найдена.");
        }
    }

	//Метод для компиляции и загрузки классов
    private static void compileAndLoadClasses(File modulesFolder, String packageName, String targetClassName) {
        try {
            File packageFolder = new File(modulesFolder, packageName);

            File[] javaFiles = packageFolder.listFiles(file -> file.isFile() && file.getName().endsWith(".java"));

            if (javaFiles != null && javaFiles.length > 0) {
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                for (File javaFile : javaFiles) {
                    int result = compiler.run(null, null, null, javaFile.getPath());
                    if (result == 0) {
                        System.out.println("Скомпилирован файл: " + javaFile.getName());
                    } else {
                        System.out.println("Ошибка компиляции файла: " + javaFile.getName());
                    }
                }

                URL[] urls = {modulesFolder.toURI().toURL()};
                try (URLClassLoader classLoader = new URLClassLoader(urls)) {
                    File[] classFiles = packageFolder.listFiles(file -> file.isFile() && file.getName().endsWith(".class"));

                    if (classFiles != null && classFiles.length > 0) {
                        for (File classFile : classFiles) {
                            String className = classFile.getName().replace(".class", "");
                            String fullClassName = packageName + "." + className;

                            if (className.equals(targetClassName)) {
                                Class<?> loadedClass = classLoader.loadClass(fullClassName);
                                System.out.println("Загружен класс: " + loadedClass.getName());

                                // Создание экземпляра класса
                                Object instance = loadedClass.getDeclaredConstructor().newInstance();

                                // Вызов метода "execute" (например)
                                try {
                                    Method method = loadedClass.getMethod("Execute");
                                    method.invoke(instance); // Вызов метода без аргументов
                                    System.out.println("Метод 'execute' выполнен в классе " + fullClassName);
                                } catch (NoSuchMethodException e) {
                                    System.out.println("Метод 'execute' не найден в классе: " + loadedClass.getName());
                                }
                            }
                        }
                    } else {
                        System.out.println("Пакет " + packageName + " не содержит классов.");
                    }
                }
            } else {
                System.out.println("Пакет " + packageName + " не содержит файлов .java.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Класс не найден: " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("Конструктор или метод не найден: " + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Ошибка создания экземпляра класса: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Недостаточно прав для доступа к классу или методу: " + e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Ошибка выполнения метода: " + e.getCause());
            e.printStackTrace();
        }
    }
}
