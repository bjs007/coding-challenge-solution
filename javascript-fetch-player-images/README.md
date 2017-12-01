
<H1> javascript-fetch-player-images </H1>
This is an application to use javascript to preload images while displaying an HTML. Preloading images is a great way to improve the user experience. When images are preloaded in the browser, the visitor can surf around a site and enjoy extremely faster loading times.
<Br>
<H4>Prerequisites </H4>
Javascript enabled browser
  
<H4> Implementation Overview</H4>
This is a basic javascript application to preload images based on the URLs provided on a server in JSON format. The script consists of few functions to make it modular. Each function has its own task

<H4> Implementation Detail </H4>
<p> There are total 4 functions. </p>
<p> * The first function fetchJson() retrieves an array of URLs and throws a promise.</p>
<p> * The second function loadImages() preload an image for each URL present in the array provided by the first function.</p>
<p> * The third function renderImages() to render each preloaded image to DOM.</p>
<p> * The final function is main() responsible for calling previous functions and making a chain of promises.</p>

This section basically outlines the functionalities of the various function used while performing the task.

<h6>fetchJson():</h6> fetchJson retrieves the an array of URLs and throws a promise.
<h6>loadImages():</h6> loadImages preloadImage for each URL.
<h6>renderImages():</h6> renderImages renders preloaded images to DOM.
<h6>main():</h6> Main function which consumes Promises thrown by above functions and chain them together.

<H4> To Run the Application </H4>
Open the index.html in a browser

<H4> Test Case </H4>
Same Images will be loaded into the browser as provided in the JSON kept on the server.
