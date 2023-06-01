package o1.theMilkyWay

import scala.collection.mutable.Map

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. In general, an “area” can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name         the name of the area
  * @param description  a basic description of the area (typically not including information about items) */
class Area(var name: String, var description: String):

  private val neighbors = Map[String, Area]()
  private val itemsList = Map[String, Item]()
  private val npcList   = Map[String, NPC]()

  def addItem(item: Item) =
    itemsList += item.name -> item

  def contains(itemName: String) = itemsList.contains(itemName)

  def removeItem(itemName: String): Option[Item] = itemsList.remove(itemName)

  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) =
    this.neighbors += direction -> neighbor

  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction–area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) =
    this.neighbors ++= exits


  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and items. If there are no
    * items present, the return value has the form "DESCRIPTION\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". If there are one or more items present, the return
    * value has the form "DESCRIPTION\nYou see here: ITEMS SEPARATED BY SPACES\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". The items and directions are listed in an arbitrary order. */
  def fullDescription =
    val exitList = "\nExits available: " + this.neighbors.keys.toVector.filter(_ != "hyspa").sorted.mkString(", ")

    var itemsAvailable = "\n\nItems: "
    if itemsList.nonEmpty then
      itemsAvailable += itemsList.keys.mkString(", ")

    var npcAvailable = "\nNPCs: "
    if npcList.nonEmpty then
      npcAvailable += npcList.keys.mkString(", ")

    if this.name == "Hell no, it's the nuclear engine" || this.name == "The Arctic Ocean" || this.name == "7-ELeven" then
      this.description
    else
      this.description +  itemsAvailable + npcAvailable + exitList

  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)

  //New part
  //Add NPC to the area
  def addNpc(npc: NPC) =
    npcList += npc.name -> npc

  //Adding the item dropped by NPC to the area
  def drop(npc: NPC) =
    val newItem = npc.drop()
    newItem match
      case Some(item) => itemsList += item.name -> item
      case None => 
  
  //Remove the NPC from the area
  def removeNpc(npc: NPC) =
    npcList.remove(npc.name)

  //Return the NPC by name
  def npcByName(name: String) =
    npcList.get(name)

end Area

