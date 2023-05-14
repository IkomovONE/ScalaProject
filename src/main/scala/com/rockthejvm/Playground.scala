package com.rockthejvm

object Playground extends App {



  val myObject = new ControlBoard()

  val solarPanels = new SolarPanels("model_3000")

  val windTurbines = new WindTurbines("model_2000")

  val hydroPlant = new HydroPlant("model_1000")

  myObject.menu()





  class SolarPanels(val id: String) {
  
  private var energyGenerated: Double = 0

  
  def generateEnergy(amount: Double): Unit = {
    energyGenerated += amount
  }

  def getEnergyGenerated: Double = energyGenerated

  override def toString: String = s"SolarPanel $id (Capacity: $capacity)"
}



class WindTurbines(val id: String) {
  
  private var energyGenerated: Double = 0

  
  def generateEnergy(amount: Double): Unit = {
    energyGenerated += amount
  }

  def getEnergyGenerated: Double = energyGenerated

  override def toString: String = s"SolarPanel $id (Capacity: $capacity)"
}






class HydroPowerPlant(val id: String) {
  
  private var energyGenerated: Double = 0

  
  def generateEnergy(amount: Double): Unit = {
    energyGenerated += amount
  }

  def getEnergyGenerated: Double = energyGenerated

  override def toString: String = s"SolarPanel $id (Capacity: $capacity)"
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

    println("---------------------------------")

    println("OR ENTER E TO EXIT")

    println("---------------------------------")


    val input= scala.io.StdIn.readLine("YOUR OPTION: ")


    input match {
      case "1" => machinesControl() 
      case "2" => machinesStatus() 
      case "3" => dataAnalysis() 
      case "4" => storageStatus() 
      case "E" => exit() 
      case "e" => exit() 
      case _   => menu()
    }
 

  }


  def machinesControl(): Unit= {

    println("\n")

    println("---------------------------------")

    

    println("CHOOSE THE ENERGY SOURCE")


    println("1. SOLAR PANELS      |  2.  WIND TURBINES")

    println("3. HYDRO POWERPLANT  |  4.  EXIT TO MENU")

    println("---------------------------------")

    println("\n")

    val powerPlant = scala.io.StdIn.readLine("YOUR OPTION: ")

    powerPlant match {
      case "1" => {

      println("\n")

      println("---------------------------------")

      println("SOLAR PANELS")

      println("WHAT WOULD YOU LIKE TO DO?")

      println("1. TURN LEFT 45 DEGREES  |  2.  TURN RIGHT 45 DEGREES")
      
      println("3. DISCONNECT/CONNECT PANELS     |  4.  EXIT TO MENU")

       val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

       

       choice match {

        case "1" => {

          solarPanels.moveLeft()

        }

        case "2" => {

          solarPanels.moveRight()
          
        }

        case "3" => {

          solarPanels.disconnect()
          
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

        println("WHAT WOULD YOU LIKE TO DO?")

        println("1. TURN LEFT 45 DEGREES  |  2.  TURN RIGHT 45 DEGREES")
        
        println("3. DISCONNECT/CONNECT TURBINES     |  4.  EXIT TO MENU")

        val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

        

        choice match {

          case "1" => {

            windTurbines.moveLeft()

          }

          case "2" => {

            windTurbines.moveRight()
            
          }

          case "3" => {

            windTurbines.disconnect()
            
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

        println("WHAT WOULD YOU LIKE TO DO?")

        
        
        println("1. DISCONNECT/CONNECT POWERPLANT    |  2.  EXIT TO MENU")

        val choice = scala.io.StdIn.readLine("YOUR OPTION: ")

        

        choice match {

          

          case "1" => {

            hydroPlant.disconnect()
            
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

  def machinesStatus() = {

    println("status")

  }

  def dataAnalysis() = {

    println("data")

  }

  def storageStatus() = {

    println("storage")

  }

  def exit() = {

    println("exit")

  }

}









class ErrorAlarm {
  def generateError(message: String): Unit = {
    // Generate an error with the given message
    // (Sample code - replace with actual implementation)
    println(s"Error: $message")
  }
}




  


class DataAnalyser(data: Seq[Double]) {
  // Methods
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

  def midrange: Double = (data.min + data.max) / 2
}




}


