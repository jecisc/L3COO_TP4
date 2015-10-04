			----------------------------------------------------
			--          TP3 : Plugins		                  --
			--                                                --
			--			 09/12/2014 - 19h00	                  --
			--                                                --
			-- authors : Cyril Ferlicot D & Thibault Verron	  --
			----------------------------------------------------

SUMMARY
	1) Introduction
	2) Run the project 
		into Eclipse
	3) Explanations of the project
		a) packages
		b) design patterns
	4) Bugs
	5) Comments

----------------------------------------------------

1) INTRODUCTION

This project is the 4th evaluated Project, named "Plugin". 
This project aim to develop an plugin's management and the development of a graphic interface. At the end we must have a text editor which will update himself when we had or delete some packages.

----------------------------------------------------

2) RUN THE PROJECT

	INTO ECLIPSE

	For run the project you need to open it into Eclipse. 
	We need to do a clean for rebuild all the .class.
	You can generate the doc with Eclipse.
	You can run the different test by running as JUnit-Case all the files inside the "test" package (inside the test folder).
	You have two type of main:
		- You can run as java application PluginAddedLoggerMain (inside plugin's package) : this will execute a text version into the console of the project.
		- You can run as java application Main (inside editor's package) : this will launch the graphic interface.
	
----------------------------------------------------

3) EPLANATIONS OF THE PROJECT
	a) PACKAGES 

Into our project you have some package. I'll explain all the different package here.

	--> Plugin
			This package contains all the implementation of the plugin's system. 
			
			The Plugin interface describe the correct implementation of a Plugin. (The different plugins will be on the "plugins's package". 
			
			When we'll find some plugins we'll send a message to some files. Those files will need to have the same method, for that we create a PlugineventListener's interface. The files which implements these interface will be able to be update by adding a plugin or deleting a plugin.
			
			For allow the plugin's system we need to create a filter for select the different files. Here we'll use PluginFilter. The method accept will take a file and return true only if the file finish by ".class", implement plugin, is on the right package and contains a constructor without parameter.

			When we've our filter we can use the PluginFinder. This class will have a timer and at each tic of the timer we'll check a folder for changes. When we find some change we update the listeners.
			
			For test all this system we have the PluginAddedLogger class which print on the console the action we execute. 
			
			We can check all of that with the PluginAddedLoggerMain.
			
	--> Plugins		
			This package contains different type of plugins like CeaserCode, ToUpperCase or ToLowerCase. 
			
			It contains also some Mock for the test (we had to put the Mock here because of the filter). 
			
	--> Editor
			This package contains the implementation of the ExpendableEditor and allow to create an editor with a graphic interface.
			The graphic interface is compose by a Frame (MyFrame). Inside this frame we've a Panel (MyPanel). 
			On this panel we've 1 TextArea on the centre and 1 JMenuBar.
			The JMenuBar is composed of 3 Menu (File, Tools and Help). 
			File and Help have some buttons and the action associate.
			The Tools Menu is generate by a PluginItem which is a listener of the PluginFinder. When we had or delete a Plugin we update the menu.			

- - - - - - - - - - - - - - - - - - - - - - - - - - 

	b) DESIGN PATTERNS
	
We used 1 design pattern for this project: Observer

We use this pattern for check the plugins's folder and update some observer when we find a new plugin or we see that a plugin is delete. 

The PluginEventListener interface is the interface that define what is an observer. 

We have some class which implements this interface, they're the concreteObservers. We have : PluginAddedLogger and PluginItem on the graphic interface. 

The pluginFinder's class is the Observable. The pluginFinder have a list of observers (on this project they are the listeners). When it found something it update the observers. 

The PluginFinder check a folder which is the concreteObservable.
----------------------------------------------------

4) BUGS

There is no bug known.

----------------------------------------------------

5) Comments

Nothing.