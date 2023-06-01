package o1.theMilkyWay

trait Item(val name: String, val description: String):
  def function(actor: Player): String
  override def toString = this.name
end Item

object blip extends Item("blip", "It's the Blip - a powerful machine that can send you to the Hyperspace.\nYou can use the blip anywhere you want, just type 'use blip' to enter Hyperspace\nFrom Hyperspace, you can travel to different time and place by passing through wormholes.\nHowever, because you cannot pay the electric bill, the deivce is only charged enough to function 4 times.\nKeep track of your Blip's available jumps and REMEMBER to save ONE jump to come back HOME."):
  //blip can send you to Hypersapce 4 times in total
  def function(actor: Player): String =
    if actor.blipLeft > 1 then
      actor.go("hyspa")
      actor.blipLeft -= 1
      "Succesfully travelled to Hyperspace.\nBlip jump(s) available: " + actor.blipLeft
    else if actor.blipLeft == 1 then
      actor.go("hyspa")
      actor.blipLeft -= 1
      "Succesfully travelled to Hyperspace.\nYour Blip has drained its battery, 0 jump left"
    else
      "The Blip has ran out of power, you stuck here! Consider quititng the game or running around this area until your turns run out"
end blip

object laserGun extends Item("laser gun", "Red Skull's signature laser gun.\nExtremely useful againts bad guys, or when you just want to do things by force."):
  //If the player is in the mall, the gun will scare the security guy and force him to give you milk. In other areas, the gun will shoot pew pew pew
  def function(actor: Player) = 
    actor.location.npcByName("security") match
      case Some(npc) =>
        npc.drop() match
          case Some(thing) =>
            actor.location.addItem(thing)
            actor.location.removeNpc(npc)
            "The gun scared the security.\nHe brought you a carton of milk and ran away!."
          case None => "" //Never happens
      case None => "The gun goes pew pew pew!"

object goldCoin extends Item("gold coin", "A 24k gold coin that a Vietnamese billionaire threw away at the plaza.\nWhen you find the person who need this coin the most, 'use' it."):
  //the same as the laser gun, gold coin will be useful only when used in the Helsinki area
  def function(actor: Player) =
    actor.location.npcByName("homeless man") match
      case Some(npc) =>
        actor.drop(this.name) //The player must possess the gold coin to use it.
        npc.get(this.name)
        npc.drop() match
          case Some(thing) =>
            actor.location.addItem(thing)
            actor.location.removeNpc(npc)
            actor.location.description = "Nothing more to do here, get the note and blip away."
            "Homeless man: Thanks man, I really appreciate it. Please keep this note. I gotta make some money now, bye!"
          case None => "" //Never happens
      case None => "You can't use the gold coin here. How about we wait for the right place/time/person to use it? "

object note extends Item("note", "An appreciation note that a homeless person gave you.\nHe promised to return the favor later."):
  //only useful when the player use it in the bedroom, with no blip jump left, and has no milk
  def function(actor: Player) =
    if actor.location.name == "Bedroom" && actor.blipIsDead && !(actor.itemsInHand.flatMap(_.split(" ").takeRight(1)).contains("milk")) then
      actor.location.addItem(freeMilk)
      "Suddenly, a carton of milk appears.\nIt turns out that the homeless man you helped was the founder of X-Group - Finland's largest retailing cooperative organisation.\nTo repay your favor, he decided to give your family a life-time supply of milk.\nNow every time you need milk, just lie on your bed and use this note."
    else
      "You used the note to make a paper airplane, threw it, then picked it up again."

object love extends Item("love", "The love from your wife!"):
  def function(actor: Player) = "Love is in the air."

object spaceSuit extends Item("spacesuit", "A spacesuit that automatically attach to your body."):
  def function(actor: Player) = "This nanotech spacesuit is automatically equipped, no need to use it."

object pho extends Item("pho", "A delicious bowl of Pho. But it is sad to know that the special cow that you have been looking for was butchered to make this magical noodle soup."):
  def function(actor: Player) = "Slurp slurp. " + this.description

object gucci extends Item("gucci bag", "A glamorous Gucci bag. But it is sad to know that the special cow that you have been looking for was butchered to make this beautiful piece of art."):
  def function(actor: Player) = "Bling bling. " + this.description

object iceCreamCone extends Item("ice cream","Ice cream from Trang Tien. This is so good, bet the milk used to make this is good too. Better ask the manager where he got the milk."):
  def function(actor: Player) = "Yum yum. " + this.description

object lunchbox extends Item("lunchbox", "A mysterious lunch box. Well you are kinda hungry. But should you eat it?"):
  //use the lunchbox will kill you, reason down below
  def function(actor: Player) =
    actor.turnDead()
    "Oh hell no! Did you just eat the lunch box? It was Darth Vader's lunch, and he just remotely killed you by his Force!"

object badge extends Item("badge", "The badge of Dinosaurs Destroyer."):
  def function(actor: Player) = "Kids don't like you."

//class milk
class milk(name: String, description: String) extends Item(name, description):
  def function(actor: Player): String =
    actor.location.npcByName("wife") match
      case Some(npc) =>
        actor.drop(this.name)
        npc.get(this.name)
        "Your wife picks up the milk and gives you a French Kiss for 5 minutes.\nThen she gives the kids cereal with that milk."
      case None =>
        s"You have a sip of the $name. " + description

object moonMilk extends milk("moon milk", "Tasty moon cow milk.")

object mallMilk extends milk("mall milk", "Average American milk.")

object galaxyMilk extends milk("galaxy milk", "Strangely good milk, but strangely strange feeling.")

object freeMilk extends milk("free milk", "A life-time supply of milk.")