package Auxiliar;

import Modelo.Entity;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Classe responsável por carregar dinamicamente classes de entidades
 * a partir de arquivos .class ou .java
 */
public class EntityLoader {
    
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    
    /**
     * Carrega uma entidade a partir de um arquivo .class ou .java
     * 
     * @param file Arquivo contendo a classe da entidade
     * @return Uma instância da entidade ou null se ocorrer um erro
     */
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
    
    /**
     * Carrega uma entidade a partir de um arquivo .class
     */
    private static Entity loadFromClassFile(File file) throws Exception {
        // Criar um diretório temporário para o carregamento da classe
        File tempDir = new File(TEMP_DIR, "entity_loader_" + System.currentTimeMillis());
        tempDir.mkdir();
        
        // Copiar o arquivo .class para o diretório temporário
        File targetFile = new File(tempDir, file.getName());
        Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        // Carregar a classe
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { tempDir.toURI().toURL() });
        String className = file.getName().replace(".class", "");
        Class<?> entityClass = classLoader.loadClass("Modelo." + className);
        
        // Verificar se a classe estende Entity
        if (!Entity.class.isAssignableFrom(entityClass)) {
            throw new Exception("A classe " + className + " não é uma entidade válida");
        }
        
        // Criar uma instância com construtor que aceita String (nome do arquivo de imagem)
        Constructor<?> constructor = entityClass.getConstructor();
        Entity entity = (Entity) constructor.newInstance();
        
        // Definir uma posição aleatória
        entity.setRandomPosition();
        
        return entity;
    }
    
    /**
     * Compila e carrega uma entidade a partir de um arquivo .java
     */
    private static Entity compileAndLoadFromJavaFile(File file) throws Exception {
        // Criar um diretório temporário para compilação
        File tempDir = new File(TEMP_DIR, "entity_compiler_" + System.currentTimeMillis());
        tempDir.mkdir();
        
        // Copiar o arquivo .java para o diretório temporário
        File targetFile = new File(tempDir, file.getName());
        Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        // Compilar o arquivo .java
        javax.tools.JavaCompiler compiler = javax.tools.ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new Exception("Compilador Java não disponível");
        }
        
        int result = compiler.run(null, null, null, targetFile.getAbsolutePath());
        if (result != 0) {
            throw new Exception("Erro ao compilar o arquivo " + file.getName());
        }
        
        // Carregar a classe compilada
        String className = file.getName().replace(".java", "");
        File classFile = new File(tempDir, className + ".class");
        
        return loadFromClassFile(classFile);
    }
}
