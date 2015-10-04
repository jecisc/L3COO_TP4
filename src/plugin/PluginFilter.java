package plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;

/**
 * This class will allow us to select some files in a folder.
 */
public class PluginFilter implements FilenameFilter {

	/**
	 * Check if a class implements plugins.Plugin's interface.
	 * @param classFile the class.
	 * @return true if the class implements plugins.Plugin's interface.
	 */
	private boolean isInterface(Class<?> classFile){
		try{
			return Plugin.class.isAssignableFrom(classFile);
		}catch (NullPointerException e){
			return false;
		}
	}
	
	/**
	 * Check if a class is on the plugins's package.
	 * @param classFile the class
	 * @return true if the class is from plugins's package.
	 */
	private boolean isFromPlugins(Class<?> classFile){
		Package actualPackage = classFile.getPackage();
		String pakageName = actualPackage.getName();
		return pakageName.equals("plugins");
	}
	
	/**
	 * Check if a class have a constructor without parameter.
	 * @param classFile the class
	 * @return true if the class have a constructor without parameter.
	 */
	private boolean haveConstructorWithoutParameter(Class<?> classFile){
		for(Constructor<?> constructor : classFile.getConstructors()){
			if (constructor.getParameterTypes().length ==0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if a file's name finish by ".class"
	 * @param name the file
	 * @return true if the name of the file finish by file.
	 */
	private boolean finishByClass(String name){
		return name.endsWith(".class");
	}
	
	/**
	 * Try to get the class name in the directory.
	 * @param name
	 * @return
	 */
	private Class<?> getClasse(String name) {
		try{
			return Class.forName("plugins."+(name.replaceAll("\\.class$", "")));
		}catch(ClassNotFoundException e){
			throw new Error("Class not found");
		}catch (NoClassDefFoundError e){
			return null;
		}
	}
	
	/**
	 * The method accept allow to know if a file finish by ".class" or not.
	 * @return true if the File's name finish by ".class"
	 */
	public boolean accept(File dir, String name) {
		if (finishByClass(name)){
			Class<?> classFile = this.getClasse(name);
			return isInterface(classFile) && isFromPlugins(classFile) && haveConstructorWithoutParameter(classFile);
		}
		return false;
	}

}