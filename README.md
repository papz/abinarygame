
# It's The Binary Game - [Phase1 Project](https://github.com/oakmac/professional-clojurescript-curriculum/blob/master/exercises/phase1-project.md "Professional Clojurescript - Phase1 Project") from [cljs.pro](https://cljs.pro/) #

  The world of Network Addressing needs you!  
  Decode each Binary level to it's Decimal equivalent for fun and profit, well, that depends on you!  
  After translating 4 problems, you score the opportunity to access that IPv4 web address.  Amazing!
  
  This is an enjoyable learning [Phase1 Project](https://github.com/oakmac/professional-clojurescript-curriculum/blob/master/exercises/phase1-project.md "Professional Clojurescript - Phase1 Project") from [Professional Clojurescript](https://cljs.pro/).

# Requirements #

- node.js (v6.0.0+, most recent version preferred)
- npm (comes bundled with node.js) or yarn
- Java SDK (Version 8+, Hotspot)

# To get going #

We can simply shadow-cljs to compile a build and watch for file changes, and take advantage of the included dev server to run the game,
`npx shadow-cljs watch frontend`  
Then go to `https://localhost:8080`
    
# Todo #

 - namespace into util, views, engine
 - Solve the game, and clear row - done 'with excitement' - not done
 - Keep a count of solutions, introduce points
 - bug- the game reorders the list
 - add 'guard rails' button to add table headers of bit values ex. 128
 - write a description of what the game is about - in a modal
 - add watchers that rerender passing in state
 - when you reach a certain score you beat the game - then choose levels
 - handling out of range indexes - the cheat button
 - add tests
 - add hints that rotate at the top left or bottom - scrolling 'focus on the wabbits'
 - After 4 wins - create an IP address and show the website url
 - Styling using css [data-bit-set="false"] { color: blue } or just use cljs if empty .colorblue
 https://css-tricks.com/a-complete-guide-to-data-attributes/#intro
 
