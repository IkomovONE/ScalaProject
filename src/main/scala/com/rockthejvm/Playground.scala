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

        //

        if (capacity>95000) {alarm.generateStorageError()} 
        val formattedTime = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val report = s"$id,$formattedTime,$energyGenerated,$capacity/100000\n"

        val fileWriter = new FileWriter("energy_history.csv", true)
        fileWriter.write(report)
        fileWriter.close()


      }

      
      
    }


    def moveLeft(): Unit ={

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

      isConnected= false



    }


  

    def disconnect(): Unit ={

      if (isConnected== true) {

      isConnected= false
    }

      else {

      isConnected= true
    }

    println("\n")

    println(s"Connected: $isConnected ")

    println("\n")

    }

    override def toString: String = s"\n ---------------------------- \n SolarPanels $id \n Energy generated: $energyGenerated, \n Turn angle: $turnAngle, \n Connected: $isConnected"
  
  }





class WindTurbines(val id: String) {
  
  private var energyGenerated: Double = 0

  private var turnAngle: Double = 360


  private var isConnected: Boolean = true

  
  def generateEnergy(amount: Double): Unit = {
    
    if (isConnected== false) {alarm.generateConnectionError()}
    else {

      energyGenerated += amount

      val energy_records = Source.fromFile("energy_history.csv").getLines().toList
      var total_energy = 0.0 // Default value
        
        energy_records.lastOption.foreach { line =>
          line.split(",") match {
            case Array(_, _, _, capacity) => 
              total_energy = capacity.split("/")(0).toDouble // Update total_energy with the extracted value
            case _ => {}
          }
        }

        val timestamp = LocalDateTime.now()
        val capacity = total_energy + energyGenerated

        if (capacity>95000) {alarm.generateStorageError()}
        val formattedTime = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val report = s"$id,$formattedTime,$energyGenerated,$capacity/10000\n"

        val fileWriter = new FileWriter("energy_history.csv", true)
        fileWriter.write(report)
        fileWriter.close()


    }
    
    }


  def moveLeft(): Unit ={

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

      isConnected= false



    }

  def disconnect(): Unit ={

    if (isConnected== true) {

    isConnected= false
  }

  else  {

    isConnected= true
  }

  println("\n")

  println(s"Connected: $isConnected ")

  println("\n")
}

  override def toString: String = s"\n ---------------------------- \n WindTurbines $id \n Energy generated: $energyGenerated, \n Turn angle: $turnAngle, \n Connected: $isConnected"
  
}






class HydroPowerPlant(val id: String) {
  
  private var energyGenerated: Double = 0

  private var isConnected: Boolean = true

  

  

  
  def generateEnergy(amount: Double): Unit = {

    if (isConnected== false) {alarm.generateConnectionError()}
    else {

      energyGenerated += amount

      val energy_records = Source.fromFile("energy_history.csv").getLines().toList
      var total_energy = 0.0 // Default value
      
      energy_records.lastOption.foreach { line =>
        line.split(",") match {
          case Array(_, _, _, capacity) => 
            total_energy = capacity.split("/")(0).toDouble // Update total_energy with the extracted value
          case _ => {}
        }
      }

      val timestamp = LocalDateTime.now()
      val capacity = total_energy + energyGenerated

      if (capacity>95000) {alarm.generateStorageError()}
      val formattedTime = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
      val report = s"$id,$formattedTime,$energyGenerated,$capacity/10000\n"

      val fileWriter = new FileWriter("energy_history.csv", true)
      fileWriter.write(report)
      fileWriter.close()
    }

  
    

    }

  def disconnectEmergency(): Unit ={

      isConnected= false




    }

  def disconnect(): Unit ={

    if (isConnected== true) {

    isConnected= false
  }

  else  {

    isConnected= true
  }

  println("\n")

  println(s"Connected: $isConnected ")

  println("\n")
}

  override def toString: String = s"\n ---------------------------- \n Hydro powerplant $id \n Energy generated: $energyGenerated, \n Connected: $isConnected"
  
}








class ControlBoard() {
  
  def menu(): Unit= {

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


    val input= scala.io.StdIn.readLine("YOUR OPTION: ")


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
 

  }


  def exit() ={

    print("BYE")

  }


  def machinesControl(): Unit= {

    println("\n")

    println("---------------------------------")

    

    println("CHOOSE THE ENERGY SOURCE")

    println("---------------------------------")


    println("1. SOLAR PANELS      |  2.  WIND TURBINES")

    println("3. HYDRO POWERPLANT  |  4.  EXIT TO MENU")

    println("---------------------------------")

    

    val powerPlant = scala.io.StdIn.readLine("YOUR OPTION: ")

    powerPlant match {
      case "1" => {

      println("\n")

      println("---------------------------------")

      println("SOLAR PANELS")

      println("---------------------------------")

      println("WHAT WOULD YOU LIKE TO DO?")

      println("---------------------------------")

      println("1. TURN LEFT 45 DEGREES        |  2.  TURN RIGHT 45 DEGREES")
      
      println("3. DISCONNECT/CONNECT PANELS   |  4.  EXIT TO MENU")

      println("---------------------------------")

       val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

       

       choice match {

        case "1" => {

          solarPanels.moveLeft()

          menu()

        }

        case "2" => {

          solarPanels.moveRight()

          menu()
          
        }

        case "3" => {

          solarPanels.disconnect()

          menu()
          
        }

        case "4" => {

          menu()
          
        }

        case _   => menu()

       }

       }
      case "2" => {

        println("\n")

        println("---------------------------------")

        println("WIND TURBINES")

        println("---------------------------------")

        println("WHAT WOULD YOU LIKE TO DO?")

        println("---------------------------------")

        println("1. TURN LEFT 45 DEGREES        |  2.  TURN RIGHT 45 DEGREES")
        
        println("3. DISCONNECT/CONNECT TURBINES |  4.  EXIT TO MENU")

        println("---------------------------------")

        val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

        

        choice match {

          case "1" => {

            windTurbines.moveLeft()

            menu()

          }

          case "2" => {

            windTurbines.moveRight()

            menu()
            
          }

          case "3" => {

            windTurbines.disconnect()

            menu()
            
          }

          case "4" => {

            menu()
            
          }

          case _   => menu()

        }
       }
      case "3" => {

        println("\n")

        println("---------------------------------")

        println("HYDRO POWERPLANT")

        println("---------------------------------")

        println("WHAT WOULD YOU LIKE TO DO?")

        println("---------------------------------")

        
        
        println("1. DISCONNECT/CONNECT POWERPLANT  |  2.  EXIT TO MENU")

        println("---------------------------------")

        val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

        

        choice match {

          

          case "1" => {

            hydroPlant.disconnect()

            menu()
            
          }

          case "2" => {

            menu()
            
          }

          case _   => menu()

        }

      }
      case "4" => menu() 
      case _   => machinesControl()
    }





    

  }

  def machinesStatus(): Unit = {

    println("\n")


    println("CHOOSE THE ENERGY SOURCE")

    println("---------------------------------")


    println("1. SOLAR PANELS      |  2.  WIND TURBINES")

    println("3. HYDRO POWERPLANT  |  4.  EXIT TO MENU")

    println("---------------------------------")

    

    val powerPlant = scala.io.StdIn.readLine("YOUR OPTION: ")

    var status= ""

    


    powerPlant match {

      case "1" => {

        status = solarPanels.toString()

        println(status)

        menu()

        
      }

      case "2" => {

        status = windTurbines.toString()

        println(status)

        menu()

      }

      case "3" => {

        status = hydroPlant.toString()

        println(status)

        menu()

      }

      case "4" => menu()

      case _ => machinesStatus()



    }

    println(s"$status")

    

  }

  def dataAnalysis(): Unit = {

    def filterDataByDate(date: LocalDate): List[String] = {
      val energyRecords = scala.io.Source.fromFile("energy_history.csv").getLines().toList
      energyRecords.filter { line =>
        line.split(",") match {
          case Array(_, timestamp, _, _) =>
            val recordDate = LocalDate.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            recordDate.isEqual(date)
          case _ =>
            false
        }
      }
    }



    val input = scala.io.StdIn.readLine("WRITE DATE IN FORMAT yyyy-MM-dd: ")

    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val chosenDate = LocalDate.parse(input, dateFormat)


   
    val filteredLines = filterDataByDate(chosenDate)

    val energyData = filteredLines.flatMap { line =>
      line.split(",") match {
        case Array(_, timestamp, energyGenerated, _) =>
          Some(energyGenerated.toDouble)
        case _ =>
          None
      }
    }

    val dataAnalyser= DataAnalyser(energyData)

    val meanValue = dataAnalyser.mean
    val medianValue = dataAnalyser.median
    val modeValue = dataAnalyser.mode
    val rangeValue = dataAnalyser.range
    val midRangeValue = dataAnalyser.midRange

    val analysisReport = s"ANALYSIS REPORT\n\n" +
      "\n" +
      s"Mean: $meanValue\n" +
      s"Median: $medianValue\n" +
      s"Mode: $modeValue\n" +
      s"Range: $rangeValue\n" +
      s"Mid-range: $midRangeValue\n" +
      "\n" +
      s"HISTORY BY TIME FORMAT: \n${filteredLines.mkString("\n")}\n"

    val outputFileName = s"data_analysis_${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}.csv"
    val fileWriter = new FileWriter(outputFileName)
    
    fileWriter.write(analysisReport)
    fileWriter.close()

    println(s"Data analysis report written to '$outputFileName'.")


    menu()

     

  }

  def storageStatus() = {

    val energy_records = Source.fromFile("energy_history.csv").getLines().toList
      var total_energy = 0.0 // Default value
      
      energy_records.lastOption.foreach { line =>
        line.split(",") match {
          case Array(_, _, _, capacity) => 
            total_energy = capacity.split("/")(0).toDouble // Update total_energy with the extracted value
          case _ => {}
        }
      }

      println("\n")

      println("---------------------------")

      println(s"Energy storage has the following amount of energy: $total_energy/100000")



      println("---------------------------")

    menu()

  }

  def energyHistoryView(): Unit = {

    println("\n")


    println("CHOOSE THE ENERGY SOURCE")

    println("---------------------------------")


    println("1. SOLAR PANELS      |  2.  WIND TURBINES")

    println("3. HYDRO POWERPLANT  |  4.  EXIT TO MENU")

    println("---------------------------------")

    

    val powerPlant = scala.io.StdIn.readLine("YOUR OPTION: ")

    

    powerPlant match {

      case "1" => printRecords("model_3000")
      case "2" => printRecords("model_2000")
      case "3" => printRecords("model_1000")
      case "4" => menu()
      case _ => energyHistoryView()


    }

    menu()

    def printRecords(idM: String): Unit = {

      val energyRecords = scala.io.Source.fromFile("energy_history.csv").getLines().toList
      val filteredLines = energyRecords.filter { line =>
        line.split(",") match {
          case Array(id, _, energyGenerated, _) => id.trim == idM && energyGenerated.nonEmpty
          case _ => false
        }
      }

      if (filteredLines.nonEmpty) {

        println("\n")
        println(s"HISTORY FOR SPECIFIC ENERGY SOURCE")

        println("------------------------------------------------")
        filteredLines.foreach { line =>
          line.split(",") match {
            case Array(_, timestamp, energyGenerated, _) => println(s"$timestamp - $energyGenerated")
            case _ =>
          }
        }
      } else {
        println(s"NOTHING FOUND")
      }
    }



    }


}



class ErrorAlarm {


  def generateStorageError(): Unit = {

    println("\n")

    println("-----------------------------------------------------------")
    
    println(s"Error: STORAGE IS ALMOST FULL. DISCONNECTING THE ENERGY SOURCES")

    println("-----------------------------------------------------------")

    solarPanels.disconnectEmergency()

    windTurbines.disconnectEmergency()

    hydroPlant.disconnectEmergency()

    

    
  }

  def generateConnectionError(): Unit = {

    println("\n")

    println("-----------------------------------------------------------")
    
    println(s"Error: ENERGY SOURCE IS DISCONNECTED")

    println("-----------------------------------------------------------")





  }
}




  
class DataAnalyser(data: Seq[Double]) {
  
  def mean: Double = data.sum / data.length.toDouble

  def median: Double = {
    val sortedData = data.sorted
    val midIndex = sortedData.length / 2
    if (sortedData.length % 2 == 0)
      (sortedData(midIndex - 1) + sortedData(midIndex)) / 2
    else
      sortedData(midIndex)
  }

  def mode: Seq[Double] = {
    val counts = mutable.Map[Double, Int]().withDefaultValue(0)
    data.foreach(value => counts(value) += 1)

    val maxCount = counts.values.max
    counts.filter(_._2 == maxCount).keys.toSeq
  }

  def range: Double = data.max - data.min

  def midRange: Double = (data.min + data.max) / 2
}






}


