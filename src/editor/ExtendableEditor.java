package editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import plugin.Plugin;
import plugin.PluginEventListener;
import plugin.PluginFinder;

/**
 * An ExtendableEditor is the graphic version of the Plugin's project.
 */
public class ExtendableEditor{

	/**
	 * MyFrame is the Frame of the graphic interface. 
	 */
	public class MyFrame extends JFrame{

		private static final long serialVersionUID = 1L;
		
		/**
		 * The finder of the plugins.
		 */
		protected final PluginFinder finder;
		
		/**
		 * The panel of the interface.
		 */
		protected final MyPanel panel;
		
		protected final static int l = 800, h=500;
		
		
		/**
		 * Constructor of the frame. This create a windows, set the windows on the center, define the close bouton's action, define a panel and set the windows to visible.
		 * @param finder the finder of the plugins.
		 */
		public MyFrame(PluginFinder finder){
			super("Extendable Editor");
			this.finder = finder;
		    setSize(MyFrame.l, MyFrame.h);
		    setLocationRelativeTo(null);
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    this.panel = new MyPanel(this);
			setContentPane(this.panel);
		    setVisible(true);
		}
		
		/**
		 * return the TextArea of the editor. This will be use for set ou get his content.
		 * @return the TextArea of the editor.
		 */
		private JTextArea getTextArea(){
			return this.panel.getTextArea();
		}
	
		/**
		 * Return the content of the editor.
		 * @return the content of the editor.
		 */
		public String getTextContent(){
			return this.getTextArea().getText();
		}
		
		/**
		 * Set the content of the editor.
		 * @param text the content we want on our editor.
		 */
		public void setTextContent(String text){
			this.getTextArea().setText(text);
		}
			
		/**
		 * return the Tools's menu
		 * @return the tools's menu.
		 */
		public JMenu getToolsMenu(){
			return this.panel.head.getToolsMenu();
		}
	}
	
	/**
	 * This is the panel of our frame. 
	 * This panel's content is :
	 * 	- A JMenuBar
	 * 	- A Body with a JTextArea.
	 */
	public class MyPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		/**
		 * The body of the window.
		 */
		protected final JComponent body;
		
		/**
		 * The Menu of the window.
		 */
		protected final HeadPanel head;
		
		/**
		 * The editor.
		 */
		protected final JTextArea editor;
		
		/**
		 * The windows which use the panel.
		 */
		protected final MyFrame frame;
		
		/**
		 * Constructor of the panel. This will set the border, create a menu at the north and a content on the center.
		 * @param frame the window which will use the panel.
		 */
		public MyPanel(MyFrame frame){
			this.frame = frame;
			this.setLayout(new BorderLayout());
			this.head = new HeadPanel(this.frame);
	        this.add(this.head, BorderLayout.NORTH);
	        
	        this.editor = (new JTextArea());
	        this.body = new JScrollPane(this.editor);
	        this.add(this.body, BorderLayout.CENTER);
		}
		
		/**
		 * return the TextArea of the panel.
		 * @return the TextArea of the panel.
		 */
		public JTextArea getTextArea(){
			return this.editor;
		}
	}
	
	/**
	 * The HeadPanel is the Menu of the window.
	 */
	public class HeadPanel extends JMenuBar {

		private static final long serialVersionUID = 1L;
		
		/**
		 * The different menu of the window.
		 */
		protected JMenu file, tools, help;
		
		/**
		 * The windows.
		 */
		protected final MyFrame frame;
		
		/**
		 * Constructor of the HeadPanel. This will create 3 menu : File, Tools and Help.
		 * @param frame the windows which will use this menu.
		 */
		public HeadPanel(MyFrame frame){
			this.frame = frame;
			this.file = new JMenu("File");
			this.tools = new JMenu("Tools");
			this.help = new JMenu("Help");
			
			this.fileContent();
			this.helpContent();
			
			this.add(file);
			this.add(tools);
			this.add(help);
		}
		
		/**
		 * Create the File Menu content.
		 */
		public void fileContent(){
			this.file.add(new JMenuItem(new NewItemAction(this.frame,"New")));
			this.file.add(new JMenuItem(new OpenItemAction(this.frame, "Open")));
			this.file.addSeparator();
			this.file.add(new JMenuItem(new ExitItemAction(this.frame, "Exit")));
		}
		
		/**
		 * Create the Help Menu content.
		 */
		public void helpContent(){
			this.help.add(new JMenuItem(new HelpItemAction(this.frame, "About ExtendableEditor")));
		}
		
		/**
		 * Return the Tools's Menu.
		 * @return the tools's menu.
		 */
		public JMenu getToolsMenu(){
			return this.tools;
		}
		
	}
	
	/**
	 * PluginItem create the Items of the Plugin's menu. This list of Items will actualize every second.
	 */
	public class PluginItem implements PluginEventListener{

		/**
		 * The window.
		 */
		protected final MyFrame frame;
		
		/**
		 * The Menu which will contains the Plugin's item.
		 */
		protected JMenu tools;
		
		/**
		 * A Map which have for key the different item of the tools's menu and the name of the plugin in value.
		 */
		protected Map<JMenuItem, String> alreadyFoundPlug;
		
		/**
		 * Constructor of the items. This will initialize the Map.
		 * @param frame the window which will use the items.
		 */
		public PluginItem(MyFrame frame){
			this.frame = frame;
			this.tools = this.frame.getToolsMenu();
			this.alreadyFoundPlug = new HashMap<JMenuItem, String>();
		}
		
		/**
		 * This will add a plugin to the Tools's menu.
		 * @param file the plugin we want to add.
		 */
		public void update(File file) {
			JMenuItem item = new JMenuItem(new PluginItemAction (this.frame, file));
			this.tools.add(item);
			this.alreadyFoundPlug.put(item, file.getName());
		}

		/**
		 * This will delete a plugin from the Tools's menu.
		 * @param file the plugin we want to delete.
		 */
		public void deletePlugin(File file) {
			Map<JMenuItem, String> tmpAlreadyFoundPlug = new HashMap<JMenuItem, String>();
			tmpAlreadyFoundPlug.putAll(this.alreadyFoundPlug);
			for (JMenuItem item: tmpAlreadyFoundPlug.keySet()){
				if(this.alreadyFoundPlug.get(item).equals(file.getName())){
					this.tools.remove(item);
					this.alreadyFoundPlug.remove(item);
				}
			}
		}
		
			/**
			 * This will create one Plugin's item.
			 */
			public class PluginItemAction extends AbstractAction{

				private static final long serialVersionUID = 1L;
	
				/**
				 * The window.
				 */ 
				protected final MyFrame frame;
				
				/**
				 * The plugin we want to add.
				 */
				protected Plugin plugin;
				
				/**
				 * Constructor of a Plugin's Item. 
				 * @param frame the window which will use this PluginItemAction.
				 * @param file the plugin.
				 */
				public PluginItemAction(MyFrame frame, File file){
					super(file.getName().replaceAll("\\.class$", ""));
					this.getPlugin(file);
					this.frame = frame;
				}
				
				/**
				 * this method will take a file and instance that file for set the plugin.
				 * @param file the plugin.
				 */
				public void getPlugin(File file){
					try{
						String path = "plugins." + file.getName().replaceAll("\\.class$", "");
						Class<?> theClass = Class.forName(path);
						this.plugin = (Plugin) theClass.newInstance();
					} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException e){
						e.printStackTrace();
					}
				}
			
				/**
				 * This will transformed the text with the transformation of the plugin.
				 */
				public void actionPerformed(ActionEvent e){
					this.frame.setTextContent(this.plugin.transform(this.frame.getTextContent()));
				}
			}
		
	}
	
	/**
	 * This will define the Exit Item's action.
	 */
	public class ExitItemAction extends AbstractAction{
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * The window.
		 */
		protected final MyFrame frame;
		
		/**
		 * Constructor of the ExitItemAction.
		 * @param frame the window
		 * @param text the name of the button.
		 */
		public ExitItemAction(MyFrame frame, String text){
			super(text);
			this.frame = frame;
		}
		
		/**
		 * Define the action of the Item : He'll leave the application.
		 */
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
	
	/**
	 * This will define the action of the Open Item.
	 */
	public class OpenItemAction extends AbstractAction{
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * The window.
		 */
		protected final MyFrame frame;
		
		/**
		 * Constructor of the OpenItemAction.
		 * @param frame the window
		 * @param text the name of the button.
		 */
		public OpenItemAction(MyFrame frame, String text){
			super(text);
			this.frame = frame;
		}
		
		/**
		 * Define the action of the button : this one will chose a file and load into the editor all the content of the file.
		 */
		public void actionPerformed(ActionEvent e){
			JFileChooser inDialog = new JFileChooser() ;
			if (inDialog.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				String txt="";
				try {
					@SuppressWarnings("resource")
					BufferedReader in = new BufferedReader(new FileReader(inDialog.getSelectedFile())) ;
					String ligne ;
					while((ligne = in.readLine()) != null) {
						txt = txt + ligne + "\n";
					}             
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Erreur de lecture pour " +
							inDialog.getSelectedFile().getName()) ; 
				}
				this.frame.getTextArea().setText(txt) ; 
			}	      
		}
	}

	/**
	 * This will define the NewItem's action.
	 */
	public class NewItemAction extends AbstractAction{
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * The window
		 */
		protected final MyFrame frame;
		
		/**
		 * Constructor of the NewItemAction.
		 * @param frame the window
		 * @param text the name of the button.
		 */
		public NewItemAction(MyFrame frame, String text){
			super(text);
			this.frame = frame;
		}
		
		/**
		 * Define the action of the button : this on will reset the text of the editor.
		 */
		public void actionPerformed(ActionEvent e){
			this.frame.setTextContent("");
		}
	}

	/**
	 * This will define the HelpItem's action.
	 */
	public class HelpItemAction extends AbstractAction{

		private static final long serialVersionUID = 1L;
		
		/**
		 * The window.
		 */
		protected final MyFrame frame;

		/**
		 * Constructor of the HelpItemAction.
		 * @param frame the window
		 * @param text the name of the button.
		 */
		public HelpItemAction(MyFrame frame, String text){
			super(text);
			this.frame = frame;
		}
		
		/**
		 * Define the action of the button : this one will load a popup.
		 */
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(this.frame, "This interface is the implementation of the plugin's project !\n Try to add or delete some plugins into the classes/plugins folder and you'll see your menu change ! ;)");
		}
	}
	
	/**
	 * Constructor of the editor.
	 * This will create a new frame and pluginFinder and start the timer. 
	 */
	public ExtendableEditor(){
		PluginFinder plug = new PluginFinder(new File("classes/plugins"));
		MyFrame frame = new MyFrame(plug);
		plug.addListener(new PluginItem(frame));
		plug.startTimer();
	}
	
}