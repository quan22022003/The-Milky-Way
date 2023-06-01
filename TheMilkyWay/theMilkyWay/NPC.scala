package o1.theMilkyWay

import scala.collection.mutable.Map
import scala.io.StdIn.readLine

class NPC(val name: String, val location: Area, val npcDrop: Item):
  private var inventoryList = Map[String, Item]()
  inventoryList += npcDrop.name -> npcDrop
  val question = ""
  val respondsToAnswers = Map[String, String]()

  def drop() = this.inventoryList.remove(npcDrop.name)

  def inventory = inventoryList

  def interaction =
    val playerAnswer = readLine("\n" + this.question + "\n\nEnter your answer, or type 'leave' to quit.\nYour answer: ").toLowerCase
    respondsToAnswers.getOrElse(playerAnswer, this.name.split(' ').map(_.capitalize).mkString(" ") + ": I don't know how to react to that.")
    
  def get(itemName: String) =
    this.location.removeItem(itemName) match
      case Some(item) =>
        this.inventoryList += item.name -> item
      case None =>
  end get

end NPC

