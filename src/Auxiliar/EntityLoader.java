package Auxiliar;

import Modelo.Entity;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class EntityLoader {
  private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

  public static Entity loadEntityFromFile(File file) {
    String fileName = file.getName();

    try {
      if (fileName.endsWith(".class")) {
        return loadFromClassFile(file);
      } else if (fileName.endsWith(".java")) {
        return compileAndLoadFromJavaFile(file);
      } else {
        System.out.println("Formato de arquivo não suportado: " + fileName);
        return null;
      }
    } catch (Exception e) {
      System.out.println("Erro ao carregar entidade: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private static Entity loadFromClassFile(File file) throws Exception {
    File tempDir = new File(TEMP_DIR, "entity_loader_" + System.currentTimeMillis());
    tempDir.mkdir();

    File targetFile = new File(tempDir, file.getName());
    Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { tempDir.toURI().toURL() });
    String className = file.getName().replace(".class", "");
    Class<?> entityClass = classLoader.loadClass("Modelo." + className);

    if (!Entity.class.isAssignableFrom(entityClass)) {
      throw new Exception("A classe " + className + " não é uma entidade válida");
    }

    Constructor<?> constructor = entityClass.getConstructor();
    Entity entity = (Entity) constructor.newInstance();

    entity.setRandomPosition();
    return entity;
  }

  private static Entity compileAndLoadFromJavaFile(File file) throws Exception {
    File tempDir = new File(TEMP_DIR, "entity_compiler_" + System.currentTimeMillis());
    tempDir.mkdir();

    File targetFile = new File(tempDir, file.getName());
    Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    javax.tools.JavaCompiler compiler = javax.tools.ToolProvider.getSystemJavaCompiler();
    if (compiler == null) {
      throw new Exception("Compilador Java não disponível");
    }

    int result = compiler.run(null, null, null, targetFile.getAbsolutePath());
    if (result != 0) {
      throw new Exception("Erro ao compilar o arquivo " + file.getName());
    }

    String className = file.getName().replace(".java", "");
    File classFile = new File(tempDir, className + ".class");

    return loadFromClassFile(classFile);
  }
}
