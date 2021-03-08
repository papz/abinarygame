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
 `(.appendChild (first (.getElementsByTagName js/document "head")) (html [:style {:src "main.css"}]))`  
 You won't be doing this - first hiccups works with html, the dom works with elements  
 `(js/console.log   (.createElement js/document (html [:link {:type "stylesheet" :href "main.css"}])))
    (let [x (.createElement js/document "link")]  
    (.appendChild (first (.getElementsByTagName js/document "head")) (.createElement js/document "link")))`  

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
 `(set! (.-innerHTML (first (.getElementsByTagName js/document "body")))
       "hwat is this and that and then, now I'm learning this ")`

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

 Mon 08 Mar 2021 17:50:17
 how to refer to this?
 
 Mon 08 Mar 2021 22:20:16
 The namespacing is forcing me to know what I'm doing(need a watch) and to rearchitect the program more sensibly
 I know I'll have to handle state a lot better, and using it to generate views could be a lot simpler
