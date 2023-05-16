package com.rockthejvm
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.io.Source
import scala.collection.mutable


//Scala REPS implementation

//Daniil Komov and Andrei Toma



object Playground extends App {



  val myObject = new ControlBoard()

  //declaring a control board object

  val solarPanels = new SolarPanels("model_3000")

   //declaring a solar panel object

  val windTurbines = new WindTurbines("model_2000")

   //declaring a wind turbine object

  val hydroPlant = new HydroPowerPlant("model_1000")

   //declaring a hydro powerplant object

  val alarm = new ErrorAlarm()

  //object for alarm system

  //creating different class objects to work with them

  solarPanels.generateEnergy(500)

  solarPanels.generateEnergy(500)

  solarPanels.generateEnergy(500)

  windTurbines.generateEnergy(500)

  solarPanels.generateEnergy(500)

  hydroPlant.generateEnergy(500)

  windTurbines.generateEnergy(500)

  //Since we don't have real REPS here, and no sensors to see if energy is generated, we are generating energy by invoking a method in the classes
  //the value inside brackets is the amount of energy to be generated

  myObject.menu()

  //calling menu of the program





  class SolarPanels(val id: String) {

    //defining solar panel class
  
    private var energyGenerated: Double = 0

    //Energy generated status variable

    private var turnAngle: Double = 360

    //Turn angle status variable


    private var isConnected: Boolean = true

    //Connection status variable



  
    def generateEnergy(amount: Double): Unit = {

      //declaring a method for the panel to generate energy

      if (isConnected== false) {alarm.generateConnectionError()} //generate connection error using a separate class, alarm system
      else {

        energyGenerated += amount

        //changing the generated energy status value

      

        val energy_records = Source.fromFile("energy_history.csv").getLines().toList

        //getting energy records from file

        var total_energy = 0.0 

        //total energy generated variable (default)
        
        energy_records.lastOption.foreach { line =>
          line.split(",") match {
            case Array(_, _, _, capacity) => 
              total_energy = capacity.split("/")(0).toDouble 
            case _ => {}
          }
        }

        //extracting the lines from the file and getting the last line to get the total energy generated

        val timestamp = LocalDateTime.now()

        //getting current timestamp
        val capacity = total_energy + energyGenerated

        //getting the current capacity value

        if (capacity>95000) {alarm.generateStorageError()} 

        //generate the storage error if capacity is more than 95000
        val formattedTime = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        //creating a formatted timestamp
        val report = s"$id,$formattedTime,$energyGenerated,$capacity/100000\n"

        //report variable is for writing into csv file

        val fileWriter = new FileWriter("energy_history.csv", true)
        fileWriter.write(report)
        fileWriter.close()

        //writing into the file


      }

      
      
    }


    def moveLeft(): Unit ={

      //function for turning the source left for better production

      if (turnAngle== 0) {

        turnAngle = 315

      }

      else {
        turnAngle -= 45
      }

      println("\n")

      println(s"Turn angle is $turnAngle degrees")

      println("\n")

    }

    def moveRight(): Unit ={

      //function for turning the source right for better production

      if (turnAngle== 360) {

        turnAngle = 45

      }

      else  {
        turnAngle += 45
      }

      println("\n")

      println(s"Turn angle is $turnAngle degrees")

      println("\n")

    }

    def disconnectEmergency(): Unit ={

      //method for emergency disconnect, chenging the isConnected value to false

      isConnected= false



    }


  

    def disconnect(): Unit ={

      //disconnect/connect method

      if (isConnected== true) {

      isConnected= false
    }

      else {

      isConnected= true
    }

    //disconnect if connected and otherwise connect, on/off switch

    println("\n")

    println(s"Connected: $isConnected ")

    println("\n")

    }

    override def toString: String = s"\n ---------------------------- \n SolarPanels $id \n Energy generated: $energyGenerated, \n Turn angle: $turnAngle, \n Connected: $isConnected"

    //For telling the status
  }





class WindTurbines(val id: String) {

  //defining wind turbine class
  
  private var energyGenerated: Double = 0

  //Energy generated status variable

  private var turnAngle: Double = 360

  //turn angle status variable


  private var isConnected: Boolean = true

  //Connection status variable

  
  def generateEnergy(amount: Double): Unit = {

    //declaring a method for the panel to generate energy
    
    if (isConnected== false) {alarm.generateConnectionError()} //generate connection error using a separate class, alarm system
    else {

      energyGenerated += amount

      //changing the generated energy status value

      val energy_records = Source.fromFile("energy_history.csv").getLines().toList

      //getting energy records from file
      var total_energy = 0.0 

      //Setting def value for total energy generated
        
        energy_records.lastOption.foreach { line =>
          line.split(",") match {
            case Array(_, _, _, capacity) => 
              total_energy = capacity.split("/")(0).toDouble 
            case _ => {}
          }
        }

        //Extracting the last info about total generated value and adding it to our def value



        val timestamp = LocalDateTime.now()

        //Getting current timestamp
        val capacity = total_energy + energyGenerated

        //Making capacity, total energy generated

        if (capacity>95000) {alarm.generateStorageError()} //throw error if capacity more then 95000
        val formattedTime = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        //formatting the time
        val report = s"$id,$formattedTime,$energyGenerated,$capacity/10000\n"

        //report variable for file writing

        val fileWriter = new FileWriter("energy_history.csv", true)
        fileWriter.write(report)
        fileWriter.close()

        //writing to file


    }
    
    }


  def moveLeft(): Unit ={

    //function for turning the source left for better production

    if (turnAngle== 0) {

      turnAngle = 315

    }

    else  {
      turnAngle -= 45
    }

    println("\n")

    println(s"Turn angle is $turnAngle degrees")

    println("\n")

  }

  def moveRight(): Unit ={

     //function for turning the source right for better production

    if (turnAngle== 360) {

      turnAngle = 45

    }

    else {

      turnAngle += 45


    }


    println("\n")

    println(s"Turn angle is $turnAngle degrees")

    println("\n")

  }

  def disconnectEmergency(): Unit ={

    //method for emergency disconnect, chenging the isConnected value to false

      isConnected= false



    }

  def disconnect(): Unit ={

    //normal connect/disconnect method



    if (isConnected== true) {

    isConnected= false
  }

  else  {

    isConnected= true
  }

  //disconnect if connected and otherwise connect, on/off switch

  println("\n")

  println(s"Connected: $isConnected ")

  println("\n")
}

  override def toString: String = s"\n ---------------------------- \n WindTurbines $id \n Energy generated: $energyGenerated, \n Turn angle: $turnAngle, \n Connected: $isConnected"
  
  //for telling the status
}






class HydroPowerPlant(val id: String) {

  //defining hydro powerplant class
  
  private var energyGenerated: Double = 0

  private var isConnected: Boolean = true

  //def values for energy and connection

  

  

  
  def generateEnergy(amount: Double): Unit = {

    //method for generating energy

    if (isConnected== false) {alarm.generateConnectionError()}
    else {

      energyGenerated += amount

      //changing the energy generated value

      val energy_records = Source.fromFile("energy_history.csv").getLines().toList

      //getting data from file
      var total_energy = 0.0 

      //def value for capacity
      
      energy_records.lastOption.foreach { line =>
        line.split(",") match {
          case Array(_, _, _, capacity) => 
            total_energy = capacity.split("/")(0).toDouble // Update total_energy with the extracted value
          case _ => {}
        }
      }

      //finding total energy using the last recording in a file

      val timestamp = LocalDateTime.now()

      //current timestamp
      val capacity = total_energy + energyGenerated

      //total capacity

      if (capacity>95000) {alarm.generateStorageError()} //give error if capacity is more than 95000
      val formattedTime = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
      //formatting current time
      val report = s"$id,$formattedTime,$energyGenerated,$capacity/10000\n"

      //report var for the file writing

      val fileWriter = new FileWriter("energy_history.csv", true)
      fileWriter.write(report)
      fileWriter.close()

      //writing to the file
    }

  
    

    }

  def disconnectEmergency(): Unit ={

    //emergency disconnect method

      isConnected= false




    }

  def disconnect(): Unit ={

    // connect/disconnect method

    if (isConnected== true) {

    isConnected= false
  }

  else  {

    isConnected= true
  }

  //disconnect if connected and otherwise connect, on/off switch

  println("\n")

  println(s"Connected: $isConnected ")

  println("\n")
}

  override def toString: String = s"\n ---------------------------- \n Hydro powerplant $id \n Energy generated: $energyGenerated, \n Connected: $isConnected"
  
  //for telling the status
}








class ControlBoard() {

  //defining the control board class
  
  def menu(): Unit= {

    //menu method

    println("\n")
    
    println("---------------------------------")

    println("WELCOME TO R.E.P.S.")

    println("---------------------------------")


    println("PLEASE CHOOSE OPTION FROM THE LIST")

    println("---------------------------------")

    println("1. CONTROL THE MACHINES |  2.  SEE MACHINES STATUS")

    println("3. DATA ANALYSIS        |  4.  SEE STORAGE STATUS")

    println("5. VIEW ENERGY PRODUCTION HISTORY")

    println("---------------------------------")

    println("OR ENTER E TO EXIT")

    println("---------------------------------")

    //menu options to the screen


    val input= scala.io.StdIn.readLine("YOUR OPTION: ")

    //gathering input


    input match {
      case "1" => machinesControl() 
      case "2" => machinesStatus()
      case "3" => dataAnalysis() 
      case "4" => storageStatus() 
      case "5" => energyHistoryView()
      case "E" => exit() 
      case "e" => exit() 
      case _   => menu()
    }

    //input matching, to different methods
 

  }


  def exit() ={

    //exit method, exits the program

    print("BYE")

  }


  def machinesControl(): Unit= {

    //machines control method, makes it possible to control the machines

    println("\n")

    println("---------------------------------")

    

    println("CHOOSE THE ENERGY SOURCE")

    println("---------------------------------")


    println("1. SOLAR PANELS      |  2.  WIND TURBINES")

    println("3. HYDRO POWERPLANT  |  4.  EXIT TO MENU")

    println("---------------------------------")

    //menu options to the screen

    

    val powerPlant = scala.io.StdIn.readLine("YOUR OPTION: ")

    //gathering the input

    //below is input matching

    powerPlant match {
      case "1" => {

        //solar panel controls

      println("\n")

      println("---------------------------------")

      println("SOLAR PANELS")

      println("---------------------------------")

      println("WHAT WOULD YOU LIKE TO DO?")

      println("---------------------------------")

      println("1. TURN LEFT 45 DEGREES        |  2.  TURN RIGHT 45 DEGREES")
      
      println("3. DISCONNECT/CONNECT PANELS   |  4.  EXIT TO MENU")

      println("---------------------------------")

      //menu options to the screen

       val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

       //another input 

       

       choice match {

        case "1" => {

          solarPanels.moveLeft()

          menu()

        }

        //move left

        case "2" => {

          solarPanels.moveRight()

          menu()
          
        }

        //move right

        case "3" => {

          solarPanels.disconnect()

          menu()
          
        }

        //disconnect/connect

        case "4" => {

          menu()
          
        }

        //back to menu

        case _   => menu()

       }

       //in case of other character also come back to menu

       }
      case "2" => {

        //wind turbine control

        println("\n")

        println("---------------------------------")

        println("WIND TURBINES")

        println("---------------------------------")

        println("WHAT WOULD YOU LIKE TO DO?")

        println("---------------------------------")

        println("1. TURN LEFT 45 DEGREES        |  2.  TURN RIGHT 45 DEGREES")
        
        println("3. DISCONNECT/CONNECT TURBINES |  4.  EXIT TO MENU")

        println("---------------------------------")

        //menu to screen

        val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

        //input

        

        choice match {

          case "1" => {

            windTurbines.moveLeft()

            menu()

          }

          // move left

          case "2" => {

            windTurbines.moveRight()

            menu()
            
          }

          //move right

          case "3" => {

            windTurbines.disconnect()

            menu()
            
          }

          //disconnect/connect

          case "4" => {

            menu()
            
          }

          //back to menu

          case _   => menu()

        }

        //back to menu in case of other character
       }
      case "3" => {

        //Hydro powerplant

        println("\n")

        println("---------------------------------")

        println("HYDRO POWERPLANT")

        println("---------------------------------")

        println("WHAT WOULD YOU LIKE TO DO?")

        println("---------------------------------")

        
        
        println("1. DISCONNECT/CONNECT POWERPLANT  |  2.  EXIT TO MENU")

        println("---------------------------------")

        //info to screen

        val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

        //input

        

        choice match {

          

          case "1" => {

            hydroPlant.disconnect()

            menu()
            
          }

          //disconnect/connect

          case "2" => {

            menu()
            
          }

          //back to menu

          case _   => menu()

        }

        //back to menu if other character

      }
      case "4" => menu() 

      //back to menu
      case _   => machinesControl()

      //machines control again if wrong input
    }





    

  }

  def machinesStatus(): Unit = {

    // method for sources statuses

    println("\n")


    println("CHOOSE THE ENERGY SOURCE")

    println("---------------------------------")


    println("1. SOLAR PANELS      |  2.  WIND TURBINES")

    println("3. HYDRO POWERPLANT  |  4.  EXIT TO MENU")

    println("---------------------------------")
    
    //menu to screen

    

    val powerPlant = scala.io.StdIn.readLine("YOUR OPTION: ")

    //input

    var status= ""

    //def value for status

    


    powerPlant match {

      case "1" => {

        status = solarPanels.toString()

        println(status)

        menu()

        
      }

      //status for solar panel

      case "2" => {

        status = windTurbines.toString()

        println(status)

        menu()

      }

      //status for wind turbine

      case "3" => {

        status = hydroPlant.toString()

        println(status)

        menu()

      }

      //status for hydro powerplant

      case "4" => menu()

      //back to menu

      case _ => machinesStatus()

      //back to machines status if wrong character



    }

    println(s"$status")

    //printing the status

    

  }

  def dataAnalysis(): Unit = {

    //method for data analysis

    def filterDataByDate(date: LocalDate): List[String] = {

      //method for filtering the data by date
      val energyRecords = scala.io.Source.fromFile("energy_history.csv").getLines().toList

      //reading data from file
      energyRecords.filter { line =>
        line.split(",") match {
          case Array(_, timestamp, _, _) =>
            val recordDate = LocalDate.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            recordDate.isEqual(date)
          case _ =>
            false
        }
      }

      // filtering the data for the specific date
    }



    val input = scala.io.StdIn.readLine("WRITE DATE IN FORMAT yyyy-MM-dd: ")

    //gethering input for date

    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    //formatting date

    val chosenDate = LocalDate.parse(input, dateFormat)

    //parsing date 


   
    val filteredLines = filterDataByDate(chosenDate)

    //filtering the data

    val energyData = filteredLines.flatMap { line =>
      line.split(",") match {
        case Array(_, timestamp, energyGenerated, _) =>
          Some(energyGenerated.toDouble)
        case _ =>
          None
      }
    }

    //getting the data into the timestamp-energy format

    val dataAnalyser= DataAnalyser(energyData)

    //Instantiating the data analyser class object

    val meanValue = dataAnalyser.mean
    val medianValue = dataAnalyser.median
    val modeValue = dataAnalyser.mode
    val rangeValue = dataAnalyser.range
    val midRangeValue = dataAnalyser.midRange

    //declaring necessary analysis variables

    val analysisReport = s"ANALYSIS REPORT\n\n" +
      "\n" +
      s"Mean: $meanValue\n" +
      s"Median: $medianValue\n" +
      s"Mode: $modeValue\n" +
      s"Range: $rangeValue\n" +
      s"Mid-range: $midRangeValue\n" +
      "\n" +
      s"HISTORY BY TIME FORMAT: \n${filteredLines.mkString("\n")}\n"

      //creating a string with all the information

    val outputFileName = s"history_analysis${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))}.csv"

    //creating a new file
    val fileWriter = new FileWriter(outputFileName)
    
    fileWriter.write(analysisReport)
    fileWriter.close()

    //writing to the file

    println(s"Data analysis report written to '$outputFileName'.")

    //delivering status


    menu()

     

  }

  def storageStatus() = {

    //method for delivering storage status

    val energy_records = Source.fromFile("energy_history.csv").getLines().toList
    //getting energy history from file
      var total_energy = 0.0 

      //def value for total energy
      
      energy_records.lastOption.foreach { line =>
        line.split(",") match {
          case Array(_, _, _, capacity) => 
            total_energy = capacity.split("/")(0).toDouble 
          case _ => {}
        }
      }

      //find the last recording and change the total_energy to it

      println("\n")

      println("---------------------------")

      println(s"Energy storage has the following amount of energy: $total_energy/100000")



      println("---------------------------")

      //printing status

    menu()

  }

  def energyHistoryView(): Unit = {

    //method for making a view for specific type of source

    println("\n")


    println("CHOOSE THE ENERGY SOURCE")

    println("---------------------------------")


    println("1. SOLAR PANELS      |  2.  WIND TURBINES")

    println("3. HYDRO POWERPLANT  |  4.  EXIT TO MENU")

    println("---------------------------------")

    //printing to the screen

    

    val powerPlant = scala.io.StdIn.readLine("YOUR OPTION: ")

    //input

    

    powerPlant match {

      case "1" => printRecords("model_3000")
      case "2" => printRecords("model_2000")
      case "3" => printRecords("model_1000")
      case "4" => menu()
      case _ => energyHistoryView()


    }

    //options for printing records for types of sources

    menu()

    def printRecords(idM: String): Unit = {

      //method for printing records

      val energyRecords = scala.io.Source.fromFile("energy_history.csv").getLines().toList

      //getting records from file
      val filteredLines = energyRecords.filter { line =>
        line.split(",") match {
          case Array(id, _, energyGenerated, _) => id.trim == idM && energyGenerated.nonEmpty
          case _ => false
        }
      }

      //filtering the records based on the name of the source (specifically determined by the model id)

      if (filteredLines.nonEmpty) {

        //if not empty do this

        println("\n")
        println(s"HISTORY FOR SPECIFIC ENERGY SOURCE")

        println("------------------------------------------------")
        filteredLines.foreach { line =>
          line.split(",") match {
            case Array(_, timestamp, energyGenerated, _) => println(s"$timestamp - $energyGenerated")
            case _ =>
          }
        }

        //printing the records for this energy source
      } else {
        println(s"NOTHING FOUND")
      }

      //printing nothing found if no info
    }



    }


}



class ErrorAlarm {

  //error alarm system class


  def generateStorageError(): Unit = {

    //defining method which gives error and disconnects the sources if the storage is almost full

    println("\n")

    println("-----------------------------------------------------------")
    
    println(s"Error: STORAGE IS ALMOST FULL. DISCONNECTING THE ENERGY SOURCES")

    println("-----------------------------------------------------------")

    //printing

    solarPanels.disconnectEmergency()

    windTurbines.disconnectEmergency()

    hydroPlant.disconnectEmergency()

    //using class objects to disconnect

    

    
  }

  def generateConnectionError(): Unit = {

    //method that generates connection error

    println("\n")

    println("-----------------------------------------------------------")
    
    println(s"Error: ENERGY SOURCE IS DISCONNECTED")

    println("-----------------------------------------------------------")

    //printing





  }
}




  
class DataAnalyser(data: Seq[Double]) {

  //class for data analysis
  
  def mean: Double = data.sum / data.length.toDouble

  //method for the mean value

  def median: Double = {
    val sortedData = data.sorted
    val midIndex = sortedData.length / 2
    if (sortedData.length % 2 == 0)
      (sortedData(midIndex - 1) + sortedData(midIndex)) / 2
    else
      sortedData(midIndex)
  }

  //method for finding median

  def mode: Seq[Double] = {
    val counts = mutable.Map[Double, Int]().withDefaultValue(0)
    data.foreach(value => counts(value) += 1)

    val maxCount = counts.values.max
    counts.filter(_._2 == maxCount).keys.toSeq
  }

  //method for finding mode

  def range: Double = data.max - data.min

  //method for finding range

  def midRange: Double = (data.min + data.max) / 2

  //method for finding midRange
}






}


