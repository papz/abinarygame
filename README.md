
# It's The Binary Game - [Phase1 Project](https://github.com/oakmac/professional-clojurescript-curriculum/blob/master/exercises/phase1-project.md "Professional Clojurescript - Phase1 Project") from [Professional Clojurescript](https://cljs.pro/) #

  The world of Network Addressing needs you!
  Decode each binary level to it's decimal equivalent for fun and profit, well, that depends on you!
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

# Running Notes #

 0. add html on to page
 Forgot about hot reloading, and how to do it, by hooking into the shadow lifecycle
 1. add state
 2. change state using atomic

 The DOM
 goog.dom
 https://m.seehuhn.de/data/jvjsdoc/example/goog/dom.html
 https://www.w3schools.com/js/js_htmldom_document.asp
 1. finding
 2. changing
 - the Property - .innerHTML, .attribute, .property
 - method - setAttribute (attr, val)
 3. Add and delete
 - createElement, removeChild,appendChild,replaceChild,write (text)
 
 Am I doing this, when the templates just react to data? no

 You don't end up doing this, as your output is just data based on functions

 todo:
 https://tailwindcss.com/docs/installation - npm packages I don't know how to include in cljs
 but then there's this https://github.com/rgm/tailwind-hiccup

 fooled by defonce, defines it once, don't need to call it

 Events
 https://developers.google.com/closure/library/docs/events_tutorial#events-in-javascript-and-closure-library
 a lot of help - Todo: transients - updating with many transformations
 https://www.learn-clojurescript.com/section-4/lesson-22-managing-state/

 Loading stylesheets in the application, probably just a library that reloads the page?
 (.appendChild (first (.getElementsByTagName js/document "head")) (html [:style {:src "main.css"}]))
 You won't be doing this - first hiccups works with html, the dom works with elements
 (js/console.log   (.createElement js/document (html [:link {:type "stylesheet" :href "main.css"}])))
 (let [x (.createElement js/document "link")]
   (.appendChild (first (.getElementsByTagName js/document "head")) (.createElement js/document "link")))

 knowing where you mount your app, and using js functions

 public API's
 https://github.com/n0shake/Public-APIs

 handling events

 loading the app on every step to get it to rerender

 once comfortable, the step should just call render with the state, not use of global

 feature of clojure - iteration after iteration
 really makes you think what to work on next
 
 problems I ran into - just an attempt without thinking about your change state trhough atoms
 - for example, setting the body as a local variable in let and then can't update it
 1. append app on to HTML - set! (el property) "new value"
 (set! (.-innerHTML (first (.getElementsByTagName js/document "body")))
       "hwat is this and that and then, now I'm learning this ")

 You may think you need a variable, that var can't change. Consider the body Element where you want your entire app to
 present side affects

 what is set! then, a function only for html

 atoms

 initial view
 grid view

 Wed 03 Mar 2021 13:27:23
 broken - I attempted to generate binary respresentation of a random-uui  - not random-uuid
 
 Think I got stuck in a recursive function where I didn't pass the initial n=1
 spent some time here working with iseq and assoc, with integers and strings
 question: not sure why solution is a iseq? and then I had to convert it into a vector

 Wed 03 Mar 2021 14:24:48
 Now that I can generate problems on the fly, and remove them, I need to be able to click on a bit to set it's value
 and a function to check to see if the binary problem is solved.

 Wed 03 Mar 2021 15:02:23
 there's going to come a time when chris let's us know that the events can be stored in the state

 Wed 03 Mar 2021 16:12:47
 finding out that the conversions of vectors and sequences is tricky, have to know what conversions are required

 Wed 03 Mar 2021 16:22:15
 I have a clickable bit button and extractable game to compare.  Now I need to update the problem binary, each time if it matches the
 solution, then the problem is solved, points added

 Wed 03 Mar 2021 16:26:06 
 before updating, i need an index on the bit, which bit is changing?

 Wed 03 Mar 2021 18:37:56
 Looking for a a vector with index's for the binary number
 https://clojure.org/reference/data_structures
 not easy to maintain, unless I just perform functions again to create each binary array

 Wed 03 Mar 2021 19:25:50
 Finally indexed the binary array, has to be a better way.

 Wed 03 Mar 2021 19:49:55
 exactly - you model events https://www.learn-clojurescript.com/section-3/lesson-19-mastering-data-with-maps-and-vectors/

 Wed 03 Mar 2021 23:21:58
 finally able to delete from atom state, update-in and dissoc fn

 Wed 03 Mar 2021 23:40:01
 awkward when using a HashMap and don't know the key but just want to remove an element
    
    * Todo
    
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

 
