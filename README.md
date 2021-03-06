# 370project
**IMPROTANT You need at least java version 13.0.2


For Mac
 - Easiest way is to download the whole repository and make that your current working directory in a terminal
 - You should be able to do a ls to verify that you are in the correct directory
 - Next you want to execute the following command
 - java --module-path javaFX/ --add-modules javafx.controls,javafx.fxml -jar 370project.jar
 - This will launch the product and you can start using it

Alternative For Mac
 - The required files for running on the mac are the javaFX directory and the 370project.jar
 - Save both of those files in a directory on your computer
 - Next open terminal and make the directory where you saved the files your current working directory
 - Once there use the following command to launch the product
 - java --module-path javaFX/ --add-modules javafx.controls,javafx.fxml -jar 370project.jar

For Linux
 - Easiest way is to download the whole repository and make that your current working directory in a terminal
 - You should be able to do a ls to verify that you are in the correct directory
 - Next you want to execute the following command
 - java --module-path javaFXLinux/lib/ --add-modules javafx.controls,javafx.fxml -jar 370project.jar
 - This will launch the product and you can start using it

Alternative For Linux
 - The required files for linux are the javaFXLinux directory and the 370project.jar
 - Save both of these files in a directory on your computer
 - Next open a terminal and make the directory where you save the files your current working directory
 - Once there use the following command to launch the product
 - java --module-path javaFX/ --add-modules javafx.controls,javafx.fxml -jar 370project.jar

For Windows
 - Easiest way is to download the whole repository and make that your current working directory in a cmd prompt
 - You should be able to do a dir to verify you are in the correct directory
 - Next you want to execute the following command
 - java --module-path javaFXWindows\lib --add-modules javafx.controls,javafx.fxml -jar 370project.jar

Alternative For Windows
 - The required files for linux are the javaFXWindows directory and the 370project.jar
 - Save both of these files in a directory on your computer
 - Next open a cmd prompt and make the directory where you save the files your current working directory
 - Once there use the following command to launch the product
 - java --module-path javaFXWindows\lib --add-modules javafx.controls,javafx.fxml -jar 370project.jar

 Notes:
 - The database will be created on initial startup. All data will be saved and to exit the program you can just click the x.
 - At any point you can delete the database from the directory. Although all data will be lost. 
 - Starting the program again as normal will create a new blank database
 - Deleting the database will result in all data being lost.

Testing:
 - A great portion of the product was tested by inspection. For what wasn't tested by inspection the only way to use the tests 
 is to import the project into intelliJ or another development envirment. 
 We ran the tests before submitting and they all passed. The tests are located in the test/ 
 directory in the repository for veiwing.