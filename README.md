# Web Slinger

Spiders shoot webbing at you - slinging webs from a distance, or when they hit you in melee!

Be careful!  This makes spiders more difficult foes, since you get stuck in the webbing.

![Web Slinger Example](https://github.com/Stormwind99/WebSlinger/raw/newmaster/src/resources/screenshots/WebSlingerExample.gif)

## FAQ

1. How to I make my mod's spiders also sling webs?

   * Subclass `EntitySpider` for your new spider - see `EntityCaveSpider` in vanilla MC for an example.
   * Adding the AIWebbingAttack task to an entity is another method: `entity.tasks.addTask(7, new AIWebbingAttack(entity));`

2. Can I sling webs myself?

   * In Creative mode or with console cheat commands, create `webslinger:webbing` items and throw them like snowballs.

## Credits

* PlayerInWebMessage.java is from the [WebShooter mod](https://github.com/josephcsible/WebShooter) (which inspired this mod) by Joseph C. Sible
