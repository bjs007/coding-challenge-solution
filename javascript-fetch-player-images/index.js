/**
 * @index.js
 * This is a basic java script which proload images based on the URLs provided on a server in JSON format.
 * The script consists of several function to make it modular. Each function has its own task.
 * The first function fetchJson retrieves the an array of URLs and throws a promise.
 * The second function loadImages preload an image for each URL present in the the array provided by the first function.
 * The third function helps to render each preloaded image to DOM.
 * The final function is main responsbile for calling previous functions and making a chain of promises.
 */


var PLAYER_IMAGES = "https://s3.amazonaws.com/p2f-coding-challenge/player-images.json";


/**
 * fetchJson retrieves the an array of URLs and throws a promise.
 * @return {Promise} on success returns an array of URL and on failure returns error in Promise
 */
function fetchJson() {
      // PLAYER_IMAGES is used in the fetch() function
    // This function returns a Promise
    return fetch(PLAYER_IMAGES, {
        method: 'get'
    })
    .then((resp) => resp.json())
    .then(function(data) {
        return data.players;
    })
    .catch(function(error) {
        return Promise.reject(error);
    });
}
/**
 * loadImages preloadImage for each URL
 * @param  images it takes an array of URLs of images
 * @return {Promise} returns a Promise.
 */
function loadImages(images) {
      // This function returns Promise
    var createdImages=[];
    return new Promise(
        function (resolve, reject) {
            var loadedCount = 0;
            var maxCount = images.length;
            for(var i=0; i<images.length; i++){
                preloadImage(images[i], function(){
                    loadedCount++;
                    if(loadedCount == maxCount){
                        return resolve(createdImages);
                    }
                });
            }
            function preloadImage(url, imageAfterLoad){
                var img = new Image();
                img.src = url;
                createdImages.push(img);
                img.onload = imageAfterLoad;
            }
        }
    );
}
/**
 * renderImages renders preloaded images to DOM.
 * @param  images it takes preloaded images in an array
 * @return {Promise} returns a Promise with a Message of successfully loading of images.
 */
function renderImages(images) {
    // This function returns a Promise with success message.
    return new Promise(
        function (resolve, reject) {
            for(var i=0; i<images.length; i++){
                document.getElementById("players").appendChild(images[i]);
            }
            return resolve("All images loaded successfully");
        }
    );
}
/**
 * Main function which consumes Promises thrown by above functions and chain them together.
 */
  function main() {

    fetchJson()    //fetch the json and then load the images. This is chaining for Promises.
    .then(loadImages)
    .then(renderImages)
    .then(function (success) {
        setTimeout(function(){
            alert("Sucess: "+success);
        },100);
    })
    .catch(function (error) {
        alert("Error: "+error);
    });
}

// Execute main function now
main();
