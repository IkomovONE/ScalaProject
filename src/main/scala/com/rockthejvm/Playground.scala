package com.rockthejvm

object Playground extends App {





  class SolarPanel(val id: String, val capacity: Double) {
  // Class variables (properties)
  private var energyGenerated: Double = 0

  // Methods
  def generateEnergy(amount: Double): Unit = {
    energyGenerated += amount
  }

  def getEnergyGenerated: Double = energyGenerated

  override def toString: String = s"SolarPanel $id (Capacity: $capacity)"
}



class WindTurbine(val id: String, val capacity: Double) {
  // Class variables (properties)
  private var energyGenerated: Double = 0

  // Methods
  def generateEnergy(amount: Double): Unit = {
    energyGenerated += amount
  }

  def getEnergyGenerated: Double = energyGenerated

  override def toString: String = s"SolarPanel $id (Capacity: $capacity)"
}



class HydroPowerPlant(val id: String, val capacity: Double) {
  // Class variables (properties)
  private var energyGenerated: Double = 0

  // Methods
  def generateEnergy(amount: Double): Unit = {
    energyGenerated += amount
  }

  def getEnergyGenerated: Double = energyGenerated

  override def toString: String = s"SolarPanel $id (Capacity: $capacity)"
}



val solarPanel1 = new SolarPanel("SP001", 1000)

  // Generate energy and retrieve energy generated
  solarPanel1.generateEnergy(500)
  val energyGenerated = solarPanel1.getEnergyGenerated

  // Print the solar panel information
  println(solarPanel1)


  val solarPanel1 = new SolarPanel("SP001", 1000)

  // Generate energy and retrieve energy generated
  solarPanel1.generateEnergy(500)
  val energyGenerated = solarPanel1.getEnergyGenerated

  // Print the solar panel information
  println(solarPanel1)



  val solarPanel1 = new SolarPanel("SP001", 1000)

  // Generate energy and retrieve energy generated
  solarPanel1.generateEnergy(500)
  val energyGenerated = solarPanel1.getEnergyGenerated

  // Print the solar panel information
  println(solarPanel1)






class ControlBoard(powerPlant: PowerPlant) {
  def turnSolarPanelLeft(panel: SolarPanel): Unit = {
    panel.turnLeft()
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
