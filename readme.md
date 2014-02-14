JavaApplicationInterface
========================

General:
-------
This Project shares and Interface for entering the Software "PTC MKS Integrity". If offers 
item representation of all important items (Project, Release, Development Order, Task and Release). 
And is open for extend these items. 
The second important thing is, that with this interface you are 
able to execute "Integrity" Queries on a special way: Not only one query can be executed, it is possible
to execute more than one query in one execution. The interface internal calculates your result and returns 
it within an result object.

Documentation:
--------------
In the folder "doc" is a full java documentation of all classes in the project

Using the interface
-------------------
For using the interface please include the 'Mksinterface_libary' class to your project and work with this 
class. It provides you all methods that you can use of this interface. For handling the result please include 
Mksinterface_result'. And for handling the items include the items you use (e.g. Mksinterface_mksitem_project).
Have a lot of fun!

Starting the TestGui
--------------------
For starting the TestGui please execute the MainFrame.java with no parameters but with the necessary libraries which
are in the folder 'lib'. For successfully executing the TestGUI you need the java interface which is given by PTC.
If you have 'Integrity' it is in the 'lib' folder of the software

Future work
------------
- Re factoring the whole project
- Make Query definitions more abstract or some classes which provide an abstract use of
  the queries.
- Run Time more efficient: Type declaration at the beginning, shorten the access to Integrity 