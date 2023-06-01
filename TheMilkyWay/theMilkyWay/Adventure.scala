package o1.theMilkyWay
import scala.collection.mutable
import scala.collection.mutable.Map
import scala.io.StdIn.readLine
import scala.util.Random

class Adventure:

  /** the name of the game */
  val title = "The Milky Way (Not that Milky Way)"

  //Setup areas
  private val bedroom     = Area("Bedroom", "Your wife is cooking, your kids are waiting for breakfast, and you are playing with the fabric of time and space?")
  private val bathroom    = Area("Bathroom", "What are you even doing here, a scientist never brushes his teeth, use the Blip to enter Hyperspace!")
  private val kitchen     = Area("Kitchen", "Your wife is waiting for milk.")

  private val hyperspace  = Area("Hyperspace", "For a proper illustration of Hyperspace, watch Interstellar by Christopher Nolan.\nBut for now, besdie the wormhole that will return you to your bedroom, you see 6 others to enter.\nWhich one will you choose?")

  private val spaceship   = Area("A spaceship, 29 April 2011", "You are in a spaceship landed on the Moon!\nYou hear some singing outside, should there be any milk out there?")
  private val moon        = Area("The Moon", "Houston, we've got a situation\nBetter stan' by de phone!\nIt's a brand new lunar taste sensation\nServed on a waffle cone!\nIt's Phineas and Ferb's moon cow farm!")

  private val airplane    = Area("A fancy airplane, 1945", "You are in a high-tech nuclear-carrying airplane flying over the Arctic.\nHoly hell! Is that Captain America fighting Red Skull?\nRed Skull's face looks tough, maybe because he drinks lots of milk, let steal some then!")
  private val nuclear     = Area("Hell no, it's the nuclear engine","As soon as you open the door, the nuclear reactor melts your face off.")
  private val escape      = Area("An escape pod", "You want to go back to that fight or fly off the airplane?")
  private val arctic      = Area("The Arctic Ocean", "You felt to the deep dark bottom of the Arctic Ocean.\nSeveral years later, people found and defrosted Steve Roger.\nYou were also found, but not defrosted. Instead, you were placed at Aalto School of ARTS as a Christmas-decoration-ice-sculpture")


  private val hanoi       = Area("The suburb of Hanoi, 2011", "A beautiful city, with lots of good foods.\nYou heard people talking (in English, fortunately) that there is a very special cow just arrived at the city. Bet its milk is delicious!")
  private val restaurant  = Area("A Pho restaurant", "The aromatic smell of Pho makes you shiver.\nCan you resist the hot bowl of Pho lying there on the table?")
  private val cityCenter  = Area("The city center", "Now you are standing in the very heart of Hanoi.\nOver there is Trang Tien Plaza, the first luxurious shopping center of Hanoi - a landmark of the city.\nAnd down here is Trang Tien Ice Cream - the childhood of many Hanoi children.\nBut where to find your milk?")
  private val plaza       = Area("Trang Tien Plaza", "You saw a very nice looking leather Gucci bag, want it?")
  private val iceCream    = Area("Trang Tien Ice Cream", "You asked for milk, but they only sell ice cream. Sad.")

  private val deathStar   = Area("A long time ago, in a galaxy far far away. Oh my Mark Hamill, it is the Death Star!", "You are standing in the Death Star center room.\nRescuing princess Leia seems fun, but without milk, your wife is no fun.\nOk, steer clear from Mr.Vader and leggo!")
  private val pantry      = Area("The Death Star pantry", "A nice and modern pantry, with an enormous refrigerator.\nThe fridge emits a very strange aura, it feels like there's a 10% chance that a bottle of milk will appear inside.\nShould you open it and go inside?")
  private val fridge      = Area("You opened and entered the refrigerator.", "It's kinda cold.")
  private val controlRoom = Area("The Death Star control room", "This is where they drive the Death star.\nHyper jump to the nearest convenient store?")
  private val sevenEleven = Area("7-ELeven", "You crashed the Death Star into a 7-Eleven store, killed yourself, Luke Skywalker, several employees, and most importantly, your kids' hope of a hearty breakast")
  private val canon       = Area("The Death Star Infamous Laser Beam", "There's a gunner Stormtrooper who is preparing the beam.")

  private val helsinki    = Area("Helsinki, Winter 1904", "BRRRR! How on Earth can it get this cold. No cow can produce milk under this weather.")

  private val parking     = Area("Twin Pines Mall Parking Lot, California 1985", "Sweet, a DeLorean car! Wait? It is THE DELOREAN!\nYou can check out the mall over there, or you can check out this greatest invention of all time")
  private val delorean    = Area("The DeLorean Time Machine", "The car seems to be locked on a ramdom point inside space-time continuum\nA test drive or not?")
  private val mall        = Area("Inside Twin Pines Mall", "There's a supermarket here, but it's closed. Oh, there's a security guy here too.")

  //Set final destination
  private val destination = kitchen

  //Set available wormholes destiantions
  val locationsList = Vector(spaceship, airplane, hanoi, deathStar, helsinki, bedroom)
  private def randomAreaNummber = Random.nextInt(locationsList.size)

  //Set Area's neighbors
  bedroom .setNeighbors(Vector("bathroom" -> bathroom, "kitchen" -> kitchen, "hyspa" -> hyperspace))
  bathroom.setNeighbors(Vector("bedroom" -> bedroom, "hyspa" -> hyperspace))
  kitchen .setNeighbors(Vector("bedroom" -> bedroom, "hyspa" -> hyperspace))

  hyperspace.setNeighbors(Vector("bedroom" -> bedroom, "1" -> spaceship, "2" -> airplane, "3" -> hanoi, "4" -> deathStar, "5" -> helsinki, "6" -> parking))

  spaceship.setNeighbors(Vector("moon" -> moon, "hyspa" -> hyperspace))
  moon     .setNeighbors(Vector("spaceship" -> spaceship, "hyspa" -> hyperspace))

  airplane   .setNeighbors(Vector("engine room" -> nuclear, "escape pod" -> escape, "hyspa" -> hyperspace))
  escape     .setNeighbors(Vector("back" -> airplane, "off the plane" -> arctic, "hyspa" -> hyperspace))

  hanoi     .setNeighbors(Vector("restaurant" -> restaurant, "city center" -> cityCenter, "hyspa" -> hyperspace))
  restaurant.setNeighbors(Vector("suburb" -> hanoi, "hyspa" -> hyperspace))
  cityCenter.setNeighbors(Vector("suburb" -> hanoi, "plaza" -> plaza, "ice cream shop" -> iceCream, "hyspa" -> hyperspace))
  plaza     .setNeighbors(Vector("city center" -> cityCenter, "hyspa" -> hyperspace))
  iceCream  .setNeighbors(Vector("city center" -> cityCenter, "hyspa" -> hyperspace))

  deathStar   .setNeighbors(Vector("laser canon" -> canon, "control room" -> controlRoom, "hyspa" -> hyperspace))
  canon       .setNeighbors(Vector("center room" -> deathStar, "pantry" -> pantry, "hyspa" -> hyperspace))
  controlRoom .setNeighbors(Vector("center room" -> deathStar, "pantry" -> pantry, "7 eleven" -> sevenEleven, "hyspa" -> hyperspace))
  pantry      .setNeighbors(Vector("laser canon" -> canon, "control room" -> controlRoom, "inside the fridge" -> fridge, "hyspa" -> hyperspace))
  fridge      .setNeighbors(Vector("outside the fridge" -> pantry, "hyspa" -> hyperspace))

  helsinki.setNeighbors(Vector("hyspa" -> hyperspace))

  parking .setNeighbors(Vector("inside the car" -> delorean, "mall" -> mall, "hyspa" -> hyperspace))
  delorean.setNeighbors(Vector("out of the car" -> parking, "test drive at 88mph" -> locationsList(randomAreaNummber), "hyspa" -> hyperspace))
  mall    .setNeighbors(Vector("parking lot" -> parking, "hyspa" -> hyperspace))

  //Place items
  bedroom.addItem(blip)
  spaceship.addItem(spaceSuit)
  cityCenter.addItem(goldCoin)
  restaurant.addItem(pho)
  plaza.addItem(gucci)
  iceCream.addItem(iceCreamCone)

  private val itemGen = Random.nextInt(10)
  if itemGen == 0 then
    fridge.addItem(galaxyMilk)
  else
    fridge.addItem(lunchbox)



  //Set up NPCs
  object phineas extends NPC("phineas", moon, moonMilk):
    override val question = "Phineas: \nYou want some milk from our moon cow?\nWell, only if you can translate the sentence 'tasty moon cow milk' to Ferb Latin."
    override val respondsToAnswers = Map[String, String]("leave" -> "Phineas: Come back anytime!", "asty-terb oon-merb ow-cerb ilk-merb" -> "Phineas: That sounds right! Here's your moon cow milk")
    private var interactionState = true
    override def interaction  =
      if interactionState then
        val playerAnswer = readLine("\n" + this.question + "\nEnter your answer, or type 'leave' to quit.\nYour answer: ").toLowerCase
        val respond = respondsToAnswers.getOrElse(playerAnswer, "Phineas: Either that was a wrong answer, or you just saying nonsense.")
        if respond == "Phineas: That sounds right! Here's your moon cow milk" then
           this.drop() match
              case Some(thing) =>
                this.location.addItem(thing)
                interactionState = false
                respond
              case None => "..." //This case never happens
        else
          respond
      else
        "I already gave you the moon milk!"

  object security extends NPC("security", mall, mallMilk):
    override val question = "Security: \nMall is closed. Come back tomorrow.\nUnless you have something intimidating enough to force me do it, like a freaking laser gun.\nDo you have one? (answer 'yes' or 'no')"
    override val respondsToAnswers = Map[String, String]("leave" -> "Security: Yeah fxxk off", "yes" -> "Security: Use it then?\nIf you are carrying a laser gun, type 'use laser gun' to show your total domination", "no" -> "Security: Then why the hell should I open the mall for you?")

  object homeless extends NPC("homeless man", helsinki, note):
    override val question = "Homelss man: \nI don't want to live like this forever.\nI'm creating a start-up of my own, I just need some money for that.\nCan you lend me some? Just a pure gold coin is enough! (answer 'yes' or 'no')"
    override val respondsToAnswers = Map[String, String]("leave" -> "Homelss man: Wait, come back!", "yes" -> "Homeless man: Then please give me your precious, I will pay you back one day!\nIf you are carrying a gold coin, type 'use gold coin' to give it to the homeless man", "no" -> "Homeless man: You don't look that rich anyway.")

  object wife extends NPC("wife", kitchen, love):
    override val question = "Wife: Did you get milk? (answer 'yes' or 'no')"
    override val respondsToAnswers =  Map[String, String]("leave" -> "Wife: Well let's see what will you get first: milk or a divorce.", "yes" -> "Wife: Really? Then give me the milk please.\nIf you are carrying milk, type 'use milkName' to give it to your wife. milkName is the name of any of the milk you are carrying.", "no" -> "Wife: Then what are you waiting for? You are lucky I'm not holding a knife.")

  object captain extends NPC("captain", airplane, laserGun):
    override val question = "Captain America: Hey, do you want to help me defeat that red piece of meat? (answer 'yes' or 'no')"
    override val respondsToAnswers = Map[String, String] ("leave" -> "Captian America: Ok just hide somewhere safe.", "yes" -> "Captain America: That's the spirit.", "no" -> "Captain America: Ok son, he's kinda out of your league anyway.")
    var interactionState = true
    override def interaction  =
      if interactionState then
        val playerAnswer = readLine("\n" + this.question + "\nEnter your answer, or type 'leave' to quit.\nYour answer: ").toLowerCase
        val respond = respondsToAnswers.getOrElse(playerAnswer, "I don't understand a word you said!")
        if playerAnswer == "yes" then
          this.location.removeNpc(redSkull)
          this.drop() match
            case Some(thing) =>
              this.location.addItem(thing)
              interactionState = false
              respond + "\nYou fought along side Captain America for 1 minute straight before running out of breath.\nYou still won, however.\nCaptain America gave you the laser gun of Red Skull as a gift."
            case None => "..." //This case never happens
        else
          respond
      else
        "Good job son, the gun looks dope right?"

  object redSkull extends NPC("red skull", airplane, laserGun):
    override val question = "Red Skull: Are you of Aryan Blood? (answer 'yes' or 'no')"
    override val respondsToAnswers = Map[String, String]("leave" -> "Red Skull: Running away? Such a coward!", "yes" -> "Red Skull: Bro, we need to talk about this, in private.", "no" -> "Red Skull: Then prepare to die!")
    override def interaction =
      val playerAnswer = readLine("\n" + this.question + "\nEnter your answer, or type 'leave' to quit.\nYour answer: ").toLowerCase
      val respond = respondsToAnswers.getOrElse(playerAnswer, "Gibberish!")
      if playerAnswer == "yes" then
        "You and Red Skull talked long and hard. But seriously dude, you have a problem."
      else if playerAnswer == "no" then
        player.turnDead()
        "Red Skull shot you to dead.\nYou died a hero, sort of."
      else
        respond

  object manager extends NPC("manager", iceCream, iceCreamCone):
    override val question = "Manager: You want to know where we get the ice cream? (answer 'yes' or 'no')"
    override val respondsToAnswers = Map[String, String]("leave" -> "Manager: Ok then, come back when you need to know.", "yes" -> "Manager:\nWe got the milk from 2 super smart kids.\nI don't know where 2 find those kids though.\nSorry, I have to take a number 2 now.\nWow, quite a lot of 2 in my answer huh?", "no" -> "Manager: ok.")

  object gunner extends NPC("gunner", canon, badge):
    override val question = "Stormtrooper: You must be the new general, do you want to randomly fire this massive destructive son of a gun? (answer 'yes' or 'no')"
    override val respondsToAnswers = Map[String, String] ("leave" -> "Stormtrooper: Did I said something wrong sir?", "yes" -> "Stormtrooper:\n3...2...1 PEWWWWWWWWWW.\nOh dear, we just killed all the dinosaurs on Mars.", "no" -> "Stormtrooper: Wow, a sane person.")
    var interactionState = true
    override def interaction  =
      if interactionState then
        val playerAnswer = readLine("\n" + this.question + "\nEnter your answer, or type 'leave' to quit.\nYour answer: ").toLowerCase
        val respond = respondsToAnswers.getOrElse(playerAnswer, "I don't understand a word you said!")
        if playerAnswer == "yes" then
           this.drop() match
              case Some(thing) =>
                this.location.addItem(thing)
                interactionState = false
                respond + "\nWell, here's your badge of honor."
              case None => "..." //This case never happens
        else
          respond
      else
        "Stormtrooper: You killed the dinosaurs!"


  moon.addNpc(phineas)
  mall.addNpc(security)
  helsinki.addNpc(homeless)
  kitchen.addNpc(wife)
  airplane.addNpc(captain)
  airplane.addNpc(redSkull)
  iceCream.addNpc(manager)
  canon.addNpc(gunner)




  //Game logic
  /** The character that the player controls in the game. */
  val player = Player(bedroom)
  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 30

  /** Determines if the your wife received milk*/
  def wifeGotMilk = wife.inventory.keys.size == 2 //the wife always keeps 'love', and she can only be given 1 type of 'milk'. So if the inventory size is == 2 then she has both love and milk.

  /** Determines if the the adventure is complete, without completing the quest. */
  private def isHome = this.player.location == this.destination

  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = isHome && wifeGotMilk

  def isLost = this.turnCount == this.timeLimit || this.player.isDead

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.isLost

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage =
    "\n\nIt's a Sunday morning, and you are enjoying it." +
    "\nThen suddenly, your wife shouts from downstair: Hey honey, can you go get some milk, the kids want cereal for breakfast but we're out of milk already!" +
    "\nYou - a mildly mad scientist who graduated from Aalto University with a 5 in 'Programming Language 1' and 0 in 'Calculus' and 'Matrix Algebra' - feel very upset." +
    "\nYou just want to lie in bed all day today. Driving to the nearest Blepa to buy some milk takes too much of your energy, which was already completely depleted during the week." +
    "\nHowever, as a loving husband and a responsible father, you reply: Yes ma'am!" +
    "\nBut, as a scientist, you never take the 'easy' way. You decide to use the Blip - your latest invention to get milk." +
    "\nThe Blip allows you to travel through time and space. Maybe somewhere along the history of human was a carton of milk for you to grab?" +
    "\nThe Blip is lying over there on the table, pick it up and start your adventure!" +
    "\nREMEMBER TO EXAMINE THE BLIP AFTER GETTING IT! You are a mad scientist, not a fool, you always check your device before using it!" +
    "\nIf you find milk, come to the kitchen to give it to your wife. That would be a WIN!" +
    "\nType 'help' for guide, be careful, avoid traps, answer wisely, and set your milk on mind! (I mean mind on milk...)"


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage =
    if this.isComplete then //change depends on type of milk
      val endings = Map[String, String](
        "moon milk" -> "The milk tastes so good that your kids decide to become doctors and engineers when they grow up (instead of Youtubers and rappers).\nBIG WIN!",
        "mall milk" -> "You brought home the wrong sort of milk.\nDue to your misdeed, the kids, who are full-fledged-lactose-intolerant, are having diarrhea for 5 days straight.\nYou kinda won?",
        "free milk" -> "Now your family will never be hungry anymore!\nYou won the game, and a milk lottery.",
        "galaxy milk" -> "Your kids awake their Force after drinking that Star Wars galaxy milk. Prepare for a reign of terror. And 2 lightsabers please.\nYou won this game, but will you win over the Dark Side?")
      val sortOfMilk = wife.inventory.keys.toVector.filter( _ != "love" ).head
      endings(sortOfMilk)
    else if isHome then
      "Home sweet home! But no milk yet, you're such a disappointment!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up. You wandered too long between dimensions, your wife left you and she took the kids with her."
    else if this.player.isDead then
      "Quite a peculiar death!"
    else  // game over due to player quitting
      "Quitter!"


  /** Plays a turn by executing the given in-game command, such as “go bedroom”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.turnCount += 1
      this.player.seeASurprise() //check if the player is eligible for a surprise due to the action
    outcomeReport.getOrElse(s"Unknown command: \"$command\".")

end Adventure

