package o1.theMilkyWay

import scala.collection.mutable.Map
import scala.util.Random
import scala.io.StdIn.readLine

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val inventoryList = Map[String, Item]()
  var blipLeft = 4
  private var notAlive = false

  def get(itemName: String): String =
    this.location.removeItem(itemName) match
      case Some(item) =>
        this.inventoryList += item.name -> item
        s"You pick up the $itemName."
      case None =>
        s"There is no $itemName here to pick up."
  end get

  def drop(itemName: String) =
    this.inventoryList.remove(itemName) match
      case Some(item) =>
        this.location.addItem(item)
        s"You drop the ${item.name}."
      case None =>
        "You don't have that."
  end drop

  def examine(itemName: String) =
    if this.has(itemName) then
      val description = this.inventoryList.get(itemName) match
        case Some(item) => item.description
        case None => "???"
      s"You look closely at the $itemName.\n" + description
    else
      "If you want to examine something, you need to pick it up first."
  end examine

  def has(itemName: String): Boolean = this.inventoryList.contains(itemName)

  def inventory =
    if this.inventoryList.nonEmpty then
      "You are carrying:\n" + this.inventoryList.keys.mkString("\n")
    else
      "You are empty-handed."
  end inventory

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then 
      "You go " + direction + "." 
    else "You can't go " + direction + "."


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() =
    "You stop and rest for a while, wondering why you get yourself into all these troubles."


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""

  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

  //new part
  def blipIsDead = this.blipLeft == 0
  
  //return the name of all items in inventory
  def itemsInHand = this.inventoryList.keys.toVector

  //check if the player is eligible for a surprise ending, the conditions are: player is in bedroom, player has no kind of milk, there 0 blip jump left, and player has the item 'note'.
  def seeASurprise() =
    if this.currentLocation.name == "Bedroom" && !(this.itemsInHand.flatMap(_.split(" ").takeRight(1)).contains("milk")) && this.blipIsDead && this.inventoryList.contains("note") then
      this.currentLocation.description = "You feel like something has changed, use your item 'note'!"

  //update if the player is dead when moving to a new area
  def updateDeath() =
    val areaName = this.currentLocation.name
    if areaName == "The Arctic Ocean" || areaName == "Hell no, it's the nuclear engine" || areaName == "The Arctic Ocean" || areaName == "7-ELeven" then
      this.turnDead()
    if areaName == "The Moon" && !(this.itemsInHand.contains("spacesuit")) then
      this.currentLocation.description = "Oh no you come out here without a spacesuit, you freeze to death!"
      this.turnDead()

  def isDead = this.notAlive

  //kill the player
  def turnDead() =
    this.notAlive = true

  def hasNoMilk = !(this.itemsInHand.contains("moon milk") || this.itemsInHand.contains("mall milk") || this.itemsInHand.contains("galaxy milk"))

  //use the item, some items will have different functions in certain areas
  def use(itemName: String) =
    inventoryList.get(itemName) match
      case Some(item) => item.function(this)
      case None => "You don't have that"
  end use

  //interact with the NPCs
  def interact(npcName: String) =
    val npc = this.location.npcByName(npcName)
    npc match
      case Some(npc) => npc.interaction
      case None => "No NPC of that name found"

  def help() =
    "You need help?\n\n" +
      "You will have 30 turns to complete the game, a turn is every time you successfully give a command.\n" +
      "During a turn, the game will display the current area's avaible exits, items, and NPCs\n"+
      "If you fail to bring the milk back to your wife in 30 turns, or you get stuck in another dimension, or you just get killed horribly, you lose.\n\n" +
      "Here are the commands that you can use:\n" +
      " go       : type 'go [space] exit' to go to the area you want\n" +
      " rest     : type 'rest' to rest...\n" +
      " quit     : type 'quit' to quit the game, especially useful when your mom calls for dinner\n" +
      " get      : type 'get [space] item' to collect the item to your inventory\n" +
      " drop     : type 'drop [space] item' to drop the item to the current area\n" +
      " use      : type 'use [space] item' to use the item, certain item will have extra function when in a certain area.\n" +
      " examine  : type 'examine [space] item' to see all the detail of the item\n" +
      " inventory: type 'inventory' to see all the items you are carrying\n" +
      " interact : type 'interact [space] NPC' to interact with the NPC\n" +
      "\nGood luck and have fun!"

end Player

